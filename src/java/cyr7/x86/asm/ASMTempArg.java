package cyr7.x86.asm;

import java.util.Objects;

import cyr7.x86.visitor.AbstractASMVisitor;

public final class ASMTempArg implements ASMTempRegArg {

    public final String name;
    public final ASMRegSize size;

    public ASMTempArg(String name, ASMRegSize size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASMTempArg that = (ASMTempArg) o;
        return Objects.equals(name, that.name) &&
            size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size);
    }

    @Override
    public String getIntelArg() {
        return this.name;
    }

    @Override
    public String toString() {
        return "ASMTempArg [name=" + name + "]";
    }

    @Override
    public <R> R accept(AbstractASMVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
