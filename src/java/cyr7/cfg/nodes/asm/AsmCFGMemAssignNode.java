package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.ir.nodes.IRExpr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGMemAssignNode extends AsmCFGNode {

    public final IRExpr target;
    public final IRExpr value;
    private AsmCFGNode out;

    public AsmCFGMemAssignNode(
        Location location,
        IRExpr target,
        IRExpr value,
        AsmCFGNode out) {

        super(location);
        this.target = target;
        this.value = value;
        this.out = out;

        this.updateIns();
    }

    @Override
    public List<AsmCFGNode> out() {
        return List.of(this.out);
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        if (out == stub) {
            this.out = n;
            this.updateIns();
        } else {
            throw new UnsupportedOperationException(
                    "Cannot change out node unless it was originally a stub node.");
        }
    }

    @Override
    public String toString() {
        return "([" + target.label() + "] = " + value.label() + ")";
    }
}