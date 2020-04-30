package cyr7.cfg.nodes;

import java.util.List;
import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRNode;

public class CFGStartNode extends CFGNode {
    private final CFGNode out;

    public CFGStartNode(IRNode source, CFGNode out) {
        super(source);
        this.out = out;
        this.updateIns();
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
