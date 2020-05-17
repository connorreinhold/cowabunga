package cyr7.cfg.ir.nodes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGStartNode extends CFGNode {

    private CFGNode out;

    public CFGStartNode(Location location, CFGNode out) {
        super(location);
        this.out = out;
        this.updateIns();
        repOk();
    }

    @Override
    public List<CFGNode> out() {
        return List.of(this.out);
    }

    public CFGNode outNode() {
        return out;
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceOutEdge(CFGNode previous, CFGNode n) {
        if (out == previous) {
            this.out = n;
            this.updateIns();
        } else {
            throw new UnsupportedOperationException(
                    "Cannot replace node arbitrarily.");
        }
        repOk();
    }

    @Override
    public <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T in) {
        return List.of(transferFunction.transfer(this, in));
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction, T input) {
        return input;
    }

    @Override
    public String toString() {
        return "start";
    }
    
    @Override
    public CFGNode copy(List<CFGNode> out) {
        assert out.size() == 1;
        return new CFGStartNode(this.location(), out.get(0));
    }

    @Override
    public Set<String> defs() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> uses() {
        return Collections.emptySet();
    }

    @Override
    public Map<String, String> gens() {
        return Collections.emptyMap();
    }

    /**
     * Although the returned set is empty, this {@link CFGStartNode node}
     * actually kills everything.
     */
    @Override
    public Set<String> kills() {
        return Collections.emptySet();
    }

    @Override
    public void refreshDfaSets() {
        return;
    }
}
