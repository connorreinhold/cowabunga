package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.constructor.AsmCFGConstructor;
import cyr7.cfg.asm.dfa.WorklistAnalysis;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.util.Pair;
import cyr7.util.Sets;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMInstrType;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class RegisterAllocator {

    private final ASMReg[] availableRegisters = {
        ASMReg.RAX,
        ASMReg.RCX,
        ASMReg.RDX,
        ASMReg.RSI,
        ASMReg.RDI,
        ASMReg.R8,
        ASMReg.R9,
        ASMReg.R10,
        ASMReg.R11,
        ASMReg.R12,
        ASMReg.R13,
        ASMReg.R14,
        ASMReg.R15
    };

    private final int K = availableRegisters.length;

    private final Set<ASMTempRegArg> precolored = Set.of(availableRegisters);

    private final List<ASMLine> functionBody;

    private final String mangledName;

    public RegisterAllocator(List<ASMLine> functionBody, String mangledName) {
        this.functionBody = new ArrayList<>(functionBody);
        this.mangledName = mangledName;
    }

    // node sets

    // temporaries, not precolored and not yet processed
    private Deque<ASMTempArg> initial;

    // list of low-degree non-move-related nodes
    private Deque<ASMTempRegArg> simplifyWorklist;

    // low-degree move-related nodes
    private Deque<ASMTempRegArg> freezeWorklist;

    // high-degree nodes
    private Deque<ASMTempRegArg> spillWorklist;

    // nodes marked for spilling during this round
    private List<ASMTempArg> spilledNodes;

    // variables that have been coalesced; when u <- v is coalesced, v is added
    // to this set and u put back on some worklist (or vice versa)
    private List<ASMTempArg> coalescedNodes;

    // nodes successfully colored
    private Set<ASMTempArg> coloredNodes;

    // stack containing temporaries removed from the graph
    private Deque<ASMTempRegArg> selectStack;

    // move sets

    // (index of) moves that have been coalesced
    private Set<Integer> coalescedMoves;

    // (index of) moves whose source and target interfere
    private Set<Integer> constrainedMoves;

    // (index of) moves that will no longer be considered for coalescing
    private Set<Integer> frozenMoves;

    // (index of) moves enabled for possible coalescing
    private Set<Integer> worklistMoves;

    // (index of) moves not yet ready for coalescing
    private Set<Integer> activeMoves;

    // other data structures

    private Set<Pair<ASMTempRegArg, ASMTempRegArg>> adjSet;

    private HashMap<ASMTempArg, Set<ASMTempRegArg>> adjList;

    private Map<ASMTempRegArg, Integer> degree;

    // a mapping from a node to the list of moves it is associated with
    private Map<ASMTempRegArg, Set<Integer>> moveList;

    // when a move (u, v) has been coalesced, and v put in coalescedNodes, then
    // alias(v) = u
    private Map<Integer, Integer> alias;

    private Map<ASMTempRegArg, ASMReg> coloring;

    private void run() {
        initial = new LinkedList<>();
        simplifyWorklist = new LinkedList<>();
        freezeWorklist = new LinkedList<>();
        spillWorklist = new LinkedList<>();
        spilledNodes = new ArrayList<>();
        coalescedNodes = new ArrayList<>();
        coloredNodes = new HashSet<>();
        selectStack = new LinkedList<>();
        coalescedMoves = new HashSet<>();
        constrainedMoves = new HashSet<>();
        frozenMoves = new HashSet<>();
        worklistMoves = new HashSet<>();
        activeMoves = new HashSet<>();
        adjSet = new HashSet<>();
        adjList = new HashMap<>();
        degree = new HashMap<>();
        moveList = new HashMap<>();
        alias = new HashMap<>();
        coloring = new HashMap<>();

        build();
        makeWorklist();
        do {
            if (!simplifyWorklist.isEmpty()) {
                simplify();
            } else if (!worklistMoves.isEmpty()) {
                coalesce();
            } else if (!freezeWorklist.isEmpty()) {
                freeze();
            } else if (!spillWorklist.isEmpty()) {
                selectSpill();
            }
        } while (!spillWorklist.isEmpty()
            || !worklistMoves.isEmpty()
            || !freezeWorklist.isEmpty()
            || !spillWorklist.isEmpty());

        assignColors();

        if (!spilledNodes.isEmpty()) {
            rewriteProgram();
            run();
        }
    }

    // build

    private void build() {
        AsmCFGConstructor constructor = new AsmCFGConstructor(functionBody);
        AsmCFGStartNode cfg = constructor.constructAsmCFG();
        Map<AsmCFGNode, Set<ASMTempRegArg>> liveVariables
            = WorklistAnalysis.analyze(cfg, new LiveVariableAnalysis(mangledName));

        for (Map.Entry<AsmCFGNode, Set<ASMTempRegArg>> keyValue : liveVariables.entrySet()) {
            List<ASMTempRegArg> args = new ArrayList<>(keyValue.getValue());

            // Add every distinct pair of conflicting registers
            for (int i = 0; i < args.size(); i++) {
                for (int j = 0; j < i; j++) {
                    addEdge(args.get(i), args.get(j));
                }
            }
        }

        for (int i = functionBody.size() - 1; i >= 0; i++) {
            ASMLine line = functionBody.get(i);
            if (line instanceof ASMInstr) {
                ASMInstr instr = (ASMInstr) line;
                if (isMoveInstruction(instr)) {
                    for (ASMTempRegArg n : getAllArgs(instr)) {
                        getOrDefault(moveList, n, () -> new HashSet<>(1)).add(i);
                    }
                    worklistMoves.add(i);
                }
            }
        }
    }

    private void addEdge(ASMTempRegArg lhs, ASMTempRegArg rhs) {
        if (adjSet.contains(new Pair<>(lhs, rhs))) {
            return;
        }

        adjSet.add(new Pair<>(lhs, rhs));
        adjSet.add(new Pair<>(rhs, lhs));

        if (lhs instanceof ASMTempArg && precolored.contains(lhs)) {
            ASMTempArg u = (ASMTempArg) lhs;
            // lhs not precolored
            getOrDefault(adjList, u, () -> new HashSet<>(1)).add(rhs);
            if (degree.containsKey(u)) {
                degree.put(u, degree.get(u) + 1);
            } else {
                degree.put(u, 1);
            }
        }

        if (rhs instanceof ASMTempArg && precolored.contains(rhs)) {
            ASMTempArg v = (ASMTempArg) rhs;
            // lhs not precolored
            getOrDefault(adjList, v, () -> new HashSet<>(1)).add(rhs);
            if (degree.containsKey(v)) {
                degree.put(v, degree.get(v) + 1);
            } else {
                degree.put(v, 1);
            }
        }
    }

    private boolean isMoveInstruction(ASMInstr instr) {
        return instr.type == ASMInstrType.MOV || instr.type == ASMInstrType.MOVQ;
    }

    // make worklist

    private void makeWorklist() {
        while (!initial.isEmpty()) {
            ASMTempArg n = initial.removeFirst();

            if (degree.getOrDefault(n, 0) >= K) {
                spillWorklist.add(n);
            } else if (moveRelated(n)) {
                freezeWorklist.add(n);
            } else {
                simplifyWorklist.add(n);
            }
        }
    }

    // simplify

    private Set<ASMTempRegArg> adjacentNodes(ASMTempRegArg arg) {
        return Sets.difference(
            adjList.get(arg),
            Sets.union(new HashSet<>(selectStack), new HashSet<>()));
    }

    private Set<Integer> nodeMoves(ASMTempRegArg arg) {
        return Sets.intersection(
            moveList.get(arg),
            Sets.union(
                activeMoves,
                worklistMoves));
    }

    private boolean moveRelated(ASMTempRegArg n) {
        return !nodeMoves(n).isEmpty();
    }

    private void simplify() {
        ASMTempRegArg n = simplifyWorklist.removeFirst();
        selectStack.push(n);
        for (ASMTempRegArg m : adjacentNodes(n)) {
            decrementDegree(m);
        }
    }

    private void decrementDegree(ASMTempRegArg m) {
        int d = degree.getOrDefault(m, 0);
        degree.put(m, d - 1);
        if (d == K) {
            enableMoves(Sets.union(Set.of(m), adjacentNodes(m)));
            spillWorklist.remove(m);
            if (moveRelated(m)) {
                freezeWorklist.add(m);
            } else {
                simplifyWorklist.add(m);
            }
        }
    }

    private void enableMoves(Set<ASMTempRegArg> nodes) {
        for (ASMTempRegArg n : nodes) {
            for (int m : nodeMoves(n)) {
                if (activeMoves.contains(m)) {
                    activeMoves.remove(m);
                    worklistMoves.add(m);
                }
            }
        }
    }

    private void addWorklist(ASMTempRegArg u) {
        if (u instanceof ASMTempArg && !moveRelated(u) && degree.getOrDefault(u, 0) < K) {
            freezeWorklist.remove(u);
            simplifyWorklist.add(u);
        }
    }

    private boolean precoloredCoalescingHeuristic(ASMTempRegArg t, ASMTempRegArg r) {
        return degree.get(t) < K || precolored.contains(t) || adjSet.contains(new Pair<>(t, r));
    }

    private boolean conservativeCoalescingHeuristic(Set<ASMTempRegArg> nodes) {
        int k = 0;
        for (ASMTempRegArg n : nodes) {
            if (degree.getOrDefault(n, 0) >= 0)
        }
    }

    // coalesce

    private void coalesce() { }

    // freeze

    private void freeze() { }

    // spill

    private void selectSpill() { }

    // assign colors

    private void assignColors() { }

    // rewriteProgram

    private void rewriteProgram() { }

    // utils

    private int getDegree(ASMTempRegArg n) {
        return degree.get(n);
    }

    private static Set<ASMTempRegArg> getAllArgs(ASMInstr instr) {
        return instr.args.stream()
            .map(arg -> arg.accept(ArgUsesVisitor.INSTANCE))
            .reduce(Sets::union)
            .orElse(Collections.emptySet());
    }

    private static <K, V> V getOrDefault(Map<K, V> map, K key, Supplier<V> supplier) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            V value = supplier.get();
            map.put(key, value);
            return value;
        }
    }

}
