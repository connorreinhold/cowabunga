package cyr7.cfg.nodes;

import java.util.ArrayList;
import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRNode;

public class CFGCallNode extends CFGNode {

    // This includes both procedures and function calls
    public final IRCallStmt call;
    private final CFGNode out;

    public CFGCallNode(IRNode source, IRCallStmt call, CFGNode out) {
        super(source);
        this.call = call;
        this.out = out;

        this.updateIns();
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
