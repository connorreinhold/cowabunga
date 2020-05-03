package cyr7.cfg.nodes.asm;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;

import java.util.List;

public class AsmCFGOpNode extends AsmCFGSourceNode {

    private AsmCFGNode out;

    public AsmCFGOpNode(int sourceIndex, ASMInstr sourceInstr, AsmCFGNode out) {
        super(sourceIndex, sourceInstr);
        this.out = out;
    }

    public AsmCFGNode out() {
        return out;
    }

    @Override
    public List<AsmCFGNode> outNodes() {
        return List.of(out);
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        return null;
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        if (out == stub) {
            out = n;
        }
        updateIns();
    }
}
