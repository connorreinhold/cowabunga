package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;

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
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        // do nothing since this node has no out nodes
    }

    @Override
    public String toString() {
        return "(return)";
    }
}
