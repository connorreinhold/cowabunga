package cyr7.cfg.nodes.ir;

import java.util.ArrayList;
import java.util.List;

import cyr7.cfg.dfa.BackwardTransferFunction;
import cyr7.cfg.dfa.ForwardTransferFunction;
import cyr7.cfg.visitor.IrCFGVisitor;
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

    public abstract <T> T accept(IrCFGVisitor<T> visitor);

    /**
     * Invariant: the number of elements in the output of this list must
     * correspond one-to-one with the elements of out().
     */
    public abstract <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T input);

    public abstract <T> T acceptBackward(BackwardTransferFunction<T> transferFunction, T input);

    protected final void updateIns() {
        for (CFGNode node : out()) {
            node.in().add(this);
        }
    }

    public abstract void convertFromStub(CFGStubNode stub, CFGNode n);

}