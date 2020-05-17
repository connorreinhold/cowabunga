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

    private void blockRepOk() {
        final var defined = new HashSet<String>();
        var topNode = block;
        var previous = block;
        while (true) {
            assert !(topNode instanceof CFGStubNode);
            assert !(topNode instanceof CFGIfNode);
            assert !(topNode instanceof CFGSelfLoopNode);
            assert !(topNode instanceof CFGBlockNode);
            assert !(topNode instanceof CFGStartNode);
            assert !(topNode instanceof CFGReturnNode);
            if (topNode instanceof CFGVarAssignNode) {
                assert !defined.contains(((CFGVarAssignNode)topNode).variable);
                defined.add(((CFGVarAssignNode)topNode).variable);
            } else if (topNode instanceof CFGCallNode) {
                final var checkNode = (CFGCallNode)topNode;
                assert !defined.stream().anyMatch(def ->
                            !def.equals("_") && (checkNode).call.collectors().contains(def));
                defined.addAll(checkNode.call.collectors());
            }

            if (topNode.in().size() != 0) {
                assert topNode.in().size() == 1;
                assert topNode.in().get(0) == previous;
            }
            assert topNode.out().size() == 1;
            previous = topNode;
            topNode = topNode.out().get(0);
            if (topNode instanceof CFGStubNode)
                break;
        }
    }

    /**
     * A block of CFG nodes, where for all nodes in {@code block}, all outgoing
     * and incoming point to nodes within {@code block}. Additionally,
     * {@code block} is a linear CFG tree, i.e. for each node, there is only
     * at most one incoming edge and one outgoing edge.
     */
    public CFGBlockNode(Location location, CFGNode block, CFGNode outNode) {
        super(location);
        this.block = block;
        this.outNode = outNode;
        this.updateIns();

        this.refreshDfaSets();

        repOk();
        blockRepOk();
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
            nodeToTraverse = nodeToTraverse.out().get(0);
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

    @Override
    public CFGNode copy(List<CFGNode> out) {
        throw new UnsupportedOperationException(
                "CFG block nodes cannot be copied");
    }

}
