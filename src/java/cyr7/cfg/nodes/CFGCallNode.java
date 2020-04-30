package cyr7.cfg.nodes;

import java.util.ArrayList;
import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRCallStmt;

public class CFGCallNode extends CFGNode {

    // This includes both procedures and function calls
    public final IRCallStmt call;
    private final List<CFGNode> in;
    private final CFGNode out;

    public CFGCallNode(IRCallStmt call, CFGNode out) {
        this.call = call;
        this.in = new ArrayList<>();
        this.out = out;
    }

    @Override
    public List<CFGNode> in() {
        return this.in;
    }

    @Override
    public List<CFGNode> out() {
        return List.of(out);
    }

    @Override
    public <T> void accept(AbstractCFGVisitor<T> visitor) {
        visitor.visit(this);
    }

}
