package cyr7.cfg.ir.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGBlockNode extends CFGNode {

    public CFGNode block;
    private CFGNode outNode;

    private Set<String> useSet;
    private Set<String> defSet;
    private Set<String> killSet;
    private Map<String, String> genSet;

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
        return Collections.unmodifiableSet(this.defSet);
    }

    @Override
    public Set<String> uses() {
        return Collections.unmodifiableSet(this.useSet);
    }

    @Override
    public Map<String, String> gens() {
        return Collections.unmodifiableMap(this.genSet);
    }

    @Override
    public Set<String> kills() {
        return Collections.unmodifiableSet(this.killSet);
    }

    @Override
    public void refreshDfaSets() {
        this.useSet = new HashSet<>();
        this.genSet = new HashMap<>();
        this.defSet = new HashSet<>();
        this.killSet = new HashSet<>();

        var topNode = this.block;
        while (!(topNode instanceof CFGStubNode)) {
            this.useSet.addAll(topNode.uses());
            this.defSet.addAll(topNode.defs());
            this.genSet.putAll(topNode.gens());
            this.killSet.addAll(topNode.kills());
            topNode = topNode.out().get(0);
        }
        return;
    }

}
