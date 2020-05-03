package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGReturnNode extends AsmCFGSourceNode {

    public AsmCFGReturnNode(int sourceIndex, ASMInstr sourceInstr) {
        super(sourceIndex, sourceInstr);
    }

    @Override
    public List<AsmCFGNode> outNodes() {
        return List.of();
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        return null;
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
    }

    @Override
    public String toString() {
        return "(return)";
    }
}
