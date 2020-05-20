package cyr7.cfg.asm.dot;

import cyr7.cfg.asm.nodes.AsmCFGIfNode;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGOpNode;
import cyr7.cfg.asm.nodes.AsmCFGReturnNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cfg.asm.visitor.AsmCFGVisitor;
import polyglot.util.Pair;

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


public class AsmCFGDotUtil {
    public static DotData execute(AsmCFGNode node) {
        final Set<AsmCFGNode> visited = new HashSet<>();
        final Queue<AsmCFGNode> worklist = new ArrayDeque<>();
        final var dv = new AsmCFGDotVisitor();
        worklist.add(node);
        while (!worklist.isEmpty()) {
            final var currentNode = worklist.remove();
            currentNode.accept(dv);
            visited.add(currentNode);
            for (AsmCFGNode out: currentNode.outNodes()) {
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
    private static class AsmCFGDotVisitor implements AsmCFGVisitor<Optional<Void>> {

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
            String label = toString(n) + "[id=" + id+"]";
            nodeToLabel.put(n, label);
            nodes.add(n);
            for(AsmCFGNode inc: n.inNodes()) {
                edges.add(new Pair<>(inc, n));
            }
        }
    
        protected String toString(AsmCFGNode n) {
            return n.toString();
        }
    
        @Override
        public Optional<Void> visit(AsmCFGOpNode n) {
            if (nodeToLabel.containsKey(n)) {
                return Optional.empty();
            }
            addDotInfo(n);
            return Optional.empty();
        }
    
        @Override
        public Optional<Void> visit(AsmCFGIfNode n) {
            if (nodeToLabel.containsKey(n)) {
                return Optional.empty();
            }
            addDotInfo(n);
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
            return Optional.empty();
        }
    }
}