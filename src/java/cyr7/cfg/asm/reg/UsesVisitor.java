package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.nodes.AsmCFGIfNode;
import cyr7.cfg.asm.nodes.AsmCFGOpNode;
import cyr7.cfg.asm.nodes.AsmCFGReturnNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cfg.asm.visitor.AsmCFGVisitor;
import cyr7.util.Sets;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collections;
import java.util.Set;

public final class UsesVisitor implements AsmCFGVisitor<Set<ASMTempRegArg>> {

    private final Set<ASMReg> returnRegisters;
    private static final Set<ASMReg> calleeSavedRegisters = Set.of(
        ASMReg.RBX,
        ASMReg.RBP,
        ASMReg.RDI,
        ASMReg.RSI,
        ASMReg.RSP,
        ASMReg.R12,
        ASMReg.R13,
        ASMReg.R14,
        ASMReg.R15);

    public UsesVisitor(Set<ASMReg> returnRegisters) {
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
        // why do this? Appel p. 228
        return Sets.union(returnRegisters, calleeSavedRegisters);
    }

    @Override
    public Set<ASMTempRegArg> visit(AsmCFGStartNode n) {
        return Collections.emptySet();
    }

}
