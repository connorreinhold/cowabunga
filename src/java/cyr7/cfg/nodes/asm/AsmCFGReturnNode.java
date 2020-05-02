package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.cfg.dfa.ForwardTransferFunction;
import cyr7.cfg.visitor.AsmCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AsmCFGReturnNode extends AsmCFGNode {

    public final int numReturnValues;

    public AsmCFGReturnNode(Location location, int numReturnValues) {
        super(location);
        this.numReturnValues = numReturnValues;
        this.updateIns();
    }

    @Override
    public List<AsmCFGNode> out() {
        return List.of();
    }

    @Override
    public <T> T accept(AsmCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void convertFromStub(AsmCFGStubNode stub, AsmCFGNode n) {
        return;
    }

    public <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T in) {
        return List.of();
    }

    @Override
    public String toString() {
        return "(return " + numReturnValues + ")";
    }
}
