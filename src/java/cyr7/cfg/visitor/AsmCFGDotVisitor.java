package cyr7.cfg.visitor;

import cyr7.cfg.nodes.asm.AsmCFGIfNode;
import cyr7.cfg.nodes.asm.AsmCFGNode;
import cyr7.cfg.nodes.asm.AsmCFGOpNode;
import cyr7.cfg.nodes.asm.AsmCFGReturnNode;
import cyr7.cfg.nodes.asm.AsmCFGStartNode;
import polyglot.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AsmCFGDotVisitor implements AsmCFGVisitor<Optional<Void>>{

    private final Map<AsmCFGNode, String> nodeToLabel;
    private int id = 0;

    private List<AsmCFGNode> nodes;
    private List<Pair<AsmCFGNode, AsmCFGNode>> edges;

    public AsmCFGDotVisitor() {
        nodeToLabel = new HashMap<>();
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public List<String> getDotNodes() {
        return this.nodes.stream().map(
                node -> nodeToLabel.get(node)).collect(Collectors.toList());
    }

    public List<Pair<String,String>> getDotEdges() {
        return this.edges.stream().map(
                nodePair -> {
                    return new Pair<>(
                        nodeToLabel.get(nodePair.part1()),
                        nodeToLabel.get(nodePair.part2()));
                }).collect(Collectors.toList());
    }

    public void addDotInfo(AsmCFGNode n) {
        id++;
        String label = n.toString() + "[id=" + id+"]";
        nodeToLabel.put(n, label);
        nodes.add(n);
        for(AsmCFGNode inc: n.inNodes()) {
            edges.add(new Pair<>(inc, n));
        }
    }

    @Override
    public Optional<Void> visit(AsmCFGOpNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        n.outNodes().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(AsmCFGIfNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        n.outNodes().get(0).accept(this);
        n.outNodes().get(1).accept(this);
        return Optional.empty();
    }
    
    @Override
    public Optional<Void> visit(AsmCFGReturnNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(AsmCFGStartNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        n.outNodes().get(0).accept(this);
        return Optional.empty();
    }
}