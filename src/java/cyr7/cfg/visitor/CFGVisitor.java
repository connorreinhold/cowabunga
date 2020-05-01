package cyr7.cfg.visitor;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;

public interface CFGVisitor<R> {

    R visit(CFGCallNode n);

    R visit(CFGIfNode n);

    R visit(CFGVarAssignNode n);

    R visit(CFGMemAssignNode n);

    R visit(CFGReturnNode n);

    R visit(CFGStartNode n);

}