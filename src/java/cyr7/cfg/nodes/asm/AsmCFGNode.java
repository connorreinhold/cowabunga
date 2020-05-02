package cyr7.cfg.nodes.asm;

import java.util.ArrayList;
import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class AsmCFGNode {

    protected final List<AsmCFGNode> in;

    private final Location location;

    protected AsmCFGNode(Location location) {
        this.in = new ArrayList<>(1);
        this.location = location;
    }

    public List<AsmCFGNode> in() {
        return in;
    }

    public Location location() {
        return location;
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