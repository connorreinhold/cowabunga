package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMReg;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGRegAssignNode extends AsmCFGNode {
    public final ASMReg register;
    public final ASMArg value;
    private AsmCFGNode outNode;

    public AsmCFGRegAssignNode(
        Location location,
        ASMReg register,
        ASMArg value,
        AsmCFGNode outNode) {

        super(location);
        this.register = register;
        this.value = value;
        this.outNode = outNode;

        this.updateIns();
    }

    @Override
    public List<AsmCFGNode> out() {
        return List.of(outNode);
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        if (outNode == stub) {
            this.outNode = n;
            this.updateIns();
        } else {
            throw new UnsupportedOperationException(
                "Cannot change out node unless it was originally a stub node.");
        }
    }

    @Override
    public String toString() {
        return "(" + register + " = " + value.getIntelArg() + ")";
    }
}
