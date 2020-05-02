package cyr7.cfg.visitor;

import cyr7.cfg.nodes.ir.CFGCallNode;
import cyr7.cfg.nodes.ir.CFGIfNode;
import cyr7.cfg.nodes.ir.CFGMemAssignNode;
import cyr7.cfg.nodes.ir.CFGReturnNode;
import cyr7.cfg.nodes.ir.CFGStartNode;
import cyr7.cfg.nodes.ir.CFGVarAssignNode;

public interface IrCFGVisitor<R> {

    R visit(CFGCallNode n);

    R visit(CFGIfNode n);

    R visit(CFGVarAssignNode n);

    R visit(CFGMemAssignNode n);

    R visit(CFGReturnNode n);

    R visit(CFGStartNode n);

}