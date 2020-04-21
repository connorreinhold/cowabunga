package cyr7.x86.visitor;

import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMAssemblerDirective;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;

public abstract class AbstractASMVisitor<R> {

    public abstract R visit(ASMTempArg t);

    public abstract R visit(ASMAddrExpr e);

    public abstract R visit(ASMMemArg m);

    public abstract R visit(ASMReg type);

    public abstract R visit(ASMInstr i);

    public abstract R visit(ASMLabel lbl);

    public abstract R visit(ASMConstArg c);

    public abstract R visit(ASMLabelArg l);

    public abstract R visit(ASMAssemblerDirective l);

}