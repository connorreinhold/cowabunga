package cyr7.x86.asm;

public class ASMMemArg implements ASMArg {
    final ASMAddrExpr address;

    public ASMMemArg(ASMAddrExpr address) {
        this.address = address;
    }
}
