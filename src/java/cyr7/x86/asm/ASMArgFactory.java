
package cyr7.x86.asm;

import java.util.Optional;

import cyr7.x86.asm.ASMAddrExpr.ScaleValues;

public class ASMArgFactory {

    public static ASMArgFactory instance = new ASMArgFactory();

    private ASMArgFactory() {
    }

    public ASMTempArg temp(String t, ASMRegSize size) {
        return new ASMTempArg(t, size);
    }

    public ASMTempArg temp(String t) {
        return temp(t, ASMRegSize.QWORD);
    }

    public ASMConstArg constant(long n) {
        return new ASMConstArg(n);
    }

    public ASMLabelArg label(String name) {
        return new ASMLabelArg(name);
    }

    public ASMAddrExpr addr(Optional<ASMTempRegArg> base, ScaleValues scale,
            Optional<ASMTempRegArg> index, long displacement) {
        return new ASMAddrExpr(base, scale, index, displacement);
    }

    public ASMMemArg mem(ASMAddrExpr addr) {
        return new ASMMemArg(addr);
    }

    public ASMMemArg mem(ASMTempRegArg base) {
        return mem(addr(Optional.of(base), ScaleValues.ONE, Optional.empty(), 0));
    }

    public ASMMemArg mem(ASMTempRegArg base, ASMTempRegArg index) {
        return mem(addr(Optional.of(base), ScaleValues.ONE, Optional.of(index), 0));
    }

    public ASMMemArg mem(ASMTempRegArg base, long displacement) {
        return mem(addr(Optional.of(base), ScaleValues.ONE, Optional.empty(), displacement));
    }

}