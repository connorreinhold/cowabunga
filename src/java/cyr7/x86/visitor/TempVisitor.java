package cyr7.x86.visitor;

import java.util.HashSet;
import java.util.Set;

import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;

public class TempVisitor extends AbstractASMVisitor<Set<ASMTempArg>> {

    public Set<ASMTempArg> visit(ASMTempArg t) {
        return Set.of(t);
    }

    public Set<ASMTempArg> visit(ASMAddrExpr e) {
        Set<ASMTempArg> temps = new HashSet<>();
        e.base.ifPresent(b -> temps.addAll(b.accept(this)));
        e.index.ifPresent(i -> temps.addAll(i.accept(this)));
        return temps;
    }

    @Override
    public Set<ASMTempArg> visit(ASMMemArg m) {
        return m.address.accept(this);
    }

    @Override
    public Set<ASMTempArg> visit(ASMInstr i) {
        Set<ASMTempArg> temps = new HashSet<>();
        i.args.forEach(arg -> {
            temps.addAll(arg.accept(this));
        });
        return temps;
    }

    @Override
    public Set<ASMTempArg> visit(ASMLabel lbl) {
        return Set.of();
    }

    @Override
    public Set<ASMTempArg> visit(ASMConstArg c) {
        return Set.of();
    }

    @Override
    public Set<ASMTempArg> visit(ASMLabelArg l) {
        return Set.of();
    }

    @Override
    public Set<ASMTempArg> visit(ASMReg type) {
        return Set.of();
    }

}