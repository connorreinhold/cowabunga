package cyr7.cfg.nodes.ir;

import cyr7.cfg.dfa.BackwardTransferFunction;
import cyr7.cfg.dfa.ForwardTransferFunction;
import cyr7.cfg.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRExpr;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.List;

public class CFGMemAssignNode extends CFGNode {

    public final IRExpr target;
    public final IRExpr value;
    private CFGNode out;

    public CFGMemAssignNode(
        Location location,
        IRExpr target,
        IRExpr value,
        CFGNode out) {

        super(location);
        this.target = target;
        this.value = value;
        this.out = out;
        this.updateIns();
    }

    @Override
    public List<CFGNode> out() {
        return List.of(this.out);
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(CFGStubNode stub, CFGNode n) {
        if (out == stub) {
            this.out = n;
            this.updateIns();
        } else {
            throw new UnsupportedOperationException(
                    "Cannot change out node unless it was originally a stub node.");
        }
    }

    @Override
    public <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T in) {
        return List.of(transferFunction.transfer(this, in));
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction, T input) {
        return transferFunction.transfer(this, input);
    }

    @Override
    public String toString() {
        String targetString = target.toString().replaceAll("\n", "");
        String valueString = target.toString().replaceAll("\n", "");
        return targetString + "=" + valueString;
    }
}