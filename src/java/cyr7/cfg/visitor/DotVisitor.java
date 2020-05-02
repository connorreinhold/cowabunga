package cyr7.cfg.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;
import polyglot.util.Pair;

public class DotVisitor implements CFGVisitor<Optional<Void>>{

    private final Map<CFGNode, String> nodeToLabel;
    private int id = 0;

    private List<CFGNode> nodes;
    private List<Pair<CFGNode, CFGNode>> edges;

    public DotVisitor() {
        nodeToLabel = new HashMap<CFGNode, String>();
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
                    System.out.println(nodePair.part1());
                    return new Pair<String, String>(
                            nodeToLabel.get(nodePair.part1()),
                            nodeToLabel.get(nodePair.part2()));
                }).collect(Collectors.toList());
    }

    public void addDotInfo(CFGNode n) {
        id++;
        String label = "[id=" + id+"]\\n" + n.CFGLabel();
        nodeToLabel.put(n, label);
        nodes.add(n);
        for(CFGNode inc: n.in()) {
            edges.add(new Pair<CFGNode, CFGNode>(inc, n));
        }
    }

    @Override
    public Optional<Void> visit(CFGCallNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGIfNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        n.out().get(0).accept(this);
        n.out().get(1).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGVarAssignNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGMemAssignNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGReturnNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGStartNode n) {
        if (nodeToLabel.containsKey(n)) {
            return Optional.empty();
        }
        addDotInfo(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }
}