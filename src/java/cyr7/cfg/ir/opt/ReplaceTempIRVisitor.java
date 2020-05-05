package cyr7.cfg.ir.opt;

import java.util.Map;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
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

public class ReplaceTempIRVisitor implements MyIRVisitor<IRExpr> {

    private ReplaceTempIRVisitor() {}

    public final static ReplaceTempIRVisitor instance = new ReplaceTempIRVisitor();

    private Map<String, String> mapping;

    public IRExpr replace(IRExpr expr, Map<String, String> mapping) {
        this.mapping = mapping;
        var result = expr.accept(this);
        this.mapping = null;
        return result;
    }

    @Override
    public IRExpr visit(IRBinOp n) {
        var replaceLeft = n.left().accept(this);
        var replaceRight = n.right().accept(this);

        return new IRBinOp(n.location(), n.opType(), replaceLeft, replaceRight);
    }

    @Override
    public IRExpr visit(IRCall n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRConst n) {
        return n;
    }

    @Override
    public IRExpr visit(IRESeq n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRMem n) {
        return new IRMem(n.location(), n.expr().accept(this));
    }

    @Override
    public IRExpr visit(IRName n) {
        return n;
    }

    @Override
    public IRExpr visit(IRTemp n) {
        if (this.mapping.containsKey(n.name())) {
            return new IRTemp(n.location(), this.mapping.get(n.name()));
        } else {
            return n;
        }
    }

    @Override
    public IRExpr visit(IRCallStmt n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRCJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRCompUnit n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRExp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRFuncDecl n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRLabel n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRMove n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRReturn n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRSeq n) {
        throw new UnsupportedOperationException();
    }


}
