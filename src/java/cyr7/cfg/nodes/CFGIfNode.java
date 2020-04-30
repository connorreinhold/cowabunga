package cyr7.cfg.nodes;

import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;

public class CFGIfNode extends CFGNode {

    private final List<CFGNode> in;
    private final CFGNode trueBranch, falseBranch;

    public CFGIfNode(List<CFGNode> in, CFGNode trueBranch,
            CFGNode falseBranch) {
        this.in = in;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public <T> void accept(AbstractCFGVisitor<T> visitor) {
        visitor.visit(this);
    }

    @Override
    public List<CFGNode> in() {
        return this.in;
    }

    @Override
    public List<CFGNode> out() {
        return List.of(trueBranch, falseBranch);
    }
}