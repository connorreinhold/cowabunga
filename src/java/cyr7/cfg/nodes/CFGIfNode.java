package cyr7.cfg.nodes;

import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGIfNode extends CFGNode {

    private final CFGNode trueBranch, falseBranch;

    public CFGIfNode(Location location, CFGNode trueBranch,
            CFGNode falseBranch) {
        super(location);
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