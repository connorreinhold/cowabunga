package cyr7.cfg.nodes;

import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRExpr;

public class CFGMemAssignNode extends CFGNode {

    private final List<CFGNode> in;
    public final IRExpr location;
    public final IRExpr value;
    private final List<CFGNode> out;

    public CFGMemAssignNode(List<CFGNode> in, IRExpr location, IRExpr value,
            List<CFGNode> out) {
        this.in = in;
        this.location = location;
        this.value = value;
        this.out = out;
    }

    @Override
    public List<CFGNode> in() {
        return this.in;
    }

    @Override
    public List<CFGNode> out() {
        return this.out;
    }

    @Override
    public <T> void accept(AbstractCFGVisitor<T> visitor) {
        visitor.visit(this);
    }

}