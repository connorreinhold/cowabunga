package cyr7.x86.asm;

import cyr7.x86.visitor.AbstractASMVisitor;

import java.util.Objects;

public final class ASMLabel implements ASMLine {

    public final String label;

    public ASMLabel(String label) {
        this.label = label;
    }

    @Override
    public String getIntelAssembly(boolean includeWhitespace, boolean includeComments) {
        // label does not include any comments, we can safely ignore includeComments
        return label + ":";
    }

    @Override
    public String toString() {
        return "ASMLabel [label=" + label + "]";
    }

    @Override
    public <R> R accept(AbstractASMVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASMLabel asmLabel = (ASMLabel) o;
        return Objects.equals(label, asmLabel.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
