package cyr7.cfg.ir.nodes;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGStubNode extends CFGNode {

    public CFGStubNode() {
        super(new Location(-1, -1));
    }

    @Override
    public List<CFGNode> out() {
        return List.of();
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        throw new UnsupportedOperationException("Cannot visit stub node");
    }

    @Override
    public void replaceOutEdge(CFGNode stub, CFGNode n) {
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

    @Override
    public CFGNode copy(List<CFGNode> out) {
        throw new UnsupportedOperationException(
                "A self loop cannot be copied");
    }
    public Set<String> defs() {
        throw new AssertionError("Stub node has no definition set");
    }

    @Override
    public Set<String> uses() {
        throw new AssertionError("Stub node has no use set");
    }

    @Override
    public Map<String, String> gens() {
        throw new AssertionError("Stub node has no gen mapping");
    }

    @Override
    public Set<String> kills() {
        throw new AssertionError("Stub node has no kill set");
    }

    @Override
    public void refreshDfaSets() {
        throw new AssertionError("Stub node has no dfa sets");
    }
}