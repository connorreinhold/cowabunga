package cyr7.cfg.ir.dfa;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;

public final class WorklistAnalysis {

    public static <L> DfaResult<L> analyze(
        CFGStartNode cfg,
        ForwardDataflowAnalysis<L> analysis) {

        Set<CFGNode> allNodes = getAllNodes(cfg);
        Queue<CFGNode> worklist = new ArrayDeque<>(allNodes);

        Map<CFGNode, L> in = new HashMap<>();
        Map<CFGNode, Map<CFGNode, L>> out = new HashMap<>();
        for (CFGNode node : allNodes) {
            HashMap<CFGNode, L> outEdges = new HashMap<>(1, 1);
            for (CFGNode outNode : node.out()) {
                outEdges.put(outNode, analysis.topValue());
            }
            out.put(node, outEdges);
        }

        long work = 0;
        while (!worklist.isEmpty()) {
            work++;
            final long size = worklist.size();
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
            final List<CFGNode> outNodes = node.out();
            final int numOfOutNodes = outNodes.size();
            for (int i = 0; i < numOfOutNodes; i++) {
                CFGNode outEdge = outNodes.get(i);
                L newOutValue = output.get(i);
                L oldOutValue = out.get(node).get(outEdge);
                if (!oldOutValue.equals(newOutValue)) {
                    out.get(node).put(outEdge, newOutValue);
                    worklist.add(outEdge);
                }
            }
        }
        System.out.println("Forward Analysis work: " + work);
        return new DfaResult<>(in, out);
    }

    public static <L> Map<CFGNode, L> analyze(
            CFGStartNode cfg,
            BackwardDataflowAnalysis<L> analysis) {

            Set<CFGNode> allNodes = getAllNodes(cfg);
            Queue<CFGNode> worklist = new ArrayDeque<>(allNodes);

            Map<CFGNode, L> in = new HashMap<>();
            Map<CFGNode, L> out = new HashMap<>();

            for (CFGNode node : allNodes) {
                out.put(node, analysis.topValue());
                in.put(node, analysis.topValue());
            }

            long work = 0;
            while (!worklist.isEmpty()) {
                work++;
                CFGNode node = worklist.remove();

                L outValue = node.out()
                    .stream()
                    .map(in::get)
                    .reduce(analysis::meet)
                    // the set of in-nodes to a node should never be empty
                    // unless it's the start node for a forward analysis or a
                    // return node for a backward analysis
                    .orElse(analysis.topValue());
                out.put(node, outValue);

                L originalInValue = in.get(node);
                L inValue= node.acceptBackward(analysis.transfer(), outValue);
                if (!originalInValue.equals(inValue)) {
                    in.put(node, inValue);
                    worklist.addAll(node.in());
                }
            }
//            System.out.println("Backward Analysis work: " + work);
            return out;
        }


    private static Set<CFGNode> getAllNodes(CFGStartNode cfg) {
        Set<CFGNode> nodes = new HashSet<>();
        Queue<CFGNode> worklist = new ArrayDeque<>();
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
