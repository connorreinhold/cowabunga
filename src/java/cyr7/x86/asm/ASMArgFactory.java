
package cyr7.x86.asm;

import java.util.Optional;

import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMTempArg.Size;

public class ASMArgFactory {

    public static ASMArgFactory instance = new ASMArgFactory();

    private ASMArgFactory() {
    }

    public ASMTempArg temp(String t, Size size) {
        return new ASMTempArg(t, size);
    }

    public ASMConstArg constant(long n) {
        return new ASMConstArg(n);
    }

    public ASMLabelArg label(String name) {
        return new ASMLabelArg(name);
    }

    public ASMAddrExpr addr(Optional<ASMTempRegArg> base, ScaleValues scale,
            Optional<ASMTempRegArg> index, int displacement) {
        return new ASMAddrExpr(base, scale, index, displacement);
    }

    public ASMMemArg mem(ASMAddrExpr addr) {
        return new ASMMemArg(addr);
    }

}