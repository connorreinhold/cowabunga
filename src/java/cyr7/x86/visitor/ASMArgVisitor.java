package cyr7.x86.visitor;

import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;

public interface ASMArgVisitor<R> {

    R accept(ASMConstArg arg);
    R accept(ASMLabelArg arg);
    R accept(ASMTempArg arg);
    R accept(ASMMemArg arg);
    R accept(ASMReg arg);

}
