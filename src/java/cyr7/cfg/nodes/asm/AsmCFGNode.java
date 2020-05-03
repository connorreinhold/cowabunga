package cyr7.cfg.nodes.asm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class AsmCFGNode {

    private List<AsmCFGNode> in;

    protected AsmCFGNode() {
        this.in = new ArrayList<>(1);
    }

    /**
     * Call this whenever the out nodes are changed
     */
    protected final void updateIns() {
        for (AsmCFGNode node : outNodes()) {
            if (!node.inNodes().contains(this)) {
                node.inNodes().add(this);
            }
        }
    }

    public List<AsmCFGNode> inNodes() {
        return in;
    }

    public abstract List<AsmCFGNode> outNodes();

    public abstract <T> T accept(AsmCFGVisitor<T> visitor);

    public abstract void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n);

}