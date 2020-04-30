package cyr7.cfg.nodes;

import java.util.ArrayList;
import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNode;

public class CFGMemAssignNode extends CFGNode {

    public final IRExpr location;
    public final IRExpr value;
    private final CFGNode out;

    public CFGMemAssignNode(IRNode source, IRExpr location, IRExpr value,
            CFGNode out) {
        super(source);
        this.location = location;
        this.value = value;
        this.out = out;

        this.updateIns();
    }

    @Override
    public List<CFGNode> in() {
        return this.in;
    }

    @Override
    public List<CFGNode> out() {
        return List.of(this.out);
    }

    @Override
    public <T> void accept(AbstractCFGVisitor<T> visitor) {
        visitor.visit(this);
    }

}