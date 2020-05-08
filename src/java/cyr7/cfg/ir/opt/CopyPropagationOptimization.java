package cyr7.cfg.ir.opt;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cfg.ir.dfa.CopyPropagationAnalysis;
import cyr7.cfg.ir.dfa.CopyPropagationAnalysis.CopyPropLattice;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRExpr;

public class CopyPropagationOptimization {

    private CopyPropagationOptimization() {}

    /**
     * Performs one passing of copy propagation.
     * <p>
     * Generally, if copies of variables/temporariess are available for
     * a variable {@code x}, then {@code x} is replaced with the variable
     * in the set of copies with the earliest definition.
     * <p>
     * @param start The {@link CFGStartNode start} node of the IR CFG.
     * @return The same {@code start} node, but the CFG has been optimized with
     *         one passing of copy propagation.
     */
    public static CFGStartNode optimize(CFGNode start) {
        // Mapping from CFGNode to the out lattices.
        CFGStartNode startNode = (CFGStartNode)start;
        System.out.println("Starts analysis");
        final var visitor = new CFGVarReplacementVisitor(
                WorklistAnalysis.analyze((CFGStartNode)start,
                CopyPropagationAnalysis.INSTANCE).in());
        System.out.println("Terminates analysis");
        Set<CFGNode> visited = new HashSet<>();
        Queue<CFGNode> nextNodes = new ArrayDeque<>();
        nextNodes.add(start);

        while (!nextNodes.isEmpty()) {
            var next = nextNodes.remove();

            // Replaces variables with copies in the mapping,
            // and updates the neighboring edges.
            next.accept(visitor);

            visited.add(next);

            for (CFGNode out: next.out()) {
                if (!visited.contains(out) && !nextNodes.contains(out)) {
                    nextNodes.add(out);
                }
            }

        }
        return startNode;
    }

    private static class CFGVarReplacementVisitor
                                    implements IrCFGVisitor<CFGNode> {

        private Map<CFGNode, CopyPropLattice> result;

        public CFGVarReplacementVisitor(Map<CFGNode, CopyPropLattice> result) {
            this.result = Collections.unmodifiableMap(result);
        }

        @Override
        public CFGNode visit(CFGCallNode n) {
            final var lattice = this.result.get(n);

            final List<IRExpr> args = n.call.args().stream().map(arg -> {
                return IRTempReplacer.replace(arg, lattice.copies);
            }).collect(Collectors.toList());
            final var call = new IRCallStmt(n.location(), n.call.collectors(),
                    n.call.target(), args);
            n.call = call;
            n.refreshDfaSets();
            return n;
        }


        @Override
        public CFGNode visit(CFGIfNode n) {
            // For copy propagation, true and false are the same.
            final var lattice = this.result.get(n);

            final var condition = IRTempReplacer.replace(n.cond, lattice.copies);
            n.cond = condition;
            n.refreshDfaSets();
            return n;
        }

        @Override
        public CFGNode visit(CFGVarAssignNode n) {
            final var lattice = this.result.get(n);

            final var value = IRTempReplacer.replace(n.value, lattice.copies);
            n.value = value;
            n.refreshDfaSets();
            return n;
        }

        @Override
        public CFGNode visit(CFGMemAssignNode n) {
            final var lattice = this.result.get(n);
            final var value = IRTempReplacer.replace(n.value, lattice.copies);
            final var mem = IRTempReplacer.replace(n.target, lattice.copies);
            n.value = value;
            n.target = mem;
            n.refreshDfaSets();
            return n;
        }

        @Override
        public CFGNode visit(CFGBlockNode n) {
            final var lattice = new HashMap<>(this.result.get(n).copies);
            n.block = new CFGBlockVarReplacementVisitor(lattice, n.block).replaceBlock();
            n.refreshDfaSets();
            return n;
        }

        @Override
        public CFGNode visit(CFGReturnNode n) {
            return n;
        }

        @Override
        public CFGNode visit(CFGStartNode n) {
            return n;
        }

        @Override
        public CFGNode visit(CFGSelfLoopNode n) {
            return n;
        }

        private static class CFGBlockVarReplacementVisitor implements IrCFGVisitor<CFGNode> {

            private final Map<String, String> copies;
            private final CFGNode topNode;

            public CFGBlockVarReplacementVisitor(Map<String, String> copies, CFGNode node) {
                this.copies = copies;
                this.topNode = node;
            }

            public CFGNode replaceBlock() {
                var nextNode = topNode;
                while (!(nextNode instanceof CFGStubNode)) {
                    nextNode = nextNode.accept(this);
                }
                return topNode;
            }

            @Override
            public CFGNode visit(CFGCallNode n) {
                final var updatedArgs = n.call.args().stream().map(arg -> {
                    return IRTempReplacer.replace(arg, copies);
                }).collect(Collectors.toList());
                n.call = new IRCallStmt(n.location(),
                        n.call.collectors(), n.call.target(), updatedArgs);
                n.refreshDfaSets();

                copies.keySet().removeAll(n.call.collectors());
                copies.values().removeAll(n.call.collectors());
                return n.outNode();
            }

            @Override
            public CFGNode visit(CFGVarAssignNode n) {
                n.value = IRTempReplacer.replace(n.value, copies);
                n.refreshDfaSets();

                copies.keySet().removeAll(n.kills());
                copies.values().removeAll(n.kills());
                copies.putAll(n.gens());
                return n.outNode();
            }

            @Override
            public CFGNode visit(CFGMemAssignNode n) {
                n.target = IRTempReplacer.replace(n.target, copies);
                n.value = IRTempReplacer.replace(n.value, copies);
                n.refreshDfaSets();

                copies.keySet().removeAll(n.kills());
                copies.values().removeAll(n.kills());
                return n.outNode();
            }


            @Override
            public CFGNode visit(CFGReturnNode n) {
                throw new AssertionError("Node not allowed in block");
            }

            @Override
            public CFGNode visit(CFGStartNode n) {
                throw new AssertionError("Node not allowed in block");
            }

            @Override
            public CFGNode visit(CFGSelfLoopNode n) {
                throw new AssertionError("Node not allowed in block");
            }

            @Override
            public CFGNode visit(CFGBlockNode n) {
                throw new AssertionError("Node not allowed in block");
            }

            @Override
            public CFGNode visit(CFGIfNode n) {
                throw new AssertionError("Node not allowed in block");
            }
        }

    }

}
