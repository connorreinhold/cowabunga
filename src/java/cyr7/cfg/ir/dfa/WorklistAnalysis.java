package cyr7.cfg.ir.dfa;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import polyglot.util.Pair;

public final class WorklistAnalysis {

    public static <L> DfaResult<L> analyzeSubsection(
            CFGStartNode start,
            Set<CFGNode> reachable,
            ForwardDataflowAnalysis<L> analysis) {
        return runAnalysis(start, reachable, analysis);
    }

    public static <L> DfaResult<L> analyze(
            CFGStartNode cfg,
            ForwardDataflowAnalysis<L> analysis) {

        Set<CFGNode> inQueue = getAllNodes(cfg);

        final var worklistPrecomputation = getSccForwardWorklist(cfg);
        final var worklist = worklistPrecomputation.part1();
        final var nodeToScc = worklistPrecomputation.part2();

        Map<CFGNode, L> in = new HashMap<>();
        Map<CFGNode, Map<CFGNode, L>> out = new HashMap<>();
        for (CFGNode node : inQueue) {
            HashMap<CFGNode, L> outEdges = new HashMap<>(1, 1);
            for (CFGNode outNode : node.out()) {
                outEdges.put(outNode, analysis.topValue());
            }
            out.put(node, outEdges);
        }
        in.put(cfg, analysis.topValue());

        while (!worklist.isEmpty()) {
            final var scc = worklist.remove();
            final var subWorklist = new ArrayDeque<>(scc.nodes);
            while (!subWorklist.isEmpty()) {
                final var node = subWorklist.remove();
                inQueue.remove(node);
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
                        if (scc.nodes.contains(outEdge)) {
                            subWorklist.add(outEdge);
                            inQueue.add(outEdge);
                        } else if (!inQueue.contains(outEdge)) {
                            final var nextScc = nodeToScc.get(outEdge);
                            worklist.add(nextScc);
                            nextScc.nodes.forEach(inQueue::add);
                        }
                    }
                }
            }
        }
        return new DfaResult<>(in, out);
    }

    private static <L> DfaResult<L> runAnalysis(
            CFGStartNode start,
            Set<CFGNode> allNodes,
            ForwardDataflowAnalysis<L> analysis) {

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

        while (!worklist.isEmpty()) {
            CFGNode node = worklist.remove();
            if (!allNodes.contains(node)) {
                continue;
            }
            L inValue;
            if (node == start) {
                inValue = analysis.topValue();
            } else {
                inValue = node.in()
                    .stream()
                    .map(n -> out.get(n).get(node))
                    .reduce(analysis::meet)
                    // the set of in-nodes to a node should never be empty
                    // unless it's the start node for a forward analysis or a
                    // return node for a backward analysis
                    .orElse(analysis.topValue());
            }
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
        return new DfaResult<>(in, out);
    }


    private static Pair<Queue<StronglyConnectedComponent>,
                    Map<CFGNode, StronglyConnectedComponent>>
                            getSccForwardWorklist(CFGStartNode start) {
        Set<CFGNode> visited = new HashSet<>();
        Queue<CFGNode> postOrderTraversal = new ArrayDeque<>();
        Deque<CFGNode> nextNodeToVisit = new ArrayDeque<>();
        nextNodeToVisit.add(start);

        while (!nextNodeToVisit.isEmpty()) {
            final var nextNode = nextNodeToVisit.pop();
            postOrderTraversal.add(nextNode);
            visited.add(nextNode);
            for (CFGNode out: nextNode.out()) {
                if (!visited.contains(out)) {
                    nextNodeToVisit.push(out);
                }
            }
        }

        Map<CFGNode, StronglyConnectedComponent> nodeToScc = new HashMap<>();
        Queue<StronglyConnectedComponent> worklist = new ArrayDeque<>();
        List<CFGNode> component = new ArrayList<>();
        while (!postOrderTraversal.isEmpty()) {
            final var nextNode = postOrderTraversal.remove();
            component.add(nextNode);
            if (postOrderTraversal.isEmpty()
                    || !postOrderTraversal.peek().out().contains(nextNode)) {
                final var scc = new StronglyConnectedComponent(component);
                worklist.add(scc);
                component.forEach(node -> nodeToScc.put(node, scc));
                component = new ArrayList<>();
            }
        }
        return new Pair<>(worklist, nodeToScc);
    }

    private static class StronglyConnectedComponent {
        public final List<CFGNode> nodes;
        public StronglyConnectedComponent(List<CFGNode> nodes) {
            this.nodes = Collections.unmodifiableList(nodes);
        }
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
