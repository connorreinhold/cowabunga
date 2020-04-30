package cyr7.cfg.visitor;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;

public abstract class AbstractCFGVisitor<R> {

    abstract public R visit(CFGCallNode n);

    abstract public R visit(CFGIfNode n);

    abstract public R visit(CFGVarAssignNode n);

    abstract public R visit(CFGMemAssignNode n);

    abstract public R visit(CFGReturnNode n);

    abstract public R visit(CFGStartNode n);

}