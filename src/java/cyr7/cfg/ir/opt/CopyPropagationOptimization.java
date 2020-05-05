package cyr7.cfg.ir.opt;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cfg.ir.dfa.CopyPropagationAnalysis;
import cyr7.cfg.ir.dfa.CopyPropagationAnalysis.CopyPropLattice;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRTemp;

public class CopyPropagationOptimization implements IrCFGVisitor<CFGNode> {

    private final Map<CFGNode, Map<CFGNode, CopyPropLattice>> result;

    private final Set<CFGNode> visited;
    private final Queue<CFGNode> nextNodes;

    public CopyPropagationOptimization(CFGNode start) {
        // Mapping from CFGNode to the out lattices.
        this.result = WorklistAnalysis.analyze((CFGStartNode)start,
                CopyPropagationAnalysis.INSTANCE);
        this.visited = new HashSet<>();
        this.nextNodes = new LinkedList<>();
        this.nextNodes.add(start);

        while (!this.nextNodes.isEmpty()) {
            var next = this.nextNodes.remove();
            var result = next.accept(this);

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
    }

    @Override
    public CFGNode visit(CFGCallNode n) {
        var outEdge = n.out().get(0);

        var lattice = this.result.get(n).get(outEdge);

        List<IRExpr> args = n.call.args().stream().map(arg -> {
            if (arg instanceof IRTemp &&
                    lattice.copies.containsKey(((IRTemp)arg).name())) {
                String variable = ((IRTemp)arg).name();
                return new IRTemp(arg.location(), lattice.copies.get(variable));
            }
            return arg;
        }).collect(Collectors.toList());
        var call = new IRCallStmt(n.location(), n.call.collectors(),
                                                n.call.target(), args);
        var stub = new CFGStubNode();
        var replacedCFGNode = new CFGCallNode(n.location(), call, stub);

        for (CFGNode incoming: n.in()) {
            incoming.out().remove(n);
            incoming.out().add(replacedCFGNode);
        }
        replacedCFGNode.convertFromStub(stub, n.out().get(0));
        return replacedCFGNode;
    }

    @Override
    public CFGNode visit(CFGIfNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CFGNode visit(CFGVarAssignNode n) {
        // TODO Auto-generated method stub
        return null;
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
