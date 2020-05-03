package cyr7.cfg.nodes.asm;

import java.util.ArrayList;
import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class AsmCFGNode {

    protected final List<AsmCFGNode> in;

    public final ASMInstr sourceInstr;

    protected AsmCFGNode(ASMInstr sourceInstr) {
        this.in = new ArrayList<>(1);
        this.sourceInstr = sourceInstr;
    }

    public List<AsmCFGNode> in() {
        return in;
    }

    public abstract List<AsmCFGNode> out();

    public abstract <T> T accept(AsmCFGVisitor<T> visitor);

    protected final void updateIns() {
        for (AsmCFGNode node : out()) {
            node.in().add(this);
        }
    }

    public abstract void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n);

}