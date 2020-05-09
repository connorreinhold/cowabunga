package cyr7.cfg.ir.dfa.loops;

import java.util.HashSet;
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
import cyr7.ir.nodes.IRTemp;
import cyr7.x86.pattern.BiPatternBuilder;

public class BasicInductionVariableVisitor
        implements IrCFGVisitor<Optional<Void>> {

    // Keep track of basic induction variables
    Set<String> inductionVars;
    Set<CFGNode> visited;
    Set<CFGNode> reachable;

    // If Temp a = ____ of wrong format, a cannot be a basic IV
    Set<String> invalidVars;

    // Pass in the set of nodes that form the loop.
    public BasicInductionVariableVisitor(Set<CFGNode> reachable) {
        this.visited = new HashSet<CFGNode>();
        this.inductionVars = new HashSet<String>();
        this.invalidVars = new HashSet<String>();
        this.reachable = reachable;
    }

    @Override
    public Optional<Void> visit(CFGCallNode n) {
        if (visited.contains(n) || !reachable.contains(n)) {
            return Optional.empty();
        }
        visited.add(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGIfNode n) {
        if (visited.contains(n) || !reachable.contains(n)) {
            return Optional.empty();
        }
        visited.add(n);
        n.out().get(0).accept(this);
        n.out().get(1).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGVarAssignNode n) {
        if (visited.contains(n) || invalidVars.contains(n.variable)
                || !reachable.contains(n)) {
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
                inductionVars.add(n.variable);
            } else if (binOp.opType() == OpType.SUB &&
                    tempMinusConst.matches(new Object[]{binOp.left(), binOp.right()})) {
                inductionVars.add(n.variable);
            } else {
                invalidVars.add(n.variable);
            }
        } else {
            invalidVars.add(n.variable);
        }

        visited.add(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGMemAssignNode n) {
        if (visited.contains(n) || !reachable.contains(n)) {
            return Optional.empty();
        }
        visited.add(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGReturnNode n) {
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGStartNode n) {
        if (visited.contains(n) || !reachable.contains(n)) {
            return Optional.empty();
        }
        visited.add(n);
        n.out().get(0).accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(CFGSelfLoopNode n) {
        return Optional.empty();
    }
}
