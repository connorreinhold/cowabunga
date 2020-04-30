package cyr7.cfg.nodes;

import java.util.ArrayList;
import java.util.List;
import cyr7.cfg.visitor.AbstractCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class CFGNode {

    protected final List<CFGNode> in;

    private final Location location;

    protected CFGNode(Location location) {
        this.in = new ArrayList<>(1);
        this.location = location;
    }

    public List<CFGNode> in() {
        return in;
    }
    
    public Location location() {
        return location;
    }

    public abstract List<CFGNode> out();

    public abstract <T> T accept(AbstractCFGVisitor<T> visitor);

    protected final void updateIns() {
        for (CFGNode node : out()) {
            node.in().add(this);
        }
    }

}