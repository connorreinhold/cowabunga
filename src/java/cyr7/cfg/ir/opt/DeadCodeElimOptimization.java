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
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.interpret.Configuration;

public class DeadCodeElimOptimization {

    private Set<CFGNode> visited;
    private Queue<CFGNode> nextNodes;

    public DeadCodeElimOptimization() {
        this.visited = Collections.emptySet();
        this.nextNodes = new LinkedList<>();
    }

    public CFGStartNode optimize(CFGNode start) {
        // Mapping from CFGNode to the out lattices.
        CFGStartNode startNode = (CFGStartNode)start;
        var result = WorklistAnalysis.analyze((CFGStartNode)start,
                                   IrLiveVariableAnalysis.INSTANCE);
        this.visited = new HashSet<>();
        this.nextNodes = new LinkedList<>();
        this.nextNodes.add(start);

        final ReplaceDeadCodeVisitor visitor = new ReplaceDeadCodeVisitor(result);

        while (!this.nextNodes.isEmpty()) {
            var next = this.nextNodes.remove();

            // Replaces variables with copies in the mapping, and updates the neighboring edges.
            next.accept(visitor);

            this.visited.add(next);

            for (CFGNode in: next.in()) {
                if (!this.visited.contains(in)) {
                    this.nextNodes.add(in);
                }
            }
            for (CFGNode out: next.out()) {
                if (!this.visited.contains(out)) {
                    this.nextNodes.add(out);
                }
            }

        }
        return startNode;
    }

    private class ReplaceDeadCodeVisitor implements IrCFGVisitor<CFGNode> {

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
                    && defined.contains(n.variable)) {
                // Remove n from the graph.
                for (CFGNode incoming: n.in()) {
                    for (CFGNode out: n.out()) {
                        if (!incoming.out().contains(out)
                            && !out.in().contains(incoming)) {
                            incoming.out().add(out);
                            out.in().add(incoming);
                        }
                    }
                }
                return n;
            } else {
                return n;
            }
        }

        @Override
        public CFGNode visit(CFGMemAssignNode n) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public CFGNode visit(CFGReturnNode n) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public CFGNode visit(CFGStartNode n) {
            // TODO Auto-generated method stub
            return null;
        }
    }

}
