package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.nodes.AsmCFGIfNode;
import cyr7.cfg.asm.nodes.AsmCFGOpNode;
import cyr7.cfg.asm.nodes.AsmCFGReturnNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cfg.asm.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collections;
import java.util.Set;

public final class UsesVisitor implements AsmCFGVisitor<Set<ASMTempRegArg>> {

    private final Set<ASMTempRegArg> returnRegisters;

    public UsesVisitor(Set<ASMTempRegArg> returnRegisters) {
        this.returnRegisters = returnRegisters;
    }

    @Override
    public Set<ASMTempRegArg> visit(AsmCFGOpNode n) {
        return null;
    }

    @Override
    public Set<ASMTempRegArg> visit(AsmCFGIfNode n) {
        return null;
    }

    @Override
    public Set<ASMTempRegArg> visit(AsmCFGReturnNode n) {
        return returnRegisters;
    }

    @Override
    public Set<ASMTempRegArg> visit(AsmCFGStartNode n) {
        return Collections.emptySet();
    }

}
