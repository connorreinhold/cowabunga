package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.visitor.AsmCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGStubNode extends AsmCFGNode {

    protected AsmCFGStubNode() {
        super(new Location(-1, -1));
    }

    @Override
    public List<AsmCFGNode> out() {
        return List.of();
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        throw new UnsupportedOperationException("Cannot visit stub node");
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        throw new UnsupportedOperationException("Cannot convert in stub node");
    }

}