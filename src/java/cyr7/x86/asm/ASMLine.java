package cyr7.x86.asm;

import cyr7.x86.visitor.AbstractASMVisitor;

public interface ASMLine {

    public String getIntelAssembly();

    public abstract <R> R accept(AbstractASMVisitor<R> visitor);

}
