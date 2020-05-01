package cyr7.cfg.dfa;

import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGStartNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class WorklistAnalysis {

    public static <L> Map<CFGNode, Map<CFGNode, L>> analyze(
        CFGStartNode cfg,
        ForwardDataflowAnalysis<L> analysis) {

        Set<CFGNode> allNodes = getAllNodes(cfg);
        Queue<CFGNode> worklist = new LinkedList<>(allNodes);

        Map<CFGNode, L> in = new HashMap<>();
        Map<CFGNode, Map<CFGNode, L>> out = new HashMap<>();
        for (CFGNode node : allNodes) {
            HashMap<CFGNode, L> outEdges = new HashMap<>(1, 1);
            for (CFGNode outNode : node.out()) {
                outEdges.put(outNode, analysis.topValue());
            }
            out.put(node, outEdges);
        }

        while (!worklist.isEmpty()) {
            CFGNode node = worklist.remove();
            L inValue = node.in()
                .stream()
                .map(n -> out.get(n).get(node))
                .reduce(analysis::meet)
                // the set of in-nodes to a node should never be empty
                // unless it's the start node for a forward analysis or a
                // return node for a backward analysis
                .orElse(analysis.topValue());
            in.put(node, inValue);

            List<L> output = node.acceptForward(analysis.transfer(), inValue);
            for (int i = 0; i < node.out().size(); i++) {
                CFGNode outEdge = node.out().get(i);
                L oldOutValue = out.get(node).get(outEdge);
                if (!oldOutValue.equals(output.get(i))) {
                    out.get(node).put(outEdge, output.get(i));
                    worklist.add(outEdge);
                }
            }
        }

        return out;
    }

    public static <L> Map<CFGNode, L> analyze(
        CFGStartNode cfg,
        BackwardDataflowAnalysis<L> analysis) {

        Set<CFGNode> allNodes = getAllNodes(cfg);
        Queue<CFGNode> worklist = new LinkedList<>(allNodes);

        Map<CFGNode, L> in = new HashMap<>();
        Map<CFGNode, L> out = new HashMap<>();
        for (CFGNode node : allNodes) {
            in.put(node, analysis.topValue());
        }

        while (!worklist.isEmpty()) {
            CFGNode node = worklist.remove();
            L outValue = node.out()
                .stream()
                .map(in::get)
                .reduce(analysis::meet)
                .orElse(analysis.topValue());
            out.put(node, outValue);

            L oldInValue = in.get(node);
            L inValue = node.acceptBackward(analysis.transfer(), outValue);
            if (!oldInValue.equals(inValue)) {
                in.put(node, inValue);
            }
        }

        return in;
    }

    static Set<CFGNode> getAllNodes(CFGStartNode cfg) {
        Set<CFGNode> nodes = new HashSet<>();
        Queue<CFGNode> worklist = new LinkedList<>();
        worklist.add(cfg);
        while (!worklist.isEmpty()) {
            CFGNode node = worklist.remove();
            if (!nodes.contains(node)) {
                nodes.add(node);
                worklist.addAll(node.out());
            }
        }
        return nodes;
    }

    private WorklistAnalysis() { }

}
