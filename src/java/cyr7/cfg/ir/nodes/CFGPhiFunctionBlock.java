package cyr7.cfg.ir.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGPhiFunctionBlock extends CFGNode {

    public Map<String, List<String>> mappings;
    private CFGNode out;

    public CFGPhiFunctionBlock(Location location, CFGNode out,
            int numOfIncoming, Set<String> variables) {
        super(location);
        this.out = out;

        this.mappings = new HashMap<>();
        for (String v: variables) {
            final List<String> arguments = new ArrayList<>();
            for (int i = 0; i < numOfIncoming; i++) {
                arguments.add(v);
            }
            this.mappings.put(v, arguments);
        }
    }

    @Override
    public List<CFGNode> out() {
        return List.of(out);
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        throw new AssertionError("Do not use a visitor on phi-function block.");
    }

    @Override
    public <T> List<T> acceptForward(
            ForwardTransferFunction<T> transferFunction, T input) {
        throw new AssertionError("Do not use a transfer function on phi-function block.");
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction,
            T input) {
        throw new AssertionError("Do not use a transfer function on phi-function block.");
    }

    @Override
    public void replaceOutEdge(CFGNode previous, CFGNode newTarget) {
        if (this.out == previous) {
            this.out = newTarget;
            this.updateIns();
        } else {
            throw new AssertionError("No node can be replaced");
        }
    }

    @Override
    public Set<String> defs() {
        throw new UnsupportedOperationException("Do not use defs set of phi-function");
    }

    @Override
    public Set<String> uses() {
        throw new UnsupportedOperationException("Do not use uses set of phi-function");
    }

    @Override
    public Map<String, String> gens() {
        throw new UnsupportedOperationException("Do not use gen set of phi-function");
    }

    @Override
    public Set<String> kills() {
        throw new UnsupportedOperationException("Do not use kill set of phi-function");
    }

    @Override
    public void refreshDfaSets() {
        throw new UnsupportedOperationException("No data to refresh");
    }

    @Override
    public CFGNode copy(List<CFGNode> out) {
        throw new UnsupportedOperationException("Do not copy phi function");
    }

}
