package cyr7.cfg.ir.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGBlockNode extends CFGNode {

    public final CFGNode block;
    private CFGNode outNode;

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

    public CFGNode outNode() {
        return outNode;
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
    public String toString() {
        List<String> descriptions = new ArrayList<>();
        CFGNode nodeToTraverse = this.block;
        while (!(nodeToTraverse instanceof CFGStubNode)) {
            descriptions.add(nodeToTraverse.toString());
        }
        return String.join("\n", descriptions);
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

    @Override
    public Set<String> defs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> uses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> gens() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> kills() {
        // TODO Auto-generated method stub
        return null;
    }

}
