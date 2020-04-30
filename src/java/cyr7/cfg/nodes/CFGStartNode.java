package cyr7.cfg.nodes;

import java.util.List;
import cyr7.cfg.visitor.AbstractCFGVisitor;

public class CFGStartNode extends CFGNode {
    private final CFGNode out;

    public CFGStartNode(CFGNode out) {
        this.out = out;
    }

    @Override
    public List<CFGNode> out() {
        return List.of(this.out);
    }

    @Override
    public List<CFGNode> in() {
        return List.of();
    }

    @Override
    public <T> void accept(AbstractCFGVisitor<T> visitor) {
        visitor.visit(this);
    }

}
