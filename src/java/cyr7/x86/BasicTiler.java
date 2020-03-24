package cyr7.x86;

import cyr7.ir.nodes.*;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMFactory;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.TilerData;
import cyr7.ir.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasicTiler implements MyIRVisitor<TilerData> {
    
    private final IdGenerator generator;
    
    public BasicTiler(IdGenerator generator) {
        this.generator = generator;
    }
    
    @Override
    public TilerData visit(IRBinOp n) {
        List<ASMInstr> insns = new ArrayList<ASMInstr>();
        IRTemp ret = generator.newTemp();
        
        TilerData left = n.left().accept(this);
        TilerData right = n.right().accept(this);
        insns.add(ASMFactory.Mov(ret, left ));
        return null;
    }

    @Override
    public TilerData visit(IRCall n) {
        return null;
    }

    @Override
    public TilerData visit(IRConst n) {
        return null;
    }

    @Override
    public TilerData visit(IRESeq n) {
        return null;
    }

    @Override
    public TilerData visit(IRMem n) {
        return null;
    }

    @Override
    public TilerData visit(IRName n) {
        return null;
    }

    @Override
    public TilerData visit(IRTemp n) {
        return null;
    }

    @Override
    public TilerData visit(IRCallStmt n) {
        return null;
    }

    @Override
    public TilerData visit(IRCJump n) {
        return null;
    }

    @Override
    public TilerData visit(IRCompUnit n) {
        return null;
    }

    @Override
    public TilerData visit(IRExp n) {
        return null;
    }

    @Override
    public TilerData visit(IRFuncDecl n) {
        return null;
    }

    @Override
    public TilerData visit(IRJump n) {
        return null;
    }

    @Override
    public TilerData visit(IRLabel n) {
        return null;
    }

    @Override
    public TilerData visit(IRMove n) {
        return null;
    }

    @Override
    public TilerData visit(IRReturn n) {
        return null;
    }

    @Override
    public TilerData visit(IRSeq n) {
        return null;
    }
}
