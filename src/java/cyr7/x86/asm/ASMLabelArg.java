package cyr7.x86.asm;

import cyr7.x86.visitor.ASMArgVisitor;
import cyr7.x86.visitor.AbstractASMVisitor;

import java.util.Objects;

public final class ASMLabelArg implements ASMArg {
    public final String label;

    public ASMLabelArg(String label) {
        this.label = label;
    }

    @Override
    public String getIntelArg() {
        return label;
    }

    @Override
    public String toString() {
        return "ASMLabelArg [label=" + label + "]";
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
        ASMLabelArg that = (ASMLabelArg) o;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
