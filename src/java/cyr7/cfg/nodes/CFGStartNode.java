package cyr7.cfg.nodes;

import java.util.List;
import cyr7.cfg.visitor.AbstractCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGStartNode extends CFGNode {
    private final CFGNode out;

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
    public <T> void accept(AbstractCFGVisitor<T> visitor) {
        visitor.visit(this);
    }

}
