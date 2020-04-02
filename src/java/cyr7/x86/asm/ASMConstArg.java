package cyr7.x86.asm;

import cyr7.x86.visitor.AbstractASMVisitor;

public class ASMConstArg implements ASMArg {
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
}
