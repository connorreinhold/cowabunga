package cyr7.cfg.asm.dfa;

import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public enum WorklistAnalysis {
    ;

    public static <L> Map<AsmCFGNode, L> analyze(
        AsmCFGStartNode cfg,
        BackwardDataflowAnalysis<L> analysis) {

        Set<AsmCFGNode> allNodes = getAllNodes(cfg);
        Queue<AsmCFGNode> worklist = new LinkedList<>(allNodes);

        Map<AsmCFGNode, L> in = new HashMap<>();
        Map<AsmCFGNode, L> out = new HashMap<>();
        for (AsmCFGNode node : allNodes) {
            in.put(node, analysis.topValue());
        }

        while (!worklist.isEmpty()) {
            AsmCFGNode node = worklist.remove();
            L outValue = node.outNodes()
                .stream()
                .map(in::get)
                .reduce(analysis::meet)
                .orElse(analysis.topValue());
            out.put(node, outValue);

            L oldInValue = in.get(node);
            L inValue = analysis.transfer(node, outValue);
            if (!oldInValue.equals(inValue)) {
                in.put(node, inValue);
                worklist.addAll(node.inNodes());
            }
        }
        return in;
    }

    static Set<AsmCFGNode> getAllNodes(AsmCFGStartNode cfg) {
        Set<AsmCFGNode> nodes = new HashSet<>();
        Queue<AsmCFGNode> worklist = new LinkedList<>();
        worklist.add(cfg);
        while (!worklist.isEmpty()) {
            AsmCFGNode node = worklist.remove();
            if (!nodes.contains(node)) {
                nodes.add(node);
                worklist.addAll(node.outNodes());
            }
        }
        return nodes;
    }

}
