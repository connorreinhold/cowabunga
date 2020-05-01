package cyr7.cfg.visitor;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;
import cyr7.ir.nodes.IRStmt;

import polyglot.util.Pair;

public class DotVisitor extends AbstractCFGVisitor<Optional<Void>>{

    private final Map<CFGNode, String> nodeToName;
    private int id = 0;
    
    private List<String> nodes;
    private List<Pair<String, String>> edges;
    
    public DotVisitor(Writer writer) {
        nodeToName = new HashMap<CFGNode, String>();
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }
    
    private void generateNodeName(CFGNode n) {
        if (nodeToName.containsKey(n)) {
            return;
        }
        id++;
        String name = n.toString() + " #" + id;
        nodeToName.put(n, name);
        nodes.add(name);
    }
    
    private void generateIncomingEdges(CFGNode n) {
        for(CFGNode inc: n.in()) {
            edges.add(new Pair<String,String>(nodeToName.get(inc), nodeToName.get(n)));
        }
    }

    @Override
    public Optional<Void> visit(CFGCallNode n) {
        generateNodeName(n);
        generateIncomingEdges(n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGIfNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Void> visit(CFGVarAssignNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Void> visit(CFGMemAssignNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Void> visit(CFGReturnNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Void> visit(CFGStartNode n) {
        // TODO Auto-generated method stub
        return null;
    }
}