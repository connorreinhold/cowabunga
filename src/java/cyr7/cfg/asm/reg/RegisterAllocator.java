package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.AsmCFGUtil;
import cyr7.cfg.asm.constructor.AsmCFGConstructor;
import cyr7.cfg.asm.dfa.WorklistAnalysis;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.ir.IdGenerator;
import cyr7.util.Pair;
import cyr7.util.Sets;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempRegArg;
import cyr7.x86.visitor.TempVisitor;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class RegisterAllocator {

    public static final ASMReg[] DEFAULT_AVAILABLE_REGISTERS = {
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

    private final ASMReg[] availableRegisters;
    private final Set<ASMTempRegArg> precolored;
    private final int K;
    private final IdGenerator generator;

    private ArrayList<ASMLine> functionBody;
    private final String mangledName;

    private SpillMemAllocator spillAllocator = new SpillMemAllocator();

    public RegisterAllocator(
        List<ASMLine> functionBody,
        String mangledName,
        ASMReg[] availableRegisters,
        IdGenerator generator) {

        ArrayList<ASMLine> augmentedFunctionBody = new ArrayList<>(functionBody);
        augmentedFunctionBody.add(ASMLineFactory.instance.Ret());

        this.availableRegisters = availableRegisters;
        this.precolored = Set.of(availableRegisters);
        this.K = availableRegisters.length;
        this.generator = generator;
        this.functionBody = augmentedFunctionBody;
        this.mangledName = mangledName;

        init();
    }

    public RegisterAllocator(List<ASMLine> functionBody, String mangledName, IdGenerator generator) {
        this(functionBody, mangledName, DEFAULT_AVAILABLE_REGISTERS, generator);
    }

    private final Worklists worklists = new Worklists();

    private final HashSet<ASMTempRegArg> spilledNodes = new HashSet<>();
    private final HashSet<ASMTempRegArg> coalescedNodes = new HashSet<>();
    private final HashSet<ASMTempRegArg> coloredNodes = new HashSet<>();
    private final Deque<ASMTempRegArg> selectStack = new LinkedList<>();

    private final Moves moves = new Moves(worklists.moves);

    private final Alias alias = new Alias(coalescedNodes);

    private InterferenceGraph graph;
    private HashMap<ASMTempRegArg, Integer> coloring;
    private CoalescingHeuristic coalescingHeuristic;

    // getters

    public HashMap<ASMTempRegArg, Integer> coloring() {
        return coloring;
    }

    public ASMReg[] registers() {
        return availableRegisters;
    }

    public ArrayList<ASMLine> program() {
        return functionBody;
    }

    public int allocatedTemps() {
        return spillAllocator.allocatedTemps();
    }

    public HashSet<Integer> coalescedMoves() {
        return moves.coalesced;
    }

    public Alias alias() {
        return alias;
    }

    // run

    public void run() {
        build();
        makeWorklist();
        do {
            if (!worklists.simplify.isEmpty()) {
                simplify();
            } else if (!worklists.moves.isEmpty()) {
                coalesce();
            } else if (!worklists.freeze.isEmpty()) {
                freeze();
            } else if (!worklists.spill.isEmpty()) {
                selectSpill();
            }
        } while (!worklists.simplify.isEmpty()
            || !worklists.moves.isEmpty()
            || !worklists.freeze.isEmpty()
            || !worklists.spill.isEmpty());

        assignColors();

        if (!spilledNodes.isEmpty()) {
            rewriteProgram();
            run();
        }
    }

    private void init() {
        coloring = new HashMap<>(K);
        for (int i = 0; i < availableRegisters.length; i++) {
            coloring.put(availableRegisters[i], i);
        }

        worklists.initial.addAll(uniqueTemps(functionBody));
    }

    private List<ASMTempArg> uniqueTemps(List<ASMLine> lines) {
        Set<ASMTempArg> temps = new HashSet<>();
        var tempVisitor = new TempVisitor();
        lines.forEach(l -> temps.addAll(l.accept(tempVisitor)));
        return new ArrayList<>(temps);
    }

    // build

    private void build() {
        AsmCFGConstructor constructor = new AsmCFGConstructor(functionBody);
        AsmCFGStartNode cfg = constructor.constructAsmCFG();
        AsmCFGUtil.printDotForFunctionAsm(cfg);
        Map<AsmCFGNode, Set<ASMTempRegArg>> liveVariables
            = WorklistAnalysis.analyze(
            cfg,
            new LiveVariableAnalysis(mangledName));

        graph = new InterferenceGraph(precolored, selectStack, coalescedNodes);
        for (var keyValue : liveVariables.entrySet()) {
            List<ASMTempRegArg> args = new ArrayList<>(keyValue.getValue());

            // Add every distinct pair of conflicting registers
            for (int i = 0; i < args.size(); i++) {
                for (int j = 0; j < i; j++) {
                    graph.addEdge(args.get(i), args.get(j));
                }
            }
        }

        for (int move = functionBody.size() - 1; move >= 0; move--) {
            if (functionBody.get(move) instanceof ASMInstr
                && Util.isMoveInstruction((ASMInstr) functionBody.get(move))) {

                ASMInstr moveInstr = (ASMInstr) functionBody.get(move);
                if (moveInstr.args.get(0) instanceof ASMTempRegArg &&
                    moveInstr.args.get(1) instanceof ASMTempRegArg) {

                    moves.assoc((ASMTempRegArg) moveInstr.args.get(0), move);
                    moves.assoc((ASMTempRegArg) moveInstr.args.get(1), move);
                    worklists.moves.add(move);
                }
            }
        }

        this.coalescingHeuristic =
            new CoalescingHeuristic(K, precolored, graph);
    }

    // make worklist

    private void makeWorklist() {
        while (!worklists.initial.isEmpty()) {
            ASMTempRegArg n = worklists.initial.removeFirst();

            if (graph.degree(n) >= K) {
                worklists.spill.add(n);
            } else if (moves.nodeIsMoveRelated(n)) {
                worklists.freeze.add(n);
            } else {
                worklists.simplify.add(n);
            }
        }
    }

    // simplify

    private void simplify() {
        ASMTempRegArg n = worklists.simplify.removeFirst();
        selectStack.push(n);
        // this automatically decrements the degree in the graph

        for (ASMTempRegArg m : graph.adjacent(n)) {
            degreeDecremented(m);
        }
    }

    private void degreeDecremented(ASMTempRegArg m) {
        if (graph.degree(m) == K - 1) {
            enableMoves(Sets.union(Set.of(m), graph.adjacent(m)));

            boolean removed = worklists.spill.remove(m);
            assert removed;

            if (moves.nodeIsMoveRelated(m)) {
                worklists.freeze.add(m);
            } else {
                worklists.simplify.add(m);
            }
        }
    }

    private void enableMoves(Set<ASMTempRegArg> nodes) {
        for (ASMTempRegArg n : nodes) {
            for (int m : moves.getMoves(n)) {
                if (moves.active.contains(m)) {
                    boolean removed = moves.active.remove(m);
                    assert removed;

                    worklists.moves.add(m);
                }
            }
        }
    }

    // coalesce

    private void addWorklist(ASMTempRegArg u) {
        if (!precolored.contains(u)
            && !moves.nodeIsMoveRelated(u)
            && graph.degree(u) < K) {

            boolean removed = worklists.freeze.remove(u);
            assert removed;

            worklists.simplify.add(u);
        }
    }

    private void coalesce() {
        int move = worklists.moves.removeFirst();
        Pair<ASMTempRegArg, ASMTempRegArg> xy = getCopyRegs(move);
        ASMTempRegArg x = xy.left, y = xy.right;

        ASMTempRegArg u, v;
        if (precolored.contains(y)) {
            u = y;
            v = x;
        } else {
            u = x;
            v = y;
        }

        if (u.equals(v)) {
            moves.coalesced.add(move);
            addWorklist(u);
        } else if (precolored.contains(v) && graph.adjacentSet(u, v)) {
            moves.constrained.add(move);
            addWorklist(u);
            addWorklist(v);
        } else if (precolored.contains(u)
            && graph.adjacent(v).stream().allMatch(t -> coalescingHeuristic.ok(t, u))
            || !precolored.contains(u)
            && coalescingHeuristic.conservative(Sets.union(
            graph.adjacent(u),
            graph.adjacent(v)))) {

            moves.coalesced.add(move);
            combine(u, v);
            addWorklist(u);
        } else {
            moves.active.add(move);
        }
    }

    private void combine(ASMTempRegArg x, ASMTempRegArg y) {
        if (worklists.freeze.contains(y)) {
            worklists.freeze.remove(y);
        } else {
            worklists.spill.remove(y);
        }

        coalescedNodes.add(y);
        alias.make(y, x);
        moves.merge(x, y);
        enableMoves(Set.of(y));

        for (ASMTempRegArg t : graph.adjacent(y)) {
            graph.addEdge(t, x);
            degreeDecremented(t);
        }

        if (graph.degree(x) >= K && worklists.freeze.contains(x)) {
            boolean removed = worklists.freeze.remove(x);
            assert removed;

            worklists.spill.add(x);
        }
    }

    // freeze

    private void freeze() {
        ASMTempRegArg x = worklists.freeze.removeFirst();
        worklists.simplify.add(x);
        freezeMoves(x);
    }

    private void freezeMoves(ASMTempRegArg u) {
        for (int move : moves.getMoves(u)) {
            Pair<ASMTempRegArg, ASMTempRegArg> xy = getCopyRegs(move);
            ASMTempRegArg x = xy.left, y = xy.right;

            ASMTempRegArg v;
            if (alias.get(y).equals(alias.get(u))) {
                v = alias.get(x);
            } else {
                v = alias.get(y);
            }

            boolean removed = moves.active.remove(move);
            assert removed;

            moves.frozen.add(move);
            if (worklists.freeze.contains(v) && moves.getMoves(v).isEmpty()) {
                removed = worklists.freeze.remove(v);
                assert removed;

                worklists.simplify.add(v);
            }
        }
    }

    // spill

    private void selectSpill() {
        ASMTempRegArg m = worklists.spill.removeFirst();
        worklists.simplify.add(m);
        freezeMoves(m);
    }

    // assign colors

    private void assignColors() {
        while (!selectStack.isEmpty()) {
            ASMTempRegArg n = selectStack.removeFirst();
            Set<Integer> okColors = IntStream.rangeClosed(0, K - 1)
                .boxed().collect(Collectors.toSet());

            for (ASMTempRegArg w : graph.adjacent(n)) {
                w = alias.get(w);
                if (coloredNodes.contains(w) || precolored.contains(w)) {
                    okColors.remove(coloring.get(w));
                }
            }

            if (okColors.isEmpty()) {
                spilledNodes.add(n);
            } else {
                coloredNodes.add(n);
                coloring.put(n, okColors.stream().findAny().get());
            }
        }

        for (ASMTempRegArg n : coalescedNodes) {
            coloring.put(n, coloring.get(alias.get(n)));
        }
    }

    // rewrite program

    private void rewriteProgram() {
        SpillProgramRewriter rewriter = new SpillProgramRewriter(
            generator,
            functionBody,
            spilledNodes,
            spillAllocator);
        rewriter.run();
        functionBody = rewriter.rewritten();

        worklists.initial.clear();
        worklists.initial.addAll(coloredNodes);
        worklists.initial.addAll(coalescedNodes);
        worklists.initial.addAll(spillAllocator.newTemps());

        coloredNodes.clear();
        coalescedNodes.clear();
    }

    // utils

    private Pair<ASMTempRegArg, ASMTempRegArg> getCopyRegs(int move) {
        ASMInstr moveInstr = (ASMInstr) functionBody.get(move);
        ASMTempRegArg x = alias.get((ASMTempRegArg) moveInstr.args.get(0));
        ASMTempRegArg y = alias.get((ASMTempRegArg) moveInstr.args.get(1));
        return new Pair<>(x, y);
    }

}
