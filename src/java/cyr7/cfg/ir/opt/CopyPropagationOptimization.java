package cyr7.cfg.ir.opt;

import java.util.Collections;
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
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRExpr;

public class CopyPropagationOptimization implements IrCFGVisitor<CFGNode> {

    private Map<CFGNode, Map<CFGNode, CopyPropLattice>> result;

    private Set<CFGNode> visited;
    private Queue<CFGNode> nextNodes;

    public CopyPropagationOptimization() {
        this.result = Collections.emptyMap();
        this.visited = Collections.emptySet();
        this.nextNodes = new LinkedList<>();
    }

    public CFGStartNode optimize(CFGNode start) {
        // Mapping from CFGNode to the out lattices.
        CFGStartNode startNode = (CFGStartNode)start;
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

            for (CFGNode out: next.out()) {
                if (!this.visited.contains(out)) {
                    this.nextNodes.add(out);
                }
            }

        }
        return startNode;
    }

    @Override
    public CFGNode visit(CFGCallNode n) {
        final var outEdge = n.out().get(0);
        final var lattice = this.result.get(n).get(outEdge);

        final List<IRExpr> args = n.call.args().stream().map(arg -> {
            return ReplaceTempIRVisitor.instance.replace(arg, lattice.copies);
        }).collect(Collectors.toList());
        final var call = new IRCallStmt(n.location(), n.call.collectors(),
                                                n.call.target(), args);
        n.call = call;
        return n;
    }


    @Override
    public CFGNode visit(CFGIfNode n) {
        final var outEdge = n.out().get(0);  // For copy propagation, true and false are the same.
        final var lattice = this.result.get(n).get(outEdge);

        final var condition = ReplaceTempIRVisitor.instance.replace(n.cond, lattice.copies);
        n.cond = condition;
        return n;
    }

    @Override
    public CFGNode visit(CFGVarAssignNode n) {
        final var outEdge = n.out().get(0);
        final var lattice = this.result.get(n).get(outEdge);

        final var value = ReplaceTempIRVisitor.instance.replace(n.value, lattice.copies);
        n.value = value;
        return n;
    }

    @Override
    public CFGNode visit(CFGMemAssignNode n) {
        final var outEdge = n.out().get(0);
        final var lattice = this.result.get(n).get(outEdge);

        final var value = ReplaceTempIRVisitor.instance.replace(n.value, lattice.copies);
        final var mem = ReplaceTempIRVisitor.instance.replace(n.target, lattice.copies);
        n.value = value;
        n.target = mem;
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

}
