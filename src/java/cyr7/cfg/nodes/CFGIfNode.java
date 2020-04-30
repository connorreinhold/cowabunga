package cyr7.cfg.nodes;

import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRExpr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGIfNode extends CFGNode {

    private final CFGNode trueBranch;
    private final CFGNode falseBranch;
    public final IRExpr guard;

    public CFGIfNode(Location location, CFGNode trueBranch,
            CFGNode falseBranch, IRExpr guard) {
        super(location);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
        this.guard = guard;

        this.updateIns();
    }

    @Override
    public <T> T accept(AbstractCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public CFGNode falseBranch() {
        return falseBranch;
    }
    
    public CFGNode trueBranch() {
        return trueBranch;
    }
    
    @Override
    public List<CFGNode> out() {
        return List.of(trueBranch, falseBranch);
    }
    


}