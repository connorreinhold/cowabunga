package cyr7.x86.asm;

import cyr7.x86.visitor.AbstractASMVisitor;

public interface ASMLine {

    default String getIntelAssembly() {
        return getIntelAssembly(true, true);
    }

    String getIntelAssembly(boolean indented, boolean withComments);

    <R> R accept(AbstractASMVisitor<R> visitor);

}
