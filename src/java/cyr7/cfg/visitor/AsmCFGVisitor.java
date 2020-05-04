package cyr7.cfg.visitor;

import cyr7.cfg.nodes.asm.AsmCFGIfNode;
import cyr7.cfg.nodes.asm.AsmCFGOpNode;
import cyr7.cfg.nodes.asm.AsmCFGReturnNode;
import cyr7.cfg.nodes.asm.AsmCFGStartNode;

public interface AsmCFGVisitor<R> {

    R visit(AsmCFGOpNode n);
    
    R visit(AsmCFGIfNode n);
    
    R visit(AsmCFGReturnNode n);
    
    R visit(AsmCFGStartNode n);
}
