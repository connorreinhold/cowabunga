package cyr7.cfg.nodes;

import java.util.ArrayList;
import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRNode;

public class CFGIfNode extends CFGNode {

    private final CFGNode trueBranch, falseBranch;

    public CFGIfNode(IRNode source, CFGNode trueBranch, CFGNode falseBranch) {
        super(source);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;

        this.updateIns();
    }

    @Override
    public <T> void accept(AbstractCFGVisitor<T> visitor) {
        visitor.visit(this);
    }

    @Override
    public List<CFGNode> out() {
        return List.of(trueBranch, falseBranch);
    }

}