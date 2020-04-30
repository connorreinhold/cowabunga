package cyr7.cfg.dfa;

import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGStartNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class IterativeAnalysis {

    public static <L> Map<CFGNode, L> analyze(
        CFGStartNode cfg,
        DataflowAnalysis<L> analysis) {

        Set<CFGNode> allNodes = getAllNodes(cfg);
        Queue<CFGNode> worklist = new LinkedList<>(allNodes);

        Map<CFGNode, L> in = new HashMap<>();
        Map<CFGNode, L> out = new HashMap<>();
        for (CFGNode node : allNodes) {
            out.put(node, analysis.topValue);
        }

        while (!worklist.isEmpty()) {
            CFGNode node = worklist.remove();
            L inValue = node
                .in()
                .stream()
                .map(in::get)
                .reduce(analysis.meet)
                .orElse(analysis.topValue);
            in.put(node, inValue);

            L oldOut = out.get(node);
            L outValue = analysis.transfer.apply(node, inValue);
            if (!oldOut.equals(outValue)) {
                out.put(node, outValue);
                worklist.addAll(node.out());
            }
        }

        return out;
    }

    private static Set<CFGNode> getAllNodes(CFGStartNode cfg) {
        Set<CFGNode> nodes = new HashSet<>();
        Queue<CFGNode> worklist = new LinkedList<>();
        worklist.add(cfg);
        while (!worklist.isEmpty()) {
            CFGNode node = worklist.remove();
            if (!nodes.contains(node)) {
                nodes.addAll(node.out());
                worklist.addAll(node.out());
            }
        }
        return nodes;
    }

    private IterativeAnalysis() { }

}
