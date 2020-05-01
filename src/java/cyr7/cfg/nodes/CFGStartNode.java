package cyr7.cfg.nodes;

import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.cfg.StubCFGNode;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGStartNode extends CFGNode {
    private CFGNode out;

    public CFGStartNode(Location location, CFGNode out) {
        super(location);
        this.out = out;
        this.updateIns();
    }

    @Override
    public List<CFGNode> out() {
        return List.of(this.out);
    }

    @Override
    public <T> T accept(AbstractCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(StubCFGNode stub, CFGNode n) {
        if (out == stub) {
            this.out = n;
            this.updateIns();
        } else {
            throw new UnsupportedOperationException(
                    "Cannot change out node unless it was originally a stub node.");
        }
    }

}
