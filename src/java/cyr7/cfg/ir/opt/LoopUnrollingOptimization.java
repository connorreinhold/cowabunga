package cyr7.cfg.ir.opt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cyr7.cfg.ir.CFGUtil;
import cyr7.cfg.ir.constructor.CFGConstructor;
import cyr7.cfg.ir.dfa.DfaResult;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.dfa.loops.BasicInductionVariableUtil;
import cyr7.cfg.ir.dfa.loops.DominatorAnalysis;
import cyr7.cfg.ir.dfa.loops.DominatorUtil;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cli.OptConfig;
import cyr7.cli.Optimization;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRTemp;
import cyr7.typecheck.IxiFileOpener;
import java_cup.runtime.ComplexSymbolFactory;

public class LoopUnrollingOptimization {
    
    // The number of copies made per loop
    final static long LOOP_UNROLL_FACTOR_LOW = 3;
    final static long LOOP_UNROLL_FACTOR_HIGH = 10;
    
    private LoopUnrollingOptimization() {}
    
    public static CFGStartNode optimize(CFGNode start) {
        CFGStartNode startNode = (CFGStartNode)start;
        DfaResult<Set<CFGNode>> result =
                WorklistAnalysis.analyze(startNode, DominatorAnalysis.INSTANCE);

        Map<CFGNode, Set<CFGNode>> cleanedDominators = DominatorUtil.generateMap(result.out());
        runIVAnalysis(cleanedDominators);
        return startNode;
    }
    
