package cyr7.ir.lowering;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;
import cyr7.visitor.MyIRVisitor;

public class EvaluatesMemExprVisitor implements MyIRVisitor<Boolean> {

    @Override
    public Boolean visit(IRBinOp n) {
        return n.left().accept(this) && n.right().accept(this);
    }

    @Override
    public Boolean visit(IRConst n) {
        return true;
    }

    @Override
    public Boolean visit(IRTemp n) {
        return false;
    }

    @Override
    public Boolean visit(IRMem n) {
        return n.expr().accept(this);
    }

    @Override
    public Boolean visit(IRCall n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRESeq n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRName n) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Boolean visit(IRCallStmt n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRCJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRCompUnit n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRExp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRFuncDecl n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRLabel n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRMove n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRReturn n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRSeq n) {
        throw new UnsupportedOperationException();
    }

}
