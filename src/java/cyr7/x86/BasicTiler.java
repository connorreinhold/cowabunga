package cyr7.x86;

import cyr7.ir.nodes.*;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.ASMInstr;

public class BasicTiler implements MyIRVisitor<ASMInstr> {
    @Override
    public ASMInstr visit(IRBinOp n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRCall n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRConst n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRESeq n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRMem n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRName n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRTemp n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRCallStmt n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRCJump n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRCompUnit n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRExp n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRFuncDecl n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRJump n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRLabel n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRMove n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRReturn n) {
        return null;
    }

    @Override
    public ASMInstr visit(IRSeq n) {
        return null;
    }
}
