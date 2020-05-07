package cyr7.cfg.ir.dfa.loops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cyr7.cfg.ir.constructor.CFGConstructor;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.nodes.IRCompUnit;

public class testStuff {
    public static void main(String[] args) throws Exception {
        File f = new File("tests/resources/testJunk.xi");
        FileReader fr = new FileReader(f);
        BufferedReader br  = new BufferedReader(fr);
        Reader reader = new BufferedReader(br);
        IRCompUnit lowered = IRUtil.generateIR(
            reader,
            "testJunk.xi",
            null,
            new LowerConfiguration(true, true),
            new DefaultIdGenerator());
        Map<String, CFGNode> cfgResult = CFGConstructor.constructCFG(lowered);
        CFGStartNode start = (CFGStartNode) cfgResult.get("_Imain_paai");
        System.out.println(start.out().get(0).out().get(0).in());
        
        //BasicInductionVariableVisitor bv = new BasicInductionVariableVisitor();
        //start.accept(bv);
        
        Map<CFGNode, Map<CFGNode, Set<CFGNode>>> result = 
                WorklistAnalysis.analyze(start, DominatorAnalysis.INSTANCE);

        for(Map.Entry<CFGNode, Map<CFGNode, Set<CFGNode>>> entry: result.entrySet()) {
            //System.out.println(entry.getKey()+": "+entry.getValue());
        }
        //Map<CFGNode, Set<CFGNode>> cleanedDominators = DominatorUtil.generateMap(result);
        //findLoops(cleanedDominators);
    }
    
    public static Map<CFGNode, Set<CFGNode>> findLoops(Map<CFGNode, Set<CFGNode>> dominators) {
        for(Map.Entry<CFGNode, Set<CFGNode>> pair: dominators.entrySet()) {
            System.out.println(pair.getKey()+": "+pair.getValue());
            CFGNode node = pair.getKey();
            for(CFGNode out: node.out()) {
                if (pair.getValue().contains(out)) {
                    Set<CFGNode> reachable = backwardsSearch(node, out);
                    //System.out.println(reachable);
                }
            }
        }
        return dominators;
    }
    
    // Precondition: tail is dominated by head.
    public static Set<CFGNode> backwardsSearch(CFGNode tail, CFGNode head) {
        Set<CFGNode> reachable = new HashSet<CFGNode>();
        Stack<CFGNode> nodes = new Stack<CFGNode>();
        nodes.push(tail);
        while(nodes.size() > 0) {
            CFGNode next = nodes.pop();
            for (CFGNode in: next.in()) {
                if (in != head) {
                    nodes.push(in);
                }
            }
            reachable.add(next);
        }
        return reachable;
    }
    
}
