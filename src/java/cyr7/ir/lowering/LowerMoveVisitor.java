package cyr7.ir.lowering;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import cyr7.ir.IdGenerator;
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
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.visitor.MyIRVisitor;

class LowerMoveVisitor implements MyIRVisitor<List<IRStmt>> {

    private Optional<List<IRStmt>> s2;
    private Optional<IRExpr> e2;
    private LoweringVisitor lower;
    private IdGenerator generator;

    public LowerMoveVisitor(LoweringVisitor visitor,
                            IdGenerator generator) {
        this.lower = visitor;
        this.generator = generator;
    };

    @Override
    public List<IRStmt> visit(IRMove n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        if (lower.commutes(n.target(), n.source())) {
            var targetResult = n.target().accept(lower).assertSecond();
            var sourceResult = n.source().accept(lower).assertSecond();

            List<IRStmt> stmts = new ArrayList<>();
            stmts.addAll(targetResult.part1());
            stmts.addAll(sourceResult.part1());
            stmts.add(make.IRMove(targetResult.part2(), sourceResult.part2()));
            return stmts;
        } else {
            var sourceResult = n.source().accept(lower).assertSecond();

            this.s2 = Optional.of(sourceResult.part1());
            this.e2 = Optional.of(sourceResult.part2());

            return n.target().accept(this);
        }
    }

    @Override
    public List<IRStmt> visit(IRMem n) {
        if (this.s2.isEmpty() || this.e2.isEmpty())
            throw new UnsupportedOperationException();

        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRStmt> stmts = new ArrayList<>();

        IRExpr memInner = n.expr();
        var exprResult = memInner.accept(lower).assertSecond();
        var s1 = exprResult.part1();
        var e1 = exprResult.part2();

        IRTemp t = make.IRTemp(generator.newTemp());

        stmts.addAll(s1);
        stmts.add(make.IRMove(t, e1));
        stmts.addAll(s2.get());
        stmts.add(make.IRMove(make.IRMem(t), e2.get()));
        return stmts;
    }

    @Override
    public List<IRStmt> visit(IRTemp n) {
        if (this.s2.isEmpty() || this.e2.isEmpty())
            throw new UnsupportedOperationException();

        List<IRStmt> stmts = new ArrayList<>();
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        stmts.addAll(s2.get());
        stmts.add(make.IRMove(n, e2.get()));
        return stmts;
    }

    @Override
    public List<IRStmt> visit(IRBinOp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRCall n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRConst n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRESeq n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRName n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRCallStmt n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRCJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRCompUnit n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRExp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRFuncDecl n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRLabel n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRReturn n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRStmt> visit(IRSeq n) {
        throw new UnsupportedOperationException();
    }
}