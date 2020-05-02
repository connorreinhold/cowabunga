package cyr7.cfg.visitor;

import cyr7.cfg.nodes.ir.CFGCallNode;
import cyr7.cfg.nodes.ir.CFGIfNode;
import cyr7.cfg.nodes.ir.CFGMemAssignNode;
import cyr7.cfg.nodes.ir.CFGReturnNode;
import cyr7.cfg.nodes.ir.CFGStartNode;
import cyr7.cfg.nodes.ir.CFGVarAssignNode;
import cyr7.ir.interpret.Configuration;
import cyr7.ir.visit.IRExprVarsVisitor;

import java.util.HashSet;
import java.util.Set;

public enum UsesVisitor implements IrCFGVisitor<Set<String>> {

    INSTANCE;

    @Override
    public Set<String> visit(CFGCallNode n) {
        return n.call.accept(IRExprVarsVisitor.INSTANCE);
    }

    @Override
    public Set<String> visit(CFGIfNode n) {
        return n.cond.accept(IRExprVarsVisitor.INSTANCE);
    }

    @Override
    public Set<String> visit(CFGVarAssignNode n) {
        return n.value.accept(IRExprVarsVisitor.INSTANCE);
    }

    @Override
    public Set<String> visit(CFGMemAssignNode n) {
        return n.value.accept(IRExprVarsVisitor.INSTANCE);
    }

    @Override
    public Set<String> visit(CFGReturnNode n) {
        Set<String> retValues = new HashSet<>();
        for (int i = 0; i < n.numReturnValues; i++) {
            retValues.add(Configuration.ABSTRACT_RET_PREFIX + i);
        }
        return retValues;
    }

    @Override
    public Set<String> visit(CFGStartNode n) {
        return Set.of();
    }

}
