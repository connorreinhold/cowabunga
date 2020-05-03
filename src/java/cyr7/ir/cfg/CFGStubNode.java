package cyr7.ir.cfg;

import java.util.List;

import cyr7.cfg.dfa.BackwardTransferFunction;
import cyr7.cfg.dfa.ForwardTransferFunction;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.visitor.CFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGStubNode extends CFGNode {

    protected CFGStubNode() {
        super(new Location(-1, -1));
    }

    @Override
    public List<CFGNode> out() {
        return List.of();
    }

    @Override
    public <T> T accept(CFGVisitor<T> visitor) {
        throw new UnsupportedOperationException("Cannot visit stub node");
    }

    @Override
    public void convertFromStub(CFGStubNode stub, CFGNode n) {
        throw new UnsupportedOperationException("Cannot convert in stub node");
    }

    @Override
    public <T> List<T> acceptForward(
            ForwardTransferFunction<T> transferFunction, T input) {
        throw new UnsupportedOperationException("Cannot accept forward in stub node");
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction,
            T input) {
        throw new UnsupportedOperationException("Cannot accept backward in stub node");
    }

    @Override
    public String toString() {
        return "Stub";
    }

}