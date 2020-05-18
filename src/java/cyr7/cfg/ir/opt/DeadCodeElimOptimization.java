package cyr7.cfg.ir.opt;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cfg.ir.dfa.IrLiveVariableAnalysis;
import cyr7.cfg.ir.dfa.IrLiveVariableAnalysis.IrLiveVarLattice;
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
import cyr7.ir.interpret.Configuration;
import cyr7.ir.visit.IRExprVarsVisitor;

public class DeadCodeElimOptimization {

    private DeadCodeElimOptimization() {}

    /**
     * Performs one passing of dead code elimination.
     * <p>
     * A CFGNode is considered "dead" if it is a variable assignment node, but
     * that assigned variable is never used in the rest of program flow.
     *
     * @param start The {@link CFGStartNode start} node of the IR CFG.
     * @return The same {@code start} node, but the CFG has been optimized with
     *         one passing of dead code elimination.
     */
    public static CFGStartNode optimize(CFGNode start) {
        // Mapping from CFGNode to the out lattices.
        CFGStartNode startNode = (CFGStartNode)start;
        var result = WorklistAnalysis.analyze((CFGStartNode)start,
                                   IrLiveVariableAnalysis.INSTANCE);
        Set<CFGNode> visited = new HashSet<>();
        Queue<CFGNode> nextNodes = new LinkedList<>();
        nextNodes.add(start);

        final ReplaceDeadCodeVisitor visitor = new ReplaceDeadCodeVisitor(result);

        while (!nextNodes.isEmpty()) {
            var next = nextNodes.remove();

            /**
             * If a CFG node n may be eliminated because it is "dead", then
             * the out nodes of the nodes n' that precede n will be changed
             * to the out node of n.
             */
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

    private static class ReplaceDeadCodeVisitor implements IrCFGVisitor<CFGNode> {

        private Map<CFGNode, IrLiveVarLattice> result;

        private boolean isAReturn(String n) {
            return n.startsWith(Configuration.ABSTRACT_RET_PREFIX);
        }

        protected ReplaceDeadCodeVisitor(Map<CFGNode, IrLiveVarLattice> result) {
            this.result = Collections.unmodifiableMap(result);
        }

        /**
         * Do not remove even if assigned variable is dead, since side effects
         * may occur.
         */
        @Override
        public CFGNode visit(CFGCallNode n) {
            return n;
        }

        @Override
        public CFGNode visit(CFGIfNode n) {
            return n;
        }

        @Override
        public CFGNode visit(CFGVarAssignNode n) {
            // If variable is not defined, then remove from graph. Unless
            // it is being assigned to a return value temp.
            Set<String> liveVars = this.result.get(n).liveVars;
            if (!this.isAReturn(n.variable)
                    && !liveVars.contains(n.variable)) {
                // Remove n from the graph.
                // For every incoming node, replace their out node with
                // this node's out node.
                for (CFGNode incoming: n.in()) {
                    for (CFGNode out: n.out()) {
                        incoming.replaceOutEdge(n, out);
                    }
                }
                n.outNode().in().remove(n);
                return n;
            } else {
                return n;
            }
        }

        @Override
        public CFGNode visit(CFGBlockNode n) {
            n.block = new CFGBlockDeadCodeVisitor(this.result.get(n).liveVars, n)
                        .replaceBlock();
            n.refreshDfaSets();
            return n;
        }

        @Override
        public CFGNode visit(CFGMemAssignNode n) {
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

        private static class CFGBlockDeadCodeVisitor implements IrCFGVisitor<CFGNode> {

            private final Set<String> defined;
            private final CFGNode firstNode;
            private final Deque<CFGNode> ordering;

            private boolean isAReturn(String n) {
                return n.startsWith(Configuration.ABSTRACT_RET_PREFIX);
            }

            public CFGBlockDeadCodeVisitor(Set<String> defined, CFGBlockNode node) {
                this.defined = new HashSet<>(defined);
                this.firstNode = node.block;
                this.ordering = new ArrayDeque<>();

                var topNode = node.block;
                while (!(topNode instanceof CFGStubNode)) {
                    this.ordering.addFirst(topNode);
                    topNode = topNode.out().get(0);
                }
            }

            public CFGNode replaceBlock() {
                while (!ordering.isEmpty()) {
                    final var nextNode = ordering.pop();
                    nextNode.accept(this);
                }
                return this.firstNode;
            }

            @Override
            public CFGNode visit(CFGCallNode n) {
                defined.removeAll(n.call.collectors());
                defined.addAll(n.call.args().stream()
                        .flatMap(arg -> arg.accept(IRExprVarsVisitor.INSTANCE).stream())
                        .collect(Collectors.toSet()));
                n.refreshDfaSets();
                return n;
            }

            @Override
            public CFGNode visit(CFGVarAssignNode n) {
                if (!this.isAReturn(n.variable)
                        && !defined.contains(n.variable)) {
                    // Remove this node
                    for (CFGNode incoming: n.in()) {
                        incoming.replaceOutEdge(n, n.outNode());
                    }
                    n.outNode().in().remove(n);
                } else {
                    defined.remove(n.variable);
                    defined.addAll(n.value.accept(IRExprVarsVisitor.INSTANCE));
                }
                return n;
            }

            @Override
            public CFGNode visit(CFGMemAssignNode n) {
                defined.addAll(n.target.accept(IRExprVarsVisitor.INSTANCE));
                defined.addAll(n.value.accept(IRExprVarsVisitor.INSTANCE));
                n.refreshDfaSets();
                return n;
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
