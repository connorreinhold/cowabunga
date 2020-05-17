package cyr7.cfg.ir.nodes;

import java.util.List;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRExpr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGIfNode extends CFGNode {

    private CFGNode trueBranch;
    private CFGNode falseBranch;
    public IRExpr cond;

    public CFGIfNode(Location location, CFGNode trueBranch,
            CFGNode falseBranch, IRExpr cond) {
        super(location);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
        this.cond = cond;

        this.updateIns();
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public CFGNode falseBranch() {
        return falseBranch;
    }

    public CFGNode trueBranch() {
        return trueBranch;
    }

    @Override
    public List<CFGNode> out() {
        return List.of(trueBranch, falseBranch);
    }

    @Override
    public void replaceOutEdge(CFGNode previous, CFGNode n) {
        if (falseBranch != previous && trueBranch != previous) {
            throw new UnsupportedOperationException(
                    "Cannot replace node arbitrarily.");
        }

        if (trueBranch == previous) {
            this.trueBranch = n;
        }

        if (falseBranch == previous) {
            this.falseBranch = n;
        }

        this.updateIns();
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

    @Override
    public String toString() {
        String condString = cond.toString().replaceAll("\n", "");
        return String.format("if(%s)", condString);
    }

    @Override
    public CFGNode copy(List<CFGNode> out) {
        assert out.size() == 2;
        return new CFGIfNode(this.location(), out.get(0), out.get(1), cond);
    }
}