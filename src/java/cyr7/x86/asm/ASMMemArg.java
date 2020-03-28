package cyr7.x86.asm;

public class ASMMemArg implements ASMArg {
    final ASMAddrExpr address;

    public ASMMemArg(ASMAddrExpr address) {
        this.address = address;
    }

    @Override
    public String getIntelArg() {
        return "[ " + this.address.getIntelExpr() +  " ]";
    }

    @Override
    public String toString() {
        return "ASMMemArg [address=" + address + "]";
    }
}
