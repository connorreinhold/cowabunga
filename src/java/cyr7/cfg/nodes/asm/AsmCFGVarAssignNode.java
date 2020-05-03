package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMRegSize;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGVarAssignNode extends AsmCFGNode {
    public final String variable;
    public final ASMArg value;
    public final ASMRegSize size;
    private AsmCFGNode outNode;

    public AsmCFGVarAssignNode(
        Location location,
        String variable,
        ASMArg value,
        ASMRegSize size,
        AsmCFGNode outNode) {

        super(location);
        this.variable = variable;
        this.value = value;
        this.outNode = outNode;
        this.size = size;

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
        return "(" + variable + " = " + value.getIntelArg() + ")";
    }
}
