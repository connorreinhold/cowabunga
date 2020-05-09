package cyr7.cfg.ir.dfa.loops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cyr7.cfg.ir.constructor.CFGConstructor;
import cyr7.cfg.ir.dfa.DfaResult;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.dfa.loops.inductionvars.InductionVariable;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cli.OptConfig;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.nodes.IRCompUnit;

public class StrengthReductionUtil {
    
    public static void main(String[] args) throws Exception {
        File f = new File("tests/resources/testJunk.xi");
        FileReader fr = new FileReader(f);
        BufferedReader br  = new BufferedReader(fr);
        Reader reader = new BufferedReader(br);
        IRCompUnit lowered = IRUtil.generateIR(
            reader,
            "testJunk.xi",
            null,
            OptConfig.none(),
            new DefaultIdGenerator());
        Map<String, CFGNode> cfgResult = CFGConstructor.constructCFG(lowered);
        CFGStartNode start = (CFGStartNode) cfgResult.get("_Imain_paai");
        
        DfaResult<Set<CFGNode>> result = 
                WorklistAnalysis.analyze(start, DominatorAnalysis.INSTANCE);

        Map<CFGNode, Set<CFGNode>> cleanedDominators = DominatorUtil.generateMap(result.out());
        runIVAnalysis(cleanedDominators);
    }
    
    // Returns head of loop mapped to IV analysis for each node in the loop
    public static Map<CFGNode, Map<CFGNode, Map<String, InductionVariable>>> runIVAnalysis(
            Map<CFGNode, Set<CFGNode>> dominators) {
        
        Map<CFGNode, Map<CFGNode, Map<String, InductionVariable>>> loopIVAnalysis = new HashMap<>();
        for(Map.Entry<CFGNode, Set<CFGNode>> pair: dominators.entrySet()) {
            CFGNode node = pair.getKey();
            for(CFGNode out: node.out()) {
                // If there is an out edge to a dominator of this node
                if (pair.getValue().contains(out)) {
                    // Found a loop
                    Set<CFGNode> reachable = backwardsSearch(node, out);
                    //System.out.println(reachable);
                    BasicInductionVariableVisitor bv = new BasicInductionVariableVisitor(reachable);
                    out.accept(bv);
                    DerivedInductionVariableAnalysis inductionAnalysis = 
                            new DerivedInductionVariableAnalysis(bv.inductionVars, out);
                    var result = WorklistAnalysis.analyzeSubsection(out, reachable, inductionAnalysis).out();
                    //System.out.println(result);
                    loopIVAnalysis.put(out, InductionVariableUtil.generateMap(result));
                }
            }
        }
        return loopIVAnalysis;
    }
    
    // Precondition: tail is dominated by head.
    public static Set<CFGNode> backwardsSearch(CFGNode tail, CFGNode head) {
        Set<CFGNode> reachable = new HashSet<CFGNode>();
        Stack<CFGNode> nodes = new Stack<CFGNode>();
        nodes.push(tail);
        while(nodes.size() > 0) {
            CFGNode next = nodes.pop();
            for (CFGNode in: next.in()) {
                if (in != head && !reachable.contains(in)) {
                    nodes.push(in);
                }
            }
            reachable.add(next);
        }
        reachable.add(head);
        return reachable;
    }
    
}