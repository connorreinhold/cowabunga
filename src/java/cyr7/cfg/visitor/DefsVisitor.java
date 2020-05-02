package cyr7.cfg.visitor;

import java.util.HashSet;
import java.util.Set;

import cyr7.cfg.nodes.ir.CFGCallNode;
import cyr7.cfg.nodes.ir.CFGIfNode;
import cyr7.cfg.nodes.ir.CFGMemAssignNode;
import cyr7.cfg.nodes.ir.CFGReturnNode;
import cyr7.cfg.nodes.ir.CFGStartNode;
import cyr7.cfg.nodes.ir.CFGVarAssignNode;

public enum DefsVisitor implements IrCFGVisitor<Set<String>> {

    INSTANCE;

    @Override
    public Set<String> visit(CFGCallNode n) {
        return new HashSet<>(n.call.collectors());
    }

    @Override
    public Set<String> visit(CFGIfNode n) {
        return Set.of();
    }

    @Override
    public Set<String> visit(CFGVarAssignNode n) {
        return Set.of(n.variable);
    }

    @Override
    public Set<String> visit(CFGMemAssignNode n) {
        return Set.of();
    }

    @Override
    public Set<String> visit(CFGReturnNode n) {
        return Set.of();
    }

    @Override
    public Set<String> visit(CFGStartNode n) {
        return Set.of();
    }
}
