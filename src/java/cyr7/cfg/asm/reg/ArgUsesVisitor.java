package cyr7.cfg.asm.reg;

import cyr7.util.Sets;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempRegArg;
import cyr7.x86.visitor.ASMArgVisitor;

import java.util.Collections;
import java.util.Set;

enum ArgUsesVisitor implements ASMArgVisitor<Set<ASMTempRegArg>> {
    INSTANCE;

    @Override
    public Set<ASMTempRegArg> accept(ASMConstArg arg) {
        return Collections.emptySet();
    }

    @Override
    public Set<ASMTempRegArg> accept(ASMLabelArg arg) {
        return Collections.emptySet();
    }

    @Override
    public Set<ASMTempRegArg> accept(ASMTempArg arg) {
        return Set.of(arg);
    }

    @Override
    public Set<ASMTempRegArg> accept(ASMMemArg arg) {
        ASMAddrExpr addr = arg.address;
        // temps/regs used by addr is
        // base (if present) union index (if present)
        return Sets.difference(Sets.union(
            addr.base.map(Set::of).orElse(Set.of()),
            addr.index.map(Set::of).orElse(Set.of())
        ), Set.of(ASMReg.RBP, ASMReg.RSP));
    }

    @Override
    public Set<ASMTempRegArg> accept(ASMReg arg) {
        if (arg == ASMReg.RBP || arg == ASMReg.RSP) {
            return Set.of();
        }
        return Set.of(arg);
    }

}
