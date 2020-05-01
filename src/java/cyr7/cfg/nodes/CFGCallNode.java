package cyr7.cfg.nodes;

import java.util.List;

import cyr7.cfg.dfa.BackwardTransferFunction;
import cyr7.cfg.dfa.ForwardTransferFunction;
import cyr7.cfg.visitor.AbstractCFGVisitor;
import cyr7.ir.nodes.IRCallStmt;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGCallNode extends CFGNode {

    // This includes both procedures and function calls
    public final IRCallStmt call;
    private final CFGNode out;

    public CFGCallNode(Location location, IRCallStmt call, CFGNode out) {
        super(location);
        this.call = call;
        this.out = out;

        this.updateIns();
    }

    @Override
    public List<CFGNode> out() {
        return List.of(out);
    }

    @Override
    public <T> T accept(AbstractCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T in) {
        return List.of(transferFunction.transfer(this, in));
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction, T input) {
        return transferFunction.transfer(this, input);
    }

}
