package cyr7.cfg.ir.nodes;

import java.util.List;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRExpr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGMemAssignNode extends CFGNode {

    public IRExpr target;
    public IRExpr value;
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

    public CFGNode outNode() {
        return out;
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
    public void replaceOutEdge(CFGNode previous, CFGNode n) {
        if (out == previous) {
            this.out = n;
            this.updateIns();
        } else {
            throw new UnsupportedOperationException(
                    "Cannot replace node arbitrarily.");
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

    @Override
    public CFGNode copy(List<CFGNode> out) {
        assert out.size() == 1;
        return new CFGMemAssignNode(this.location(), target, value, out.get(0));
    }
}