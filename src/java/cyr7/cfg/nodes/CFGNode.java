package cyr7.cfg.nodes;

import java.util.ArrayList;
import java.util.List;
import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRNode;

public abstract class CFGNode {

    protected final List<CFGNode> in;

    private final IRNode source;

    protected CFGNode(IRNode source) {
        this.in = new ArrayList<>(1);
        this.source = source;
    }

    public List<CFGNode> in() {
        return in;
    }

    public abstract List<CFGNode> out();

    public abstract <T> void accept(AbstractCFGVisitor<T> visitor);

    protected final void updateIns() {
        for (CFGNode node : out()) {
            node.in().add(this);
        }
    }

}