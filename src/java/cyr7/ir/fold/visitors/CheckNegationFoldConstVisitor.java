package cyr7.ir.fold.visitors;

import java.util.Optional;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
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

public class CheckNegationFoldConstVisitor implements MyIRVisitor<Optional<IRExpr>> {

    public final static CheckNegationFoldConstVisitor instance =
            new CheckNegationFoldConstVisitor();

    @Override
    public Optional<IRExpr> visit(IRBinOp n) {
        if (n.opType() == OpType.XOR && n.left().isConstant()
                && n.left().constant() == 1) {
            return Optional.of(n.right());
        }
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRCall n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRConst n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRESeq n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRMem n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRName n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRTemp n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRCallStmt n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRCJump n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRCompUnit n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRExp n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRFuncDecl n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRJump n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRLabel n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRMove n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRReturn n) {
        return Optional.empty();
    }

    @Override
    public Optional<IRExpr> visit(IRSeq n) {
        return Optional.empty();
    }

}
