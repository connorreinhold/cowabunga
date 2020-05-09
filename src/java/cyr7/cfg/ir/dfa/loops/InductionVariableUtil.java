package cyr7.cfg.ir.dfa.loops;

import java.util.HashMap;
import java.util.Map;

import cyr7.cfg.ir.dfa.loops.inductionvars.InductionVariable;
import cyr7.cfg.ir.nodes.CFGNode;

public class InductionVariableUtil {
    
    // Generates a map of CFG nodes to all nodes that dominate them
    public static Map<CFGNode, Map<String, InductionVariable>> generateMap(
            Map<CFGNode, Map<CFGNode, Map<String, InductionVariable>>> worklistOutput) {
        Map<CFGNode, Map<String, InductionVariable>> map = new HashMap<>();
        for(Map.Entry<CFGNode, Map<CFGNode, Map<String, InductionVariable>>> entry: worklistOutput.entrySet()) {
            CFGNode arbitraryOut = entry.getKey().out().get(0);
            map.put(entry.getKey(), entry.getValue().get(arbitraryOut));
        }
        return map;
    }
}
