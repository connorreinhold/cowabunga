package cyr7.cfg.nodes.asm;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;

import java.util.List;

public class AsmCFGOpNode extends AsmCFGNode {

    public final AsmCFGNode out;

    public AsmCFGOpNode(ASMInstr sourceInstr, AsmCFGNode out) {
        super(sourceInstr);
        this.out = out;
    }

    @Override
    public List<AsmCFGNode> out() {
        return List.of(out);
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        return null;
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {

    }
}
