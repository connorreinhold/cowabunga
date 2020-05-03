package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGStartNode extends AsmCFGNode {
    private AsmCFGNode out;

    public AsmCFGStartNode(Location location, AsmCFGNode out) {
        super(location);
        this.out = out;
        this.updateIns();
    }

    @Override
    public List<AsmCFGNode> out() {
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
