package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.ir.nodes.IRExpr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGIfNode extends AsmCFGNode {

    private AsmCFGNode trueBranch;
    private AsmCFGNode falseBranch;
    public final IRExpr cond;

    public AsmCFGIfNode(Location location, AsmCFGNode trueBranch,
            AsmCFGNode falseBranch, IRExpr cond) {
        super(location);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
        this.cond = cond;

        this.updateIns();
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public AsmCFGNode falseBranch() {
        return falseBranch;
    }

    public AsmCFGNode trueBranch() {
        return trueBranch;
    }

    @Override
    public List<AsmCFGNode> out() {
        return List.of(trueBranch, falseBranch);
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        if (trueBranch == stub) {
            this.trueBranch = n;
        } else if (falseBranch == stub) {
            this.falseBranch = n;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot change out node unless it was originally a stub node.");
        }
        this.updateIns();
    }

    @Override
    public String toString() {
        return "(if " + cond.label() + ")";
    }
}