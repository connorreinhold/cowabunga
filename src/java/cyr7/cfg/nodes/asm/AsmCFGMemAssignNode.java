package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMAddrExpr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGMemAssignNode extends AsmCFGNode {

    public final ASMAddrExpr address;
    public final AsmCFGOperationExpr value;
    private AsmCFGNode out;

    public AsmCFGMemAssignNode(
        Location location,
        ASMAddrExpr address,
        AsmCFGOperationExpr value,
        AsmCFGNode out) {

        super(location);
        this.address = address;
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
        return "([" + address.getIntelExpr() + "] = " + value.getIntelArg() + ")";
    }
}