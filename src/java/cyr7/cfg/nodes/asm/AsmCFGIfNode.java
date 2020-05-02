package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMArg;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGIfNode extends AsmCFGNode {

    public enum ASMCompareType {
        LT, LTE, GT, GTE, EQ, NEQ
    }

    private final ASMCompareType type;
    private AsmCFGNode trueBranch;
    private AsmCFGNode falseBranch;
    public final ASMArg leftOperand;
    public final ASMArg rightOperand;

    public AsmCFGIfNode(Location location, AsmCFGNode trueBranch,
            AsmCFGNode falseBranch, ASMCompareType type,
            ASMArg leftOperand, ASMArg rightOperand) {
        super(location);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
        this.type = type;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
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
        return "(if " + this.leftOperand.getIntelArg()
        + type.toString() + this.rightOperand.getIntelArg() + ")";
    }
}