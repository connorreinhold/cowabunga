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
import cyr7.cfg.ir.dfa.DfaResult;
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

    private CCPOptimization() {}

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
                if (!visited.contains(out) && !nextNodes.contains(out)) {
                    nextNodes.add(out);
                }
            }

        }
        return startNode;
    }

    // TODO: Removing nodes if they are unreachable
    private static class CcpCfgTransformationVisitor implements IrCFGVisitor<CFGNode> {

        private final Map<CFGNode, LatticeElement> incomingLattices;
        private final Map<CFGNode, Map<CFGNode, LatticeElement>> outgoingLattices;

        public CcpCfgTransformationVisitor(DfaResult<LatticeElement> ccpResult) {
            // May be it is more helpful to know both the outs and ins of nodes.
            this.incomingLattices = ccpResult.in();
            this.outgoingLattices = ccpResult.out();
        }

        @Override
        public CFGNode visit(CFGCallNode n) {
            final var lattice = incomingLattices.get(n);
            final IRCallStmt call = n.call;
            List<IRExpr> updatedArgs = call.args().stream().map(arg -> {
                return IRTempToConstant.replace(arg, lattice);
            }).collect(Collectors.toList());

            n.call = new IRCallStmt(n.location(),
                                    call.collectors(),
                                    call.target(),
                                    updatedArgs);
            return n;
        }

        @Override
        public CFGNode visit(CFGIfNode n) {
            final var incomingLattice = incomingLattices.get(n);
            final var outgoingLattice = outgoingLattices.get(n);

            final var trueBranch = n.trueBranch();
            final var falseBranch = n.falseBranch();
            if (incomingLattice.unreachable()) {
                // Remove this node as the incoming node of the next.
                trueBranch.in().remove(n);
                falseBranch.in().remove(n);
                return n;
            }

            n.cond = IRTempToConstant.replace(n.cond, incomingLattice);
            if (outgoingLattice.get(trueBranch).unreachable()) {
                // Remove this node and link the previous nodes to the
                // false branch
                falseBranch.in().remove(n);
                for (CFGNode incoming: n.in()) {
                    incoming.replaceOutEdge(n, falseBranch);
                }
            } else if (outgoingLattice.get(falseBranch).unreachable()) {
                // Remove this node and link the previous nodes to the
                // true branch
                trueBranch.in().remove(n);
                for (CFGNode incoming: n.in()) {
                    incoming.replaceOutEdge(n, trueBranch);
                }
            }
            return n;
        }

        @Override
        public CFGNode visit(CFGVarAssignNode n) {
            final var lattice = incomingLattices.get(n);
            if (lattice.unreachable()) {
                // Remove this node as the incoming node of the next.
                n.outNode().in().remove(n);
                return n;
            }
            n.value = IRTempToConstant.replace(n.value, lattice);
            return n;
        }

        @Override
        public CFGNode visit(CFGMemAssignNode n) {
            final var lattice = incomingLattices.get(n);
            if (lattice.unreachable()) {
                // Remove this node as the incoming node of the next.
                n.outNode().in().remove(n);
                return n;
            }
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
            final var lattice = incomingLattices.get(n);
            if (lattice.unreachable()) {
                // Remove this node as the incoming node of the next.
                n.outNode().in().remove(n);
                return n;
            }
            return n;
        }

        @Override
        public CFGNode visit(CFGSelfLoopNode n) {
            return n;
        }

    }

}
