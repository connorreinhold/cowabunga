package cyr7.ir;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import cyr7.ir.LoweringVisitor.Result;
import cyr7.ir.fold.MyIRVisitor;
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
import cyr7.util.OneOfTwo;
import polyglot.util.Pair;

public class LoweringVisitor implements MyIRVisitor<Result> {

    public static class Result extends OneOfTwo<List<IRStmt>, Pair<List<IRStmt>, IRExpr>> {

        public static Result stmts(List<IRStmt> statement) {
            return new Result(statement, null);
        }

        public static Result expr(List<IRStmt> sideEffects, IRExpr expr) {
            return new Result(null, new Pair<>(sideEffects, expr));
        }

        protected Result(List<IRStmt> stmts, Pair<List<IRStmt>, IRExpr> expr) {
            super(stmts, expr);
        }

    }

    private static boolean binOpCommutes(IRExpr e1, IRExpr e2) {
        return false;
    }

    // source is a
    // TODO
    private static boolean moveCommutes(IRExpr dest, IRExpr source) {
        return false;
    }

    private final IdGenerator generator;

    public LoweringVisitor(IdGenerator generator) {
        this.generator = generator;
    }

    @Override
    public Result visit(IRBinOp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        var leftResult = n.left().accept(this).assertSecond();
        var rightResult = n.right().accept(this).assertSecond();
        List<IRStmt> stmts = new ArrayList<>();
        if (binOpCommutes(n.left(), n.right())) {
            stmts.addAll(leftResult.part1());
            stmts.addAll(rightResult.part1());

            IRExpr expr = make.IRBinOp(n.opType(),
                leftResult.part2(),
                rightResult.part2());

            return Result.expr(stmts, expr);

        } else {
            String t1 = generator.newTemp();
            stmts.addAll(leftResult.part1());
            stmts.add(make.IRMove(
                make.IRTemp(t1),
                leftResult.part2()
            ));
            stmts.addAll(rightResult.part1());

            IRExpr expr = make.IRBinOp(n.opType(),
                make.IRTemp(t1),
                rightResult.part2());

            return Result.expr(stmts, expr);
        }
    }

    @Override
    public Result visit(IRCall n) {
//        IRNodeFactory make = new IRNodeFactory_c(n.location());
//
//        List<IRStmt> stmts = new LinkedList<>();
//        var iterator = n.args().iterator();
//
//        int i = 0;
//        while (iterator.hasNext()) {
//            var nextArg = iterator.next();
//            Result argResult = nextArg.accept(this);
//            var resultPair = argResult.assertSecond();
//            stmts.addAll(resultPair.part1());
//            var argTemp = make.IRTemp(generator.argTemp(i));
//            stmts.add(make.IRMove(argTemp, resultPair.part2()));
//            i++;
//        }
//
//        stmts.add(
//                make.IRCallStmt(List.of(), make.IRName(n.label()), List.of()));

//        String t = generator.newTemp();
//
//        List<IRStmt> statements = new ArrayList<>();
//        for (int i = 0; i < n.args().size(); i++) {
//            IRExpr arg = n.args().get(i);
//
//            String argTemp = generator.argTemp(i);
//
//            var result = arg.accept(this).assertSecond();
//            statements.addAll(result.part1());
//            statements.add(
//                make.IRMove(
//                    make.IRTemp(argTemp),
//                    result.part2()
//                )
//            );
//        }

//        return Result.expr(statements, make.IRTemp(t));
        throw new UnsupportedOperationException();
    }

    @Override
    public Result visit(IRConst n) {
        return Result.expr(List.of(), n);
    }

    @Override
    public Result visit(IRESeq n) {
        List<IRStmt> stmts = new ArrayList<>();
        stmts.addAll(n.accept(this).assertFirst());
        var result = n.expr().accept(this).assertSecond();
        stmts.addAll(result.part1());
        return Result.expr(stmts, result.part2());
    }

    @Override
    public Result visit(IRMem n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        var index = n.expr().accept(this).assertSecond();
        return Result.expr(index.part1(), make.IRMem(index.part2()));
    }

    @Override
    public Result visit(IRName n) {
        return Result.expr(List.of(), n);
    }

    @Override
    public Result visit(IRTemp n) {
        return Result.expr(List.of(), n);
    }

    @Override
    public Result visit(IRCallStmt n) {
        return null;
    }

    @Override
    public Result visit(IRCJump n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        var index = n.cond().accept(this).assertSecond();

        List<IRStmt> stmts = new ArrayList<>(index.part1());
        stmts.add(make.IRCJump(index.part2(), n.trueLabel(), n.falseLabel()));

        return Result.stmts(stmts);
    }

    @Override
    public Result visit(IRCompUnit n) {
        return null;
    }

    @Override
    public Result visit(IRExp n) {
        return Result.stmts(n.expr().accept(this).assertSecond().part1());
    }

    @Override
    public Result visit(IRFuncDecl n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRStmt> stmts = new LinkedList<>();

        stmts.add(make.IRLabel(n.label()));
        var body = n.body().accept(this).assertFirst();
        stmts.addAll(body);
        return Result.stmts(stmts);
    }

    @Override
    public Result visit(IRJump n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        var index = n.target().accept(this).assertSecond();

        List<IRStmt> stmts = new ArrayList<>(index.part1());
        stmts.add(make.IRJump(index.part2()));

        return Result.stmts(stmts);
    }

    @Override
    public Result visit(IRLabel n) {
        return Result.stmts(List.of(n));
    }

    @Override
    public Result visit(IRMove n) {
        var handler = new MoveHandleVisitor(this);
        return Result.stmts(n.accept(handler));
    }

    @Override
    public Result visit(IRReturn n) {
        return Result.stmts(List.of(n));
    }

    @Override
    public Result visit(IRSeq n) {
        return Result.stmts(n.stmts());
    }


    private class MoveHandleVisitor implements MyIRVisitor<List<IRStmt>> {

        private Optional<List<IRStmt>> s2;
        private Optional<IRExpr> e2;
        private LoweringVisitor lower;

        public MoveHandleVisitor(LoweringVisitor visitor) {
            this.lower = visitor;
        };

        @Override
        public List<IRStmt> visit(IRMove n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());
            if (moveCommutes(n.target(), n.source())) {
                var targetResult = n.target().accept(lower).assertSecond();
                var sourceResult = n.source().accept(lower).assertSecond();

                List<IRStmt> stmts = new ArrayList<>();
                stmts.addAll(targetResult.part1());
                stmts.addAll(sourceResult.part1());
                stmts.add(make.IRMove(targetResult.part2(),
                        sourceResult.part2()));
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


}
