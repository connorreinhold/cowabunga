package cyr7.cfg.ir.dfa.loops;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRTemp;

public class BasicInductionVariableVisitor implements IrCFGVisitor<Optional<Void>> {

    // Keep track of basic induction variables along with what they are incremented by each loop
    Map<String, Long> mapVarToInc;
    Set<CFGNode> visited;
    
    // If Temp a = ____ of wrong format, a cannot be a basic IV
    Set<String> invalidVars;
    
    // TODO: pass in the set of nodes that form the loop.
    public BasicInductionVariableVisitor() {
        visited = new HashSet<CFGNode>();
        mapVarToInc = new HashMap<String, Long>();
        invalidVars = new HashSet<String>();
    }
    
    @Override
    public Optional<Void> visit(CFGCallNode n) {
        if (visited.contains(n)) {
            return Optional.empty();
        }
        n.out().get(0).accept(this);
        visited.add(n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGIfNode n) {
        if (visited.contains(n)) {
            return Optional.empty();
        }
        n.out().get(0).accept(this);
        n.out().get(1).accept(this);
        visited.add(n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGVarAssignNode n) {
        if (visited.contains(n) || invalidVars.contains(n.variable)) {
            return Optional.empty();
        }
        boolean validInductionVariable = false;
        Long loopInc = 0l;
        if (n.value instanceof IRBinOp) {
            IRBinOp binOp = (IRBinOp) n.value;
            if (binOp.opType() == OpType.ADD) {
                if (isCorrectTemp(binOp.left(), n.variable) 
                        && isConst(binOp.right())) {
                    // If a = a + CONST
                    loopInc = ((IRConst) binOp.right()).value();
                    if (mapVarToInc.containsKey(n.variable)) {
                        loopInc += mapVarToInc.get(n.variable);
                    }
                    validInductionVariable = true;
                } else if (isCorrectTemp(binOp.right(), n.variable)
                        && isConst(binOp.left())) {
                    // If a = CONST + a
                    loopInc = ((IRConst) binOp.left()).value();
                    if (mapVarToInc.containsKey(n.variable)) {
                        loopInc += mapVarToInc.get(n.variable);
                    }
                    validInductionVariable = true;
                }
            }
        }
        if (validInductionVariable) {
            mapVarToInc.put(n.variable, loopInc);
        } else {
            invalidVars.add(n.variable);
        }
        n.out().get(0).accept(this);
        visited.add(n);
        return Optional.empty();
    }
    
    private static boolean isCorrectTemp(IRExpr node, String target) {
        if (node instanceof IRTemp && ((IRTemp)node).name().equals(target)) {
            return true;
        }
        return false;
    }
    
    private static boolean isConst(IRExpr node) {
        return node instanceof IRConst;
    }

    @Override
    public Optional<Void> visit(CFGMemAssignNode n) {
        if (visited.contains(n)) {
            return Optional.empty();
        }
        n.out().get(0).accept(this);
        visited.add(n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGReturnNode n) {
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGStartNode n) {
        if (visited.contains(n)) {
            return Optional.empty();
        }
        n.out().get(0).accept(this);
        visited.add(n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGSelfLoopNode n) {
        // TODO Auto-generated method stub
        return null;
    }
}
