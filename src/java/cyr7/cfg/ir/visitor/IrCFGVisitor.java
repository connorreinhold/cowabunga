package cyr7.cfg.ir.visitor;

import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;

public interface IrCFGVisitor<R> {

    R visit(CFGCallNode n);

    R visit(CFGIfNode n);

    R visit(CFGVarAssignNode n);

    R visit(CFGMemAssignNode n);

    R visit(CFGReturnNode n);

    R visit(CFGStartNode n);

    R visit(CFGSelfLoopNode n);

}