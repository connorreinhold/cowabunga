package cyr7.x86.asm;

import java.util.Objects;

import cyr7.x86.visitor.AbstractASMVisitor;

public class ASMTempArg implements ASMTempRegArg {

    public enum Size {
        QWORD, BYTE
    }

    public final String name;
    public final Size size;

    public ASMTempArg(String name, Size size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ASMTempArg)) {
            return false;
        }
        ASMTempArg other = (ASMTempArg) obj;
        return Objects.equals(name, other.name);
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
