package cyr7.x86.asm;

import cyr7.x86.visitor.AbstractASMVisitor;

import java.util.Objects;

public final class ASMConstArg implements ASMArg {
    public final long constant;

    public ASMConstArg(long constant) {
        this.constant = constant;
    }

    @Override
    public String getIntelArg() {
        return String.valueOf(constant);
    }

    @Override
    public String toString() {
        return "ASMConstArg [constant=" + constant + "]";
    }

    @Override
    public <R> R accept(AbstractASMVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASMConstArg that = (ASMConstArg) o;
        return constant == that.constant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(constant);
    }
}
