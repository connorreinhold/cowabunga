package cyr7.cfg.ir.opt;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cfg.ir.dfa.CCPAnalysis;
import cyr7.cfg.ir.dfa.CCPAnalysis.LatticeElement;
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
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRExpr;

public class CCPOptimization {

    public static CFGStartNode optimize(CFGNode start) {
        CFGStartNode startNode = (CFGStartNode)start;
        final var ccpResult = WorklistAnalysis.analyze(startNode,
                                CCPAnalysis.INSTANCE);
        final var visitor = new CcpCfgTransformationVisitor(ccpResult);
        final Set<CFGNode> visited = new HashSet<>();
        final Queue<CFGNode> nextNodes = new ArrayDeque<>();
        nextNodes.add(start);

        while (!nextNodes.isEmpty()) {
            var next = nextNodes.remove();

            // Replaces variables with copies in the mapping,
            // and updates the neighboring edges.
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

    private static class CcpCfgTransformationVisitor implements IrCFGVisitor<CFGNode> {

        private final Map<CFGNode, LatticeElement> incomingLattice;

        public CcpCfgTransformationVisitor(Map<CFGNode, LatticeElement> ccpResult) {
            // May be it is more helpful to know both the outs and ins of nodes.
            this.incomingLattice = ccpResult;
        }

        @Override
        public CFGNode visit(CFGCallNode n) {
            final var lattice = incomingLattice.get(n);
            final IRCallStmt call = n.call;
            List<IRExpr> updatedArgs = call.args().stream().map(arg -> {
                return IRTempToConstant.replace(arg, lattice);
            }).collect(Collectors.toList());

            n.call = new IRCallStmt(n.location(), call.collectors(), call.target(), updatedArgs);
            return n;
        }

        @Override
        public CFGNode visit(CFGIfNode n) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public CFGNode visit(CFGVarAssignNode n) {
            final var lattice = incomingLattice.get(n);
            n.value = IRTempToConstant.replace(n.value, lattice);
            return n;
        }

        @Override
        public CFGNode visit(CFGMemAssignNode n) {
            final var lattice = incomingLattice.get(n);
            n.value = IRTempToConstant.replace(n.value, lattice);
            n.target = IRTempToConstant.replace(n.target, lattice);
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
