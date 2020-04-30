package cyr7.cfg.nodes;

import java.util.List;
import cyr7.cfg.visitor.AbstractCFGVisitor;

public class CFGReturnNode extends CFGNode {
    private final List<CFGNode> in;

    public CFGReturnNode(List<CFGNode> in) {
        this.in = in;
    }

    @Override
    public List<CFGNode> in() {
        return this.in;
    }

    @Override
    public List<CFGNode> out() {
        return List.of();
    }

    @Override
    public <T> void accept(AbstractCFGVisitor<T> visitor) {
        visitor.visit(this);
    }

}
