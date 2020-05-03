package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGReturnNode extends AsmCFGNode {

    protected AsmCFGReturnNode(ASMInstr sourceInstr) {
        super(sourceInstr);
    }

    @Override
    public List<AsmCFGNode> out() {
        return List.of();
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
//        return visitor.visit(this);
        return null;
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        return;
    }

    @Override
    public String toString() {
        return "(return)";
    }
}
