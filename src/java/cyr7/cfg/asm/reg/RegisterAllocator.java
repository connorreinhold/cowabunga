package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.AsmCFGUtil;
import cyr7.cfg.asm.constructor.AsmCFGConstructor;
import cyr7.cfg.asm.constructor.AsmCFGUnreachableNodeCleaner;
import cyr7.cfg.asm.dfa.WorklistAnalysis;
import cyr7.cfg.asm.dot.AsmCFGAnalysisDotVisitor;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGSourceNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cfg.ir.CFGUtil;
import cyr7.cli.CLI;
import cyr7.ir.IdGenerator;
import cyr7.util.Sets;
import cyr7.x86.asm.ASMArg;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class RegisterAllocator {

    public static final int MAX_ITERATIONS = 50;

    public static final ASMReg[] REGISTERS = {
        ASMReg.RAX,
        ASMReg.RCX,
        ASMReg.RDX,
        ASMReg.RBX,
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

    public static final Set<ASMTempRegArg> PRECOLORED = Set.of(REGISTERS);

    public static final int K = PRECOLORED.size();

    private ArrayList<ASMLine> functionBody;
    private final String mangledName;
    private final IdGenerator generator;

    public RegisterAllocator(
        List<ASMLine> functionBody,
        String mangledName,
        IdGenerator generator) {

        this.generator = generator;
        this.functionBody = new ArrayList<>(functionBody);
        this.functionBody.add(ASMLineFactory.instance.Ret());
        this.mangledName = mangledName;

        worklists.initial.addAll(uniqueTemps(functionBody));
    }

    public List<ASMLine> program() {
        return functionBody;
    }

    public int numSpilledTemps() {
        return spillAllocator.allocatedTemps();
    }

    private final SpillMemAllocator spillAllocator = new SpillMemAllocator();

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

    // run

    private static List<ASMTempArg> uniqueTemps(List<ASMLine> lines) {
        Set<ASMTempArg> temps = new HashSet<>();
        var tempVisitor = new TempVisitor();
        lines.forEach(l -> temps.addAll(l.accept(tempVisitor)));
        return new ArrayList<>(temps);
    }

    public void run() throws RegisterAllocationFailedException {
        int iterations = 0;
        boolean rerun;
        do {
            iterations++;
            CLI.debugPrint("Iterations: " + iterations);
//            CLI.lazyDebugPrint(() -> {
//                for (int i = 0; i < functionBody.size(); i++) {
//                    CLI.debugPrint(String.format("%3d: %s", i, functionBody.get(i).getIntelAssembly()));
//                }
//                System.err.flush();
//            });

            if (iterations >= RegisterAllocator.MAX_ITERATIONS) {
                throw new RegisterAllocationFailedException(iterations);
            }

            coloring = new HashMap<>(K);
            for (int i = 0; i < REGISTERS.length; i++) {
                coloring.put(REGISTERS[i], i);
            }

            assert worklists.simplify.isEmpty();
            assert worklists.freeze.isEmpty();
            assert spilledNodes.isEmpty();
            assert coalescedNodes.isEmpty();
            assert coloredNodes.isEmpty();
            assert selectStack.isEmpty();

            moves.coalesced.clear();
            moves.constrained.clear();
            moves.frozen.clear();
            worklists.moves.clear();
            moves.active.clear();
            moves.clearMoveList();
            alias.clearAliases();

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
                rerun = true;

                rewriteProgram();
            } else {
                rerun = false;

                FinalProgramRewriter rewriter = new FinalProgramRewriter(
                    PRECOLORED,
                    functionBody,
                    coloring,
                    REGISTERS,
                    moves.coalesced,
                    alias);
                rewriter.run();
                functionBody = rewriter.rewritten();
            }
        } while (rerun);
    }

    // build

    private void build() {
        AsmCFGConstructor constructor = new AsmCFGConstructor(functionBody);
        AsmCFGStartNode cfg = constructor.constructAsmCFG();
        new AsmCFGUnreachableNodeCleaner().removeUnreachableNodes(cfg);
        Map<AsmCFGNode, Set<? extends ASMTempRegArg>> liveInVariables
            = WorklistAnalysis.analyze(cfg, new LiveVariableAnalysis(mangledName));

        Map<AsmCFGNode, Set<? extends ASMTempRegArg>> liveOutVariables = new HashMap<>();
        for (var entry : liveInVariables.entrySet()) {
            List<AsmCFGNode> prevs = entry.getKey().inNodes();
            for (AsmCFGNode prev : prevs) {
                if (liveOutVariables.containsKey(prev)) {
                    Set<ASMTempRegArg> newLiveOut
                        = Sets.union(liveOutVariables.get(prev), entry.getValue());
                    liveOutVariables.put(prev, newLiveOut);
                } else {
                    liveOutVariables.put(prev, entry.getValue());
                }
            }
        }

//        CLI.lazyDebugPrint(() -> {
//            CLI.debugPrint("<-- LIVE VARIABLE ANALYSIS --> ");
//            AsmCFGUtil.debugOutputDotForAnalysis(cfg, node -> {
//                if (liveOutVariables.containsKey(node)) {
//                    return liveOutVariables.get(node).stream().map(ASMArg::getIntelArg).collect(Collectors.joining(", "));
//                } else {
//                    return "None";
//                }
//            });
//            CLI.debugPrint("<-- LIVE VARIABLE ANALYSIS --> ");
//        });

        graph = new InterferenceGraph(PRECOLORED, selectStack, coalescedNodes);
        for (var entry : liveOutVariables.entrySet()) {
            HashSet<ASMTempRegArg> live = new HashSet<>(entry.getValue());
            live.remove(ASMReg.RSP);
            live.remove(ASMReg.RBP);

            if (entry.getKey() instanceof AsmCFGSourceNode) {
                ASMInstr instr = ((AsmCFGSourceNode) entry.getKey()).sourceInstr();
                if (Util.isVarToVarMove(instr)) {
                    live.removeAll(entry.getKey().accept(new UsesVisitor(mangledName)));
                }

                SpecialEdges.addSpecialEdges(instr, graph);
            }

            Set<ASMTempRegArg> defs = entry.getKey().accept(new DefsVisitor());
            live.addAll(defs);
            for (ASMTempRegArg d : defs) {
                if (d instanceof ASMReg && !PRECOLORED.contains(d)) {
                    continue;
                }

                for (ASMTempRegArg l : live) {
                    if (l instanceof ASMReg && !PRECOLORED.contains(l)) {
                        continue;
                    }

                    graph.addEdge(l, d);
                }
            }

        }

        for (int move = 0; move < functionBody.size(); move++) {
            if (functionBody.get(move) instanceof ASMInstr
                && Util.isVarToVarMove((ASMInstr) functionBody.get(move))) {

                ASMInstr moveInstr = (ASMInstr) functionBody.get(move);
                moves.assoc((ASMTempRegArg) moveInstr.args.get(0), move);
                moves.assoc((ASMTempRegArg) moveInstr.args.get(1), move);
                worklists.moves.add(move);
            }
        }

        this.coalescingHeuristic =
            new CoalescingHeuristic(K, PRECOLORED, graph);
    }

    // make worklist

    private void makeWorklist() {
        assert worklists.freeze.isEmpty();
        assert worklists.spill.isEmpty();
        assert worklists.simplify.isEmpty();

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

        Set<ASMTempRegArg> worklistUnions = Sets.union(
            Sets.union(PRECOLORED, worklists.simplify),
            Sets.union(worklists.freeze, worklists.spill));
        for (ASMTempRegArg node : worklists.simplify) {
            assert graph.degree(node) == Sets.intersection(graph.adjList(node), worklistUnions).size();
            assert graph.degree(node) < K;
            assert Sets.intersection(moves.getMoves(node),
                Sets.union(moves.active, worklists.moves)).isEmpty();
        }
        for (ASMTempRegArg node : worklists.freeze) {
            assert graph.degree(node) == Sets.intersection(graph.adjList(node), worklistUnions).size();
            assert graph.degree(node) < K;
            assert !Sets.intersection(moves.getMoves(node),
                Sets.union(moves.active, worklists.moves)).isEmpty();
        }
        for (ASMTempRegArg node : worklists.spill) {
            assert graph.degree(node) == Sets.intersection(graph.adjList(node), worklistUnions).size();
            assert graph.degree(node) >= K;
        }
    }

    // simplify

    private void simplify() {
        ASMTempRegArg n = worklists.simplify.removeFirst();
        selectStack.push(n);

        for (ASMTempRegArg m : graph.adjacent(n)) {
            decrementDegree(m);
        }
    }

    private void decrementDegree(ASMTempRegArg m) {
        int degree = graph.decrementDegree(m);
        if (degree == K) {
            enableMoves(Sets.union(Set.of(m), graph.adjacent(m)));

            assert worklists.spill.contains(m);
            worklists.spill.remove(m);

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
        if (!PRECOLORED.contains(u)
            && !moves.nodeIsMoveRelated(u)
            && graph.degree(u) < K) {

            boolean removed = worklists.freeze.remove(u);
            assert removed;

            worklists.simplify.add(u);
        }
    }

    private void coalesce() {
        int move = worklists.moves.removeFirst();
        ASMInstr moveInstr = (ASMInstr) functionBody.get(move);
        ASMTempRegArg x = alias.get((ASMTempRegArg) moveInstr.args.get(0));
        ASMTempRegArg y = alias.get((ASMTempRegArg) moveInstr.args.get(1));

        ASMTempRegArg u, v;
        if (PRECOLORED.contains(y)) {
            u = y;
            v = x;
        } else {
            u = x;
            v = y;
        }

        if (u.equals(v)) {
            moves.coalesced.add(move);
            addWorklist(u);
        } else if (PRECOLORED.contains(v) || graph.adjacentSet(u, v)) {
            moves.constrained.add(move);
            addWorklist(u);
            addWorklist(v);
        } else if (
            (PRECOLORED.contains(u)
                && graph.adjacent(v).stream().allMatch(t -> coalescingHeuristic.ok(t, u)))
                || (!PRECOLORED.contains(u)
                && coalescingHeuristic.conservative(
                Sets.union(graph.adjacent(u), graph.adjacent(v))))) {

            moves.coalesced.add(move);
            combine(u, v);
            addWorklist(u);
        } else {
            moves.active.add(move);
        }
    }

    private void combine(ASMTempRegArg u, ASMTempRegArg v) {
        if (worklists.freeze.contains(v)) {
            boolean removed = worklists.freeze.remove(v);
            assert removed;
        } else {
            boolean removed = worklists.spill.remove(v);
            assert removed;
        }

        coalescedNodes.add(v);
        alias.make(v, u);
        moves.merge(u, v);
        enableMoves(Set.of(v));

        for (ASMTempRegArg t : graph.adjacent(v)) {
            graph.addEdge(t, u);
            decrementDegree(t);
        }

        if (graph.degree(u) >= K && worklists.freeze.contains(u)) {
            boolean removed = worklists.freeze.remove(u);
            assert removed;

            worklists.spill.add(u);
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
            ASMInstr moveInstr = (ASMInstr) functionBody.get(move);
            ASMTempRegArg x = (ASMTempRegArg) moveInstr.args.get(0);
            ASMTempRegArg y = (ASMTempRegArg) moveInstr.args.get(1);

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

    private ASMTempRegArg popFavoriteSpill() {
        Iterator<ASMTempRegArg> iterator = worklists.spill.iterator();
        while (iterator.hasNext()) {
            ASMTempRegArg m = iterator.next();
            if (m instanceof ASMReg) {
                iterator.remove();
                return m;
            } else if (!((ASMTempArg) m).name.startsWith("_t_spill_")) {
                iterator.remove();
                return m;
            }
        }
        return worklists.spill.removeFirst();
    }

    private void selectSpill() {
        ASMTempRegArg m = popFavoriteSpill();
        worklists.simplify.add(m);
        freezeMoves(m);
    }

    // assign colors

    private void assignColors() {
        while (!selectStack.isEmpty()) {
            ASMTempRegArg n = selectStack.removeFirst();
            Set<Integer> okColors = IntStream.rangeClosed(0, K - 1)
                .boxed().collect(Collectors.toSet());

            for (ASMTempRegArg w : graph.adjList(n)) {
                w = alias.get(w);
                if (coloredNodes.contains(w) || PRECOLORED.contains(w)) {
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
        CLI.debugPrint("Spilled nodes: " + spilledNodes);

        SpillProgramRewriter rewriter = new SpillProgramRewriter(
            generator,
            functionBody,
            spilledNodes,
            spillAllocator
        );
        rewriter.run();
        functionBody = rewriter.rewritten();

        spilledNodes.clear();
        worklists.initial.clear();
        worklists.initial.addAll(coloredNodes);
        worklists.initial.addAll(coalescedNodes);
        worklists.initial.addAll(spillAllocator.newTemps());
        coloredNodes.clear();
        coalescedNodes.clear();
    }

}
