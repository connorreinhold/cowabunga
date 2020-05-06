package cyr7.cfg.ir.nodes;

import java.util.List;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGSelfLoopNode extends CFGNode {

    public CFGSelfLoopNode() {
        super(new Location(-1, -1));
    }

    @Override
    public List<CFGNode> out() {
        return List.of(this);
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
        throw new UnsupportedOperationException(
                "The out nodes of a self loop cannot be replaced");
    }

}
