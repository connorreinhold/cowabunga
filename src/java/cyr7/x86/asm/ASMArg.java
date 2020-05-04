package cyr7.x86.asm;

import cyr7.x86.visitor.ASMArgVisitor;
import cyr7.x86.visitor.AbstractASMVisitor;

public interface ASMArg {

    public String getIntelArg();

    public <R> R accept(AbstractASMVisitor<R> visitor);

    <R> R accept(ASMArgVisitor<R> visitor);

}
