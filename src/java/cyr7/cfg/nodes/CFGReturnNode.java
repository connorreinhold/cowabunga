package cyr7.cfg.nodes;

import java.util.List;
import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRNode;

public class CFGReturnNode extends CFGNode {

    public CFGReturnNode(IRNode source, List<CFGNode> in) {
        super(source);
        this.updateIns();
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
