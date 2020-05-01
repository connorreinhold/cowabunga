package cyr7.cfg.visitor;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;

import java.util.Set;

public enum UsesVisitor implements AbstractCFGVisitor<Set<String>> {

    INSTANCE;

    @Override
    public Set<String> visit(CFGCallNode n) {
        return null;
    }

    @Override
    public Set<String> visit(CFGIfNode n) {
        return null;
    }

    @Override
    public Set<String> visit(CFGVarAssignNode n) {
        return null;
    }

    @Override
    public Set<String> visit(CFGMemAssignNode n) {
        return null;
    }

    @Override
    public Set<String> visit(CFGReturnNode n) {
        return null;
    }

    @Override
    public Set<String> visit(CFGStartNode n) {
        return null;
    }
}
