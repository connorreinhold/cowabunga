package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;

public class AsmCFGStartNode extends AsmCFGNode {

    private AsmCFGNode out;

    public AsmCFGStartNode(AsmCFGNode out) {
        this.out = out;
        this.updateIns();
    }

    public AsmCFGNode out() {
        return out;
    }

    @Override
    public List<AsmCFGNode> outNodes() {
        return List.of(this.out);
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        if (out == stub) {
            this.out = n;
            this.updateIns();
        } else {
            throw new UnsupportedOperationException(
                    "Cannot change out node unless it was originally a stub node.");
        }
    }

    @Override
    public String toString() {
        return "(start)";
    }
}
