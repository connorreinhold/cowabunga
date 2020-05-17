package cyr7.cfg.ir.dfa.loops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRTemp;
import java_cup.runtime.ComplexSymbolFactory;

public class LoopUnrollingOptimization {
    
    // The number of copies made per loop
    final static int LOOP_UNROLL_FACTOR = 3;
    
    private LoopUnrollingOptimization() {}
    
    public static CFGNode optimize(CFGNode head, Set<CFGNode> reachable) {
        BasicInductionVariableVisitor bv = new BasicInductionVariableVisitor(reachable);
        head.accept(bv);
        
        Map<String, Long> ivStrideMap = bv.ivStrideMap;
        
        if (head instanceof CFGIfNode) {
            CFGIfNode ifNode = (CFGIfNode) head;
            if (ifNode.cond instanceof IRBinOp) {
                IRBinOp guard = (IRBinOp) ifNode.cond;
                String potentialIV = null;
                OpType op = null;
                Long constant = null;
                if (guard.left() instanceof IRTemp) {
                    potentialIV = ((IRTemp) guard.left()).name();
                }
                if (guard.opType() == OpType.LT || guard.opType() == OpType.LEQ) {
                    op = guard.opType();
                }
                if (guard.right() instanceof IRConst) {
                    constant = ((IRConst) guard.right()).value();
                }
                if (potentialIV != null && op != null && constant != null && ivStrideMap.containsKey(potentialIV)) {
                    CFGStubNode epilogueStub = new CFGStubNode();
                    CFGNode epilogueBody = copyNode(head.out().get(0), head, 
                            epilogueStub, new HashMap<CFGNode, CFGNode>());
                    CFGNode epilogue = head.copy(List.of(epilogueBody, head.out().get(1)));
                    for(CFGNode in: epilogueStub.in()) {
                        in.replaceOutEdge(epilogueStub, epilogue);
                    }
                    
                    CFGNode nextPointer = epilogue;
                    CFGStubNode unrollStub = new CFGStubNode();
                    for(int i = 0; i < LOOP_UNROLL_FACTOR; i++) {
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
                                    new IRConst(l, LOOP_UNROLL_FACTOR - 1)));
                    // i + c(n-1) < u
                    IRExpr newCond = new IRBinOp(l, op, newLHS, new IRConst(l, constant));
                    CFGNode unrolledHeader = new CFGIfNode(head.location(), epilogue, nextPointer, newCond);
                    
                    for(CFGNode in: unrollStub.in()) {
                        in.replaceOutEdge(unrollStub, unrolledHeader);
                    }

                    return unrolledHeader;
                }
            }
        }
        return head;
    }
    
    private static CFGNode copyNode(CFGNode toCopy, CFGNode oldPointer, 
            CFGNode nextPointer, Map<CFGNode, CFGNode> copies) {
        if (copies.containsKey(toCopy)) {
            return copies.get(toCopy);
        }
        
        if (toCopy.out().contains(oldPointer)) {
            CFGNode lastNode = toCopy.copy(List.of(nextPointer));
            copies.put(toCopy, lastNode);
            return lastNode;
        } else {
            List<CFGNode> copiedOutNodes = new ArrayList<CFGNode>();
            List<CFGStubNode> stubNodes = new ArrayList<CFGStubNode>();
            for (CFGNode out: toCopy.out()) {
                if (copies.containsKey(out)) {
                    copiedOutNodes.add(copies.get(out));
                } else {
                    CFGStubNode copyStub = new CFGStubNode();
                    copies.put(toCopy, copyStub);
                    stubNodes.add(copyStub);
                    copiedOutNodes.add(copyNode(out, oldPointer, nextPointer, copies));
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
}
