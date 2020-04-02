package cyr7.x86.asm;

import cyr7.x86.visitor.AbstractASMVisitor;

public class ASMLabel implements ASMLine {

    public final String label;

    public ASMLabel(String label) {
        this.label = label;
    }

    @Override
    public String getIntelAssembly() {
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

}
