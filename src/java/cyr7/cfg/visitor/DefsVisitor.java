package cyr7.cfg.visitor;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;

import java.util.HashSet;
import java.util.Set;

public enum DefsVisitor implements CFGVisitor<Set<String>> {

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
