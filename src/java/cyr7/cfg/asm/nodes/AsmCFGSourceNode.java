package cyr7.cfg.asm.nodes;

import cyr7.x86.asm.ASMInstr;

public abstract class AsmCFGSourceNode extends AsmCFGNode {

    private final int sourceIndex;
    private final ASMInstr sourceInstr;

    public AsmCFGSourceNode(int sourceIndex, ASMInstr sourceInstr) {
        this.sourceIndex = sourceIndex;
        this.sourceInstr = sourceInstr;
    }

    public int sourceIndex() {
        return sourceIndex;
    }

    public ASMInstr sourceInstr() {
        return sourceInstr;
    }

}