    public static void runIVAnalysis(
            Map<CFGNode, Set<CFGNode>> dominators) {
        
        Set<CFGNode> nodesAnalyzed = new HashSet<>();
        Set<CFGNode> headersRemoved = new HashSet<>();
        
        for(Map.Entry<CFGNode, Set<CFGNode>> pair: dominators.entrySet()) {
            CFGNode node = pair.getKey();
            for(CFGNode out: node.out()) {
                // If there is an out edge to a dominator of this node, there's a loop
                if (pair.getValue().contains(out) && !nodesAnalyzed.contains(out)) {
                    Set<CFGNode> tailNodes = new HashSet<>();
                    for (CFGNode tailNode: out.in()) {
                        // Take union of backwards search from all tailnodes from the head
                        if (dominators.containsKey(tailNode) && dominators.get(tailNode).contains(out)) {
                            tailNodes.add(tailNode);
                        }
                    }
                    Set<CFGNode> reachable = backwardsSearch(tailNodes, out);
                    nodesAnalyzed.addAll(reachable);
                    
                    CFGNode newUnrolledHead = LoopUnrollingOptimization.optimizeLoop(out, reachable);
                    for(CFGNode inc: out.in()) {
                        if (!reachable.contains(inc) && !headersRemoved.contains(inc)) {
                            inc.replaceOutEdge(out, newUnrolledHead);
                        }
                    }
                    headersRemoved.add(out);

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
    
    public static CFGNode optimizeLoop(CFGNode head, Set<CFGNode> reachable) {
        Map<String, Long> ivStrideMap = BasicInductionVariableUtil.execute(head, reachable);
        
        long loop_unroll_factor = -1;
        if (reachable.size() < 5) {
            loop_unroll_factor = LOOP_UNROLL_FACTOR_HIGH;
        } else if (reachable.size() < 50) {
            loop_unroll_factor = LOOP_UNROLL_FACTOR_LOW;
        }
        
        if (loop_unroll_factor > 0 && head instanceof CFGIfNode) {
            CFGIfNode ifNode = (CFGIfNode) head;
            if (ifNode.cond instanceof IRBinOp) {
                IRBinOp guard = (IRBinOp) ifNode.cond;
                String potentialIV = null;
                OpType op = null;
                IRExpr rhs = null;
                Long potentialIVInc = null;
                if (guard.left() instanceof IRTemp) {
                    potentialIV = ((IRTemp) guard.left()).name();
                    potentialIVInc = ivStrideMap.get(potentialIV);
                }
                if (potentialIVInc != null && potentialIVInc > 0 && 
                        (guard.opType() == OpType.LT || guard.opType() == OpType.LEQ)) {
                    op = guard.opType();
                }
                
                if (potentialIVInc != null && potentialIVInc < 0 &&
                        (guard.opType() == OpType.GT || guard.opType() == OpType.GEQ)) {
                    op = guard.opType();
                }
                    
                if (guard.right() instanceof IRExpr) {
                    rhs = (IRExpr) guard.right();
                    if (rhs instanceof IRConst && potentialIVInc != null) {
                        loop_unroll_factor = Math.min(loop_unroll_factor,
                                Math.floorDiv(((IRConst) rhs).value(),potentialIVInc));
                    }
                }
                if (potentialIV != null && op != null && rhs != null && ivStrideMap.containsKey(potentialIV)) {
                    CFGStubNode epilogueStub = new CFGStubNode();
                    CFGNode epilogueBody = copyNode(head.out().get(0), head, 
                            epilogueStub, new HashMap<CFGNode, CFGNode>());
                    CFGNode epilogue = head.copy(List.of(epilogueBody, head.out().get(1)));
                    for(CFGNode in: epilogueStub.in()) {
                        in.replaceOutEdge(epilogueStub, epilogue);
                    }
                    
                    CFGNode nextPointer = epilogue;
                    CFGStubNode unrollStub = new CFGStubNode();
                    for(int i = 0; i < loop_unroll_factor; i++) {
                        CFGNode loopCopy;
                        if (i == 0) {
                            loopCopy = copyNode(head.out().get(0), head, 
                                unrollStub, new HashMap <CFGNode, CFGNode>());
                        } else {
                            loopCopy = copyNode(head.out().get(0), head, 
                                    nextPointer, new HashMap <CFGNode, CFGNode>());
                        }
                        nextPointer = loopCopy;
                    }
                    
                    ComplexSymbolFactory.Location l = ifNode.cond.location();
                    
                    // i + c(n-1)
                    IRExpr newLHS = new IRBinOp(l, OpType.ADD, new IRTemp(l, potentialIV), 
                            new IRBinOp(l, OpType.MUL, new IRConst(l, ivStrideMap.get(potentialIV)), 
                                    new IRConst(l, loop_unroll_factor - 1)));
                    // i + c(n-1) < u
                    IRExpr newCond = new IRBinOp(l, op, newLHS, rhs);
                    CFGNode unrolledHeader = new CFGIfNode(head.location(), nextPointer, epilogue, newCond);
                    
                    for(CFGNode in: unrollStub.in()) {
                        in.replaceOutEdge(unrollStub, unrolledHeader);
                    }
                    return unrolledHeader;
                }
            }
        }
        return head;
    }
    
    // HeadRef is used to find the last node in this iteration of the loop, 
    // and have it point to [nextPointer]
    private static CFGNode copyNode(CFGNode toCopy, CFGNode headRef, 
            CFGNode nextPointer, Map<CFGNode, CFGNode> copies) {
        
        if (copies.containsKey(toCopy)) {
            return copies.get(toCopy);
        }
        List<CFGNode> copiedOutNodes = new ArrayList<CFGNode>();
        List<CFGStubNode> stubNodes = new ArrayList<CFGStubNode>();
        for (CFGNode out: toCopy.out()) {
            if (out == headRef) {;
                copiedOutNodes.add(nextPointer);
            } else if (copies.containsKey(out)) {
                copiedOutNodes.add(copies.get(out));
            } else {
                CFGStubNode copyStub = new CFGStubNode();
                copies.put(toCopy, copyStub);
                stubNodes.add(copyStub);
                copiedOutNodes.add(copyNode(out, headRef, nextPointer, copies));
            }
        }
        CFGNode copy = toCopy.copy(copiedOutNodes);
        for(CFGStubNode stub: stubNodes) {
            for(CFGNode in: stub.in()) {
                in.replaceOutEdge(stub, copy);
            }
        }
        copies.put(toCopy, copy);
        return copy;
        
    }
}
