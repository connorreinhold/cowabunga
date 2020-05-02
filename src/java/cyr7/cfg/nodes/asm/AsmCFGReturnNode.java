package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGReturnNode extends AsmCFGNode {

    public AsmCFGReturnNode(Location location) {
        super(location);
        this.updateIns();
    }

    @Override
    public List<AsmCFGNode> out() {
        return List.of();
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        return;
    }

    @Override
    public String toString() {
        return "(return)";
    }
}
