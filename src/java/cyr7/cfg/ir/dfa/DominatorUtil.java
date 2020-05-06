package cyr7.cfg.ir.dfa;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.util.Sets;

public final class DominatorUtil {
    
    public static Map<CFGNode, Set<CFGNode>> generateMap(
            Map<CFGNode, Map<CFGNode, Set<CFGNode>>> worklistOutput) {
        Map<CFGNode, Set<CFGNode>> map = new HashMap<>();
        for(Map.Entry<CFGNode, Map<CFGNode, Set<CFGNode>>> entry: worklistOutput.entrySet()) {
            for(Map.Entry<CFGNode, Set<CFGNode>> outEdges: entry.getValue().entrySet()) {
                Set<CFGNode> meet;
                if (map.containsKey(outEdges.getKey())) {
                    // Each time a node is visited, take the meet with previous incoming edges
                    meet = Sets.intersection(map.get(outEdges.getKey()), outEdges.getValue());
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
