package cyr7.x86.asm;

import cyr7.x86.visitor.AbstractASMVisitor;

public class ASMLabelArg implements ASMArg {
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
}
