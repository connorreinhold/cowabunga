package cyr7.cfg.asm.visitor;

import cyr7.cfg.asm.nodes.AsmCFGIfNode;
import cyr7.cfg.asm.nodes.AsmCFGOpNode;
import cyr7.cfg.asm.nodes.AsmCFGReturnNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;

public interface AsmCFGVisitor<R> {

    R visit(AsmCFGOpNode n);
    
    R visit(AsmCFGIfNode n);
    
    R visit(AsmCFGReturnNode n);
    
    R visit(AsmCFGStartNode n);
}
