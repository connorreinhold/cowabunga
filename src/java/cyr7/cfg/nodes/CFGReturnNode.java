package cyr7.cfg.nodes;

import java.util.List;

import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.cfg.StubCFGNode;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGReturnNode extends CFGNode {

    public CFGReturnNode(Location location) {
        super(location);
        this.updateIns();
    }

    @Override
    public List<CFGNode> out() {
        return List.of();
    }

    @Override
    public <T> T accept(AbstractCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(StubCFGNode stub, CFGNode n) {
        return;
    }

}
