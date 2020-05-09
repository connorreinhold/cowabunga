package cyr7.cfg.ir.dfa.loops;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.dfa.loops.DominatorAnalysis.InfiniteSet;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.util.Sets;

public final class DominatorUtil {

    /**
     * Returns a Mapping 1, which is from function name to another Mapping 2.
     * <p>
     * Mapping 2 is from a node {@code n} in the CFG in the function to the set
     * of all nodes that {@code n} dominates.
     * @param cfgs
     * @return
     */
    public static Map<String, Map<CFGNode, Set<CFGNode>>>
            generateDominanceTree(Map<String, CFGStartNode> cfgs) {
        final Map<String, Map<CFGNode, Set<CFGNode>>> domTreeFunction = new HashMap<>();
        cfgs.forEach((functionName, cfg) -> {
            domTreeFunction.put(functionName, new HashMap<>());
            final var domTree = domTreeFunction.get(functionName);
            final var analysisResult =
                    WorklistAnalysis.analyze(cfg, DominatorAnalysis.INSTANCE);
            // In() is the same as out() without the self node.
            final var dominatedRelation = analysisResult.in();
            dominatedRelation.forEach((dominated, dominatorSet) -> {
                final var out = Sets.union(Set.of(dominated), dominatorSet);
                out.forEach(dominator -> {
                    if (domTree.containsKey(dominator)) {
                        domTree.get(dominator).add(dominated);
                    } else {
                        domTree.put(dominator, new HashSet<>());
                        domTree.get(dominator).add(dominated);
                    }
                });
            });
        });
        return domTreeFunction;
    }


    // Generates a map of CFG nodes to all nodes that dominate them
    public static Map<CFGNode, Set<CFGNode>> generateMap(
            Map<CFGNode, Map<CFGNode, Set<CFGNode>>> worklistOutput) {
        Map<CFGNode, Set<CFGNode>> map = new HashMap<>();
        for(Map.Entry<CFGNode, Map<CFGNode, Set<CFGNode>>> entry: worklistOutput.entrySet()) {
            for(Map.Entry<CFGNode, Set<CFGNode>> outEdges: entry.getValue().entrySet()) {
                Set<CFGNode> meet;
                if (map.containsKey(outEdges.getKey())) {
                    // Each time a node is visited, take the meet with previous incoming edges
                    if (outEdges.getValue() == InfiniteSet.INSTANCE) {
                        meet = map.get(outEdges.getKey());
                    } else if (map.get(outEdges.getKey()) == InfiniteSet.INSTANCE) {
                        meet = outEdges.getValue();
                    } else {
                        meet = Sets.intersection(map.get(outEdges.getKey()), outEdges.getValue());
                    }
                } else {
                    meet = outEdges.getValue();
                }
                // A node is dominated by itself
                meet.add(outEdges.getKey());
                map.put(outEdges.getKey(), meet);
            }
        }
        return map;
    }

    private DominatorUtil() { }
}
