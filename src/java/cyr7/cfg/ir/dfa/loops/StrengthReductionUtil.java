package cyr7.cfg.ir.dfa.loops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cyr7.cfg.ir.CFGUtil;
import cyr7.cfg.ir.constructor.CFGConstructor;
import cyr7.cfg.ir.dfa.DfaResult;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
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
        DefaultIdGenerator idGenerator = new DefaultIdGenerator();
        IRCompUnit lowered = IRUtil.generateIR(
            reader,
            "testJunk.xi",
            null,
            OptConfig.none(),

            new DefaultIdGenerator());
        Map<String, CFGStartNode> cfgResult = CFGConstructor.constructCFG(lowered);
        CFGStartNode start = cfgResult.get("_Imain_paai");

        DfaResult<Set<CFGNode>> result =
                WorklistAnalysis.analyze(start, DominatorAnalysis.INSTANCE);

        Map<CFGNode, Set<CFGNode>> cleanedDominators = DominatorUtil.generateMap(result.out());
        runIVAnalysis(cleanedDominators);
        Writer writer = new PrintWriter(System.out);
        CFGUtil.outputDotForFunctionIR(start, writer);
        
    }
    
    public static void runIVAnalysis(
            Map<CFGNode, Set<CFGNode>> dominators) {
        
        Set<CFGNode> nodesAnalyzed = new HashSet<>();
        for(Map.Entry<CFGNode, Set<CFGNode>> pair: dominators.entrySet()) {
            CFGNode node = pair.getKey();
            for(CFGNode out: node.out()) {
                // If there is an out edge to a dominator of this node, there's a loop
                if (pair.getValue().contains(out) && !nodesAnalyzed.contains(out)) {
                    Set<CFGNode> tailNodes = new HashSet<>();
                    for (CFGNode tailNode: out.in()) {
                        // take union of backwards search from all tailnodes from the head
                        // Need to check if contains because it is possible it was replaced
                        if (dominators.containsKey(tailNode) && dominators.get(tailNode).contains(out)) {
                            tailNodes.add(tailNode);
                        }
                    }
                    Set<CFGNode> reachable = backwardsSearch(tailNodes, out);
                    nodesAnalyzed.addAll(reachable);
                    CFGNode newUnrolledHead = LoopUnrollingOptimization.optimize(out, reachable);
                    for(CFGNode inc: out.in()) {
                        inc.replaceOutEdge(out, newUnrolledHead);
                    }

                }
            }
        }
    }
    
    // Precondition: tail is dominated by head.
    public static Set<CFGNode> backwardsSearch(Set<CFGNode> tailNodes, CFGNode head) {
        Set<CFGNode> reachable = new HashSet<>();
        Stack<CFGNode> nodes = new Stack<>();
        for(CFGNode tail: tailNodes) {
            nodes.push(tail);
        }
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
