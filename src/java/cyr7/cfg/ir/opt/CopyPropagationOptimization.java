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

            // Replaces variables with copies in the mapping, and updates the neighboring edges.
            next.accept(this);

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

    private CFGNode replaceEdges(CFGNode original, CFGNode current, CFGStubNode ... stubs) {
        for (CFGNode incoming: original.in()) {
            incoming.out().remove(original);
            incoming.out().add(current);
        }
        for (CFGStubNode stub: stubs) {
            current.out().remove(stub);
        }
        for (CFGNode outgoing: original.out()) {
            outgoing.in().remove(original);
            outgoing.in().add(current);
        }
        return current;
    }

    @Override
    public CFGNode visit(CFGCallNode n) {
        var outEdge = n.out().get(0);
        var lattice = this.result.get(n).get(outEdge);

        List<IRExpr> args = n.call.args().stream().map(arg -> {
            return ReplaceTempIRVisitor.instance.replace(arg, lattice.copies);
        }).collect(Collectors.toList());
        var call = new IRCallStmt(n.location(), n.call.collectors(),
                                                n.call.target(), args);
        var stub = new CFGStubNode();
        var replacedCFGNode = new CFGCallNode(n.location(), call, stub);
        return this.replaceEdges(n, replacedCFGNode, stub);
    }


    @Override
    public CFGNode visit(CFGIfNode n) {
        var outEdge = n.out().get(0);  // For copy propagation, true and false are the same.
        var lattice = this.result.get(n).get(outEdge);

        var condition = ReplaceTempIRVisitor.instance.replace(n.cond, lattice.copies);
        var trueStub = new CFGStubNode();
        var falseStub = new CFGStubNode();
        var replacedCFGNode = new CFGIfNode(n.location(), trueStub, falseStub, condition);
        return this.replaceEdges(n, replacedCFGNode, trueStub, falseStub);
    }

    @Override
    public CFGNode visit(CFGVarAssignNode n) {
        var outEdge = n.out().get(0);
        var lattice = this.result.get(n).get(outEdge);

        var value = ReplaceTempIRVisitor.instance.replace(n.value, lattice.copies);
        var stub = new CFGStubNode();
        var replacedCFGNode = new CFGVarAssignNode(n.location(), n.variable, value, stub);
        return this.replaceEdges(n, replacedCFGNode, stub);
    }

    @Override
    public CFGNode visit(CFGMemAssignNode n) {
        var outEdge = n.out().get(0);
        var lattice = this.result.get(n).get(outEdge);

        var value = ReplaceTempIRVisitor.instance.replace(n.value, lattice.copies);
        var mem = ReplaceTempIRVisitor.instance.replace(n.target, lattice.copies);
        var stub = new CFGStubNode();
        var replacedCFGNode = new CFGMemAssignNode(n.location(), mem, value, stub);
        return this.replaceEdges(n, replacedCFGNode, stub);
    }

    @Override
    public CFGNode visit(CFGReturnNode n) {
        return n;
    }

    @Override
    public CFGNode visit(CFGStartNode n) {
        return n;
    }

}
