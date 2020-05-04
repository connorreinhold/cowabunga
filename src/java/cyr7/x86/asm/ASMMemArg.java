package cyr7.x86.asm;

import java.util.Objects;
import java.util.Optional;

import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.visitor.ASMArgVisitor;
import cyr7.x86.visitor.AbstractASMVisitor;

public final class ASMMemArg implements ASMArg {
    public final ASMAddrExpr address;

    public ASMMemArg(ASMAddrExpr address) {
        this.address = address;
    }

    public ASMMemArg(ASMTempRegArg arg) {
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

    @Override
    public <R> R accept(ASMArgVisitor<R> visitor) {
        return visitor.accept(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASMMemArg asmMemArg = (ASMMemArg) o;
        return Objects.equals(address, asmMemArg.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
