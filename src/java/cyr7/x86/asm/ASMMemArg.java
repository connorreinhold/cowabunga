package cyr7.x86.asm;

import java.util.Optional;

import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.visitor.AbstractASMVisitor;

public class ASMMemArg implements ASMArg {
    public final ASMAddrExpr address;

    public ASMMemArg(ASMAddrExpr address) {
        this.address = address;
    }

    public ASMMemArg(ASMArg arg) {
        this.address = new ASMAddrExpr(Optional.of(arg), ScaleValues.ONE,
                Optional.empty(), 0);
    }

    @Override
    public String getIntelArg() {
        return "[ " + this.address.getIntelExpr() + " ]";
    }

    @Override
    public String toString() {
        return "ASMMemArg [address=" + address + "]";
    }

    @Override
    public <R> R accept(AbstractASMVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
