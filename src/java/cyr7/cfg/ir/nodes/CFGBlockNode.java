package cyr7.cfg.ir.nodes;

import java.util.List;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGBlockNode extends CFGNode {

    public final CFGNode block;
    public CFGNode outNode;

    public CFGBlockNode(Location location, CFGNode block,
            CFGNode outNode) {
        super(location);
        this.block = block;
        this.outNode = outNode;
        this.updateIns();
        repOk();
    }

    @Override
    public List<CFGNode> out() {
        return List.of(outNode);
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <T> List<T> acceptForward(
            ForwardTransferFunction<T> transferFunction, T input) {
        return List.of(transferFunction.transfer(this, input));
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction,
            T input) {
        return transferFunction.transfer(this, input);
    }

    @Override
    public void replaceOutEdge(CFGNode previous, CFGNode newTarget) {
        if (outNode == previous) {
            this.outNode = newTarget;
            this.updateIns();
        } else {
            throw new UnsupportedOperationException(
                    "Cannot replace node arbitrarily.");
        }
        repOk();
    }

}
