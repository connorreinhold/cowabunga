package cyr7.cfg.ir.opt;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import cyr7.cfg.ir.dfa.IrLiveVariableAnalysis;
import cyr7.cfg.ir.dfa.IrLiveVariableAnalysis.IrLiveVarLattice;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.interpret.Configuration;

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
                if (!visited.contains(out)) {
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
            Set<String> defined = this.result.get(n).liveVars;
            if (!this.isAReturn(n.variable)
                    && !defined.contains(n.variable)) {
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
    }

}
