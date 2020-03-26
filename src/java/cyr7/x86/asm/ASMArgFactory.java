
package cyr7.x86.asm;

import java.util.Optional;

import cyr7.x86.asm.ASMAddrExpr.ScaleValues;

public class ASMArgFactory {

    public static ASMArgFactory instance = new ASMArgFactory();

    private ASMArgFactory() {
    }

    public ASMConstArg constant(long n) {
        return new ASMConstArg(n);
    }

    public ASMLabelArg label(String name) {
        return new ASMLabelArg(name);
    }

    public ASMAddrExpr addr(Optional<ASMReg> base, ScaleValues scale, Optional<ASMReg> index, int displacement) {
        return new ASMAddrExpr(base, scale, index, displacement);
    }

    public ASMMemArg mem(ASMAddrExpr addr) {
        return new ASMMemArg(addr);
    }

}