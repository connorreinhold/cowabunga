package cyr7.cfg.ir.dfa.loops;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRTemp;
import cyr7.x86.pattern.BiPatternBuilder;


public class BasicInductionVariableUtil {
    
    public static Map<String, Long> execute(CFGNode node, Set<CFGNode> reachable) {
        final Set<CFGNode> visited = new HashSet<>();
        final Queue<CFGNode> worklist = new ArrayDeque<>();
        final var bv = new BasicInductionVariableVisitor(reachable);
        worklist.add(node);
        while (!worklist.isEmpty()) {
            final var currentNode = worklist.remove();
            currentNode.accept(bv);
            visited.add(currentNode);
            for (CFGNode out: currentNode.out()) {
                if (!visited.contains(out) && reachable.contains(out)) {
                    worklist.add(out);
                }
            }
        }
        return bv.ivStrideMap();
    }
    
    private static class BasicInductionVariableVisitor 
        implements IrCFGVisitor<Optional<Void>> {
        
        // Keep track of basic induction variables mapped to stride
        Map<String, Long> ivStrideMap;
        Set<String> invalid;
        
        // Pass in the set of nodes that form the loop.
        public BasicInductionVariableVisitor(Set<CFGNode> reachable) {
            this.invalid = new HashSet<>();
            this.ivStrideMap = new HashMap<>();
        }
        
        public Map<String, Long> ivStrideMap() {
            return ivStrideMap;
        }
        
        @Override
        public Optional<Void> visit(CFGCallNode n) {
            return Optional.empty();
        }
        
        @Override
        public Optional<Void> visit(CFGIfNode n) {
            return Optional.empty();
        }
        
        @Override
        public Optional<Void> visit(CFGVarAssignNode n) {
            if (invalid.contains(n.variable)) {
                return Optional.empty();
            }
            
            if(ivStrideMap.containsKey(n.variable)) {
                ivStrideMap.remove(n.variable);
                invalid.add(n.variable);
                return Optional.empty();
            }
            
            var tempPlusConst = BiPatternBuilder
                    .left()
                    .instOf(IRTemp.class)
                    .and(temp -> temp.name().equals(n.variable))
                    .right()
                    .instOf(IRConst.class)
                    .finish()
                    .enableCommutes();
            
            var tempMinusConst = BiPatternBuilder
                    .left()
                    .instOf(IRTemp.class)
                    .and(temp -> temp.name().equals(n.variable))
                    .right()
                    .instOf(IRConst.class)
                    .finish();
            
            if (n.value instanceof IRBinOp) {
                IRBinOp binOp = (IRBinOp) n.value;
                if (binOp.opType() == OpType.ADD &&
                        tempPlusConst.matches(new Object[]{binOp.left(), binOp.right()})) {
                    ivStrideMap.put(n.variable, tempPlusConst.rightObj().value());
                } else if (binOp.opType() == OpType.SUB &&
                        tempMinusConst.matches(new Object[]{binOp.left(), binOp.right()})) {
                    ivStrideMap.put(n.variable, -tempMinusConst.rightObj().value());
                } else {
                    invalid.add(n.variable);
                }
            } else {
                invalid.add(n.variable);
            }
            
            return Optional.empty();
        }
        
        @Override
        public Optional<Void> visit(CFGMemAssignNode n) {
            return Optional.empty();
        }
        
        @Override
        public Optional<Void> visit(CFGReturnNode n) {
            return Optional.empty();
        }
        
        @Override
        public Optional<Void> visit(CFGStartNode n) {
            return Optional.empty();
        }
        
        @Override
        public Optional<Void> visit(CFGSelfLoopNode n) {
            return Optional.empty();
        }
        
        @Override
        public Optional<Void> visit(CFGBlockNode n) {
            throw new UnsupportedOperationException("Basic Induction Variable"
                    + " Visitor does not have support for basic blocks");
        }
    }
    
}

