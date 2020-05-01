package cyr7.cfg.nodes;

import java.util.List;

import cyr7.cfg.dfa.BackwardTransferFunction;
import cyr7.cfg.dfa.ForwardTransferFunction;
import cyr7.cfg.visitor.AbstractCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGIfNode extends CFGNode {

    private final CFGNode trueBranch, falseBranch;

    public CFGIfNode(Location location, CFGNode trueBranch,
            CFGNode falseBranch) {
        super(location);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;

        this.updateIns();
    }

    @Override
    public <T> T accept(AbstractCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<CFGNode> out() {
        return List.of(trueBranch, falseBranch);
    }

    @Override
    public <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T in) {
        return List.of(
            transferFunction.transferTrue(this, in),
            transferFunction.transferFalse(this, in));
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction, T input) {
        return transferFunction.transfer(this, input);
    }

}