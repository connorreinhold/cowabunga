package cyr7.cfg.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;

import polyglot.util.Pair;

public class DotVisitor extends AbstractCFGVisitor<Optional<Void>>{

    private final Map<CFGNode, String> nodeToLabel;
    private int id = 0;
    
    private List<String> nodes;
    private List<Pair<String, String>> edges;
    
    public DotVisitor() {
        nodeToLabel = new HashMap<CFGNode, String>();
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }
    
    public List<String> getNodes() {
        return this.nodes;
    }
    
    public List<Pair<String,String>> getEdges() {
        return edges;
    }

    public void addDotInfo(CFGNode n) {
        if (nodeToLabel.containsKey(n)) {
            return;
        }
        id++;
        String label = n.CFGLabel() + "[id=" + id+"]";
        nodeToLabel.put(n, label);
        nodes.add(label);
        for(CFGNode inc: n.in()) {
            edges.add(new Pair<String,String>(nodeToLabel.get(inc), nodeToLabel.get(n)));
        }
    }
    
    @Override
    public Optional<Void> visit(CFGCallNode n) {
        addDotInfo(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGIfNode n) {
        addDotInfo(n);
        n.out().get(0).accept(this);
        n.out().get(1).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGVarAssignNode n) {
        addDotInfo(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGMemAssignNode n) {
        addDotInfo(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGReturnNode n) {
        addDotInfo(n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGStartNode n) {
        addDotInfo(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }
}