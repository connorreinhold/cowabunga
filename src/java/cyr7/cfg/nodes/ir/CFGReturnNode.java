package cyr7.cfg.nodes.ir;

import java.util.List;

import cyr7.cfg.dfa.BackwardTransferFunction;
import cyr7.cfg.dfa.ForwardTransferFunction;
import cyr7.cfg.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGReturnNode extends CFGNode {

    public final int numReturnValues;

    public CFGReturnNode(Location location, int numReturnValues) {
        super(location);
        this.numReturnValues = numReturnValues;
        this.updateIns();
    }

    @Override
    public List<CFGNode> out() {
        return List.of();
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(CFGStubNode stub, CFGNode n) {
        return;
    }

    public <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T in) {
        return List.of();
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction, T input) {
        return transferFunction.transfer(this, input);
    }

    @Override
    public String toString() {
        return "(return " + numReturnValues + ")";
    }
}
