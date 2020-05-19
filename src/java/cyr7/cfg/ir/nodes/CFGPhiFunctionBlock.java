package cyr7.cfg.ir.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGPhiFunctionBlock extends CFGNode {

    public String variable;
    public List<String> arguments;
    private CFGNode out;

    public CFGPhiFunctionBlock(Location location, int numOfIncoming,
            String variable, CFGNode out) {
        super(location);
        this.variable = variable;
        this.out = out;

        this.arguments = new ArrayList<>();
        for (int i = 0; i < numOfIncoming; i++) {
            this.arguments.add(variable);
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
        return Collections.singleton(this.variable);
    }

    @Override
    public Set<String> uses() {
        return new HashSet<>(this.arguments);
    }

    @Override
    public Map<String, String> gens() {
        return Collections.emptyMap();
    }

    @Override
    public Set<String> kills() {
        return Collections.singleton(this.variable);
    }

    @Override
    public void refreshDfaSets() {
        return;
    }

    @Override
    public CFGNode copy(List<CFGNode> out) {
        throw new UnsupportedOperationException("Do not copy phi functtion");
    }

}
