package cyr7.cfg.ir.dot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import polyglot.util.Pair;

public class IrCFGDotUtil {
    public static DotData execute(CFGNode node) {
        final Set<CFGNode> visited = new HashSet<>();
        final Queue<CFGNode> worklist = new ArrayDeque<>();
        final var dv = new IrCFGDotVisitor();
        worklist.add(node);
        while (!worklist.isEmpty()) {
            final var currentNode = worklist.remove();
            currentNode.accept(dv);
            visited.add(currentNode);
            for (CFGNode out: currentNode.out()) {
                if (!visited.contains(out)) {
                    worklist.add(out);
                }
            }
        }
        return new DotData(dv.getDotNodes(), dv.getDotEdges());
    }

    public static class DotData {
        private List<String> nodes;
        private List<Pair<String, String>> edges;
        public DotData(List<String> nodes, List<Pair<String, String>> edges) {
            this.nodes = nodes;
            this.edges = edges;
        }
        public List<String> nodes() {
            return nodes;
        }
        
        public List<Pair<String, String>> edges() {
            return edges;
        }
        
    }
    
    private static class IrCFGDotVisitor implements IrCFGVisitor<Optional<Void>> {

        private final Map<CFGNode, String> nodeToLabel;
        private int id = 0;
    
        private List<CFGNode> nodes;
        private List<Pair<CFGNode, CFGNode>> edges;
    
        public IrCFGDotVisitor() {
            nodeToLabel = new HashMap<>();
            nodes = new ArrayList<>();
            edges = new ArrayList<>();
        }
    
        public List<String> getDotNodes() {
            return this.nodes.stream().map(node -> nodeToLabel.get(node)).collect(Collectors.toList());
        }
    
        public List<Pair<String, String>> getDotEdges() {
            return this.edges.stream().map(nodePair -> {
                return new Pair<>(nodeToLabel.get(nodePair.part1()), nodeToLabel.get(nodePair.part2()));
            }).collect(Collectors.toList());
        }
    
        public void addDotInfo(CFGNode n) {
            id++;
            String label = n.toString() + "[id=" + id + "]";
            nodeToLabel.put(n, label);
            nodes.add(n);
            for (CFGNode inc : n.in()) {
                edges.add(new Pair<>(inc, n));
            }
        }
    
        @Override
        public Optional<Void> visit(CFGCallNode n) {
            if (nodeToLabel.containsKey(n)) {
                return Optional.empty();
            }
            addDotInfo(n);
            return Optional.empty();
        }
    
        @Override
        public Optional<Void> visit(CFGIfNode n) {
            if (nodeToLabel.containsKey(n)) {
                return Optional.empty();
            }
            addDotInfo(n);
            return Optional.empty();
        }
    
        @Override
        public Optional<Void> visit(CFGVarAssignNode n) {
            if (nodeToLabel.containsKey(n)) {
                return Optional.empty();
            }
            addDotInfo(n);
            return Optional.empty();
        }
    
        @Override
        public Optional<Void> visit(CFGMemAssignNode n) {
            if (nodeToLabel.containsKey(n)) {
                return Optional.empty();
            }
            addDotInfo(n);
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
            return Optional.empty();
        }
    
        @Override
        public Optional<Void> visit(CFGSelfLoopNode n) {
            if (nodeToLabel.containsKey(n)) {
                return Optional.empty();
            }
            addDotInfo(n);
            return Optional.empty();
        }
    
        @Override
        public Optional<Void> visit(CFGBlockNode n) {
            if (nodeToLabel.containsKey(n)) {
                return Optional.empty();
            }
            addDotInfo(n);
            return Optional.empty();
        }
    }
}