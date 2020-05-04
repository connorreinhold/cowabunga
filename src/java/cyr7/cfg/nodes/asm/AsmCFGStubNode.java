package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;

public class AsmCFGStubNode extends AsmCFGSourceNode {

    public AsmCFGStubNode(int sourceIndex, ASMInstr sourceInstr) {
        super(sourceIndex, sourceInstr);
    }

    @Override
    public List<AsmCFGNode> outNodes() {
        return List.of();
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        throw new UnsupportedOperationException("Cannot visit stub node");
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        throw new UnsupportedOperationException("Cannot convert in stub node");
    }

}