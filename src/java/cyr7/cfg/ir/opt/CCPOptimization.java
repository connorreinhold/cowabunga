package cyr7.cfg.ir.opt;

import java.util.ArrayDeque;
import java.util.Collections;
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

    private static class CcpCfgTransformationVisitor implements IrCFGVisitor<CFGNode> {

        private final Map<CFGNode, LatticeElement> incomingLattices;
        private final Map<CFGNode, Map<CFGNode, LatticeElement>> outgoingLattices;

        public CcpCfgTransformationVisitor(DfaResult<LatticeElement> ccpResult) {
            // May be it is more helpful to know both the outs and ins of nodes.
            this.incomingLattices = ccpResult.in();
            this.outgoingLattices = ccpResult.out();
        }

        @Override
        public CFGNode visit(CFGBlockNode n) {
            var incoming = incomingLattices.get(n);
            if (incoming.unreachable()) {
                n.outNode().in().removeAll(Collections.singleton(n));
                return n;
            } else {
                n.block = new CFGBlockDeadCodeVisitor(incoming, n).replaceBlock();
                n.refreshDfaSets();
                return n;
            }
        }

        @Override
        public CFGNode visit(CFGCallNode n) {
            final var incomingLattice = incomingLattices.get(n);
            if (incomingLattice.unreachable()) {
                // Remove this node as the incoming node of the next.
                n.outNode().in().removeAll(Collections.singleton(n));
                return n;
            }
            final var lattice = incomingLattices.get(n);
            final IRCallStmt call = n.call;
            List<IRExpr> updatedArgs = call.args().stream().map(arg -> {
                return IRTempToConstant.replace(arg, lattice);
            }).collect(Collectors.toList());

            n.call = new IRCallStmt(n.location(),
                                    call.collectors(),
                                    call.target(),
                                    updatedArgs);
            n.refreshDfaSets();
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
                trueBranch.in().removeAll(Collections.singleton(n));
                falseBranch.in().removeAll(Collections.singleton(n));
                return n;
            }

            n.cond = IRTempToConstant.replace(n.cond, incomingLattice);
            if (outgoingLattice.get(trueBranch).unreachable()) {
                // Remove this node and link the previous nodes to the
                // false branch
                trueBranch.in().removeAll(Collections.singleton(n));
                for (CFGNode incoming: n.in()) {
                    incoming.replaceOutEdge(n, falseBranch);
                }
            } else if (outgoingLattice.get(falseBranch).unreachable()) {
                // Remove this node and link the previous nodes to the
                // true branch
                falseBranch.in().removeAll(Collections.singleton(n));
                for (CFGNode incoming: n.in()) {
                    incoming.replaceOutEdge(n, trueBranch);
                }
            }
            n.refreshDfaSets();
            return n;
        }

        @Override
        public CFGNode visit(CFGVarAssignNode n) {
            final var lattice = incomingLattices.get(n);
            if (lattice.unreachable()) {
                // Remove this node as the incoming node of the next.
                n.outNode().in().removeAll(Collections.singleton(n));
                return n;
            }
            n.value = IRTempToConstant.replace(n.value, lattice);
            n.refreshDfaSets();
            return n;
        }

        @Override
        public CFGNode visit(CFGMemAssignNode n) {
            final var lattice = incomingLattices.get(n);
            if (lattice.unreachable()) {
                // Remove this node as the incoming node of the next.
                n.outNode().in().removeAll(Collections.singleton(n));
                return n;
            }
            n.value = IRTempToConstant.replace(n.value, lattice);
            n.target = IRTempToConstant.replace(n.target, lattice);
            n.refreshDfaSets();
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
                n.outNode().in().removeAll(Collections.singleton(n));
                return n;
            }
            return n;
        }

        @Override
        public CFGNode visit(CFGSelfLoopNode n) {
            return n;
        }


        private static class CFGBlockDeadCodeVisitor implements IrCFGVisitor<CFGNode> {

            private LatticeElement elements;
            private final CFGNode firstNode;

            public CFGBlockDeadCodeVisitor(LatticeElement elements, CFGBlockNode node) {
                this.elements = elements;
                this.firstNode = node.block;
            }

            public CFGNode replaceBlock() {
                var nextNode = firstNode;
                while (!(nextNode instanceof CFGStubNode)) {
                    nextNode = nextNode.accept(this);
                    elements = nextNode.acceptForward(CCPAnalysis.INSTANCE.transfer(), elements).get(0);
                }
                return this.firstNode;
            }

            @Override
            public CFGNode visit(CFGCallNode n) {
                final IRCallStmt call = n.call;
                List<IRExpr> updatedArgs = call.args().stream().map(arg -> {
                    return IRTempToConstant.replace(arg, elements);
                }).collect(Collectors.toList());
                n.call = new IRCallStmt(n.location(),
                                        call.collectors(),
                                        call.target(),
                                        updatedArgs);
                n.refreshDfaSets();
                return n.outNode();
            }

            @Override
            public CFGNode visit(CFGVarAssignNode n) {
                n.value = IRTempToConstant.replace(n.value, elements);
                n.refreshDfaSets();
                return n;
            }

            @Override
            public CFGNode visit(CFGMemAssignNode n) {
                n.value = IRTempToConstant.replace(n.value, elements);
                n.target = IRTempToConstant.replace(n.target, elements);
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
