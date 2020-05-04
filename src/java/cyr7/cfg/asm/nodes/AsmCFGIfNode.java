package cyr7.cfg.asm.nodes;

import cyr7.cfg.asm.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;

import java.util.List;

public class AsmCFGIfNode extends AsmCFGSourceNode {

    private AsmCFGNode trueBranch;
    private AsmCFGNode falseBranch;

    public AsmCFGIfNode(
        int sourceIndex,
        ASMInstr sourceInstr,
        AsmCFGNode trueBranch,
        AsmCFGNode falseBranch) {

        super(sourceIndex, sourceInstr);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
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
    public List<AsmCFGNode> outNodes() {
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
        return sourceInstr().getIntelAssembly();
    }

}