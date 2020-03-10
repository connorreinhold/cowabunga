package cyr7.ir.lowering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.ir.IdGenerator;
import cyr7.ir.lowering.LoweringVisitor.Result;
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
import cyr7.util.OneOfThree;
import cyr7.util.OneOfTwo;
import cyr7.visitor.MyIRVisitor;
import polyglot.util.Pair;

public class LoweringVisitor implements MyIRVisitor<Result> {

    public static class Result
            extends OneOfThree<List<IRStmt>, Pair<List<IRStmt>, IRExpr>, IRCompUnit> {

        public static Result stmts(List<IRStmt> statement) {
            return new Result(statement, null, null);
        }

        public static Result expr(List<IRStmt> sideEffects, IRExpr expr) {
            return new Result(null, new Pair<>(sideEffects, expr), null);
        }
        
        public static Result compUnit(IRCompUnit compUnit) {
            return new Result(null, null, compUnit);
        }

        protected Result(List<IRStmt> stmts, Pair<List<IRStmt>, IRExpr> expr, IRCompUnit compUnit) {
            super(stmts, expr, compUnit);
        }

    }

    private final IRExprCommutableChecker commutability;
    private final IdGenerator generator;

    public LoweringVisitor(IdGenerator generator) {
        this(generator, true);
    }

    public LoweringVisitor(IdGenerator generator,
            boolean commuteOptimizationEnabled) {
        this.generator = generator;
        this.commutability = new IRExprCommutableChecker(this,
                commuteOptimizationEnabled);
    }

    // Methods

    /**
     * Checks if there exists an instruction in {@code e2} that affects
     * {@code e1}. Returns {@code true} if {@code e2} does not {@code e1}.
     */
    public boolean commutes(IRExpr e1, IRExpr e2) {
        if (!commutability.commutes(e1, e2)) {
            return false;
        }

        var e1Result = e1.accept(this).assertSecond();
        var e2Result = e2.accept(this).assertSecond();

        List<IRStmt> e2Stmt = e2Result.part1();
        if (containsJumps(e2Stmt)) {
            return false;
        }

        // If e2 assigns to a MEM(e), where e cannot be simplified to
        // a constant.
        List<IRStmt> e1Stmt = e1Result.part1();
        if (!(this.canDetermineEquality(e2Stmt)
            && this.canDetermineEquality(e1Stmt))) {
            return false;
        }

        Set<IRExpr> e1UsedExprSet = this.setOfUsedExpr(e1Stmt);
        Set<IRExpr> e2DestSet = this.setOfDestinations(e2Stmt);

        return Collections.disjoint(e1UsedExprSet, e2DestSet);
    }

    /**
     * Returns true if there is a Jump
     *
     * @param stmts
     * @return
     */
    private boolean containsJumps(List<IRStmt> stmts) {
        ContainsJumpsStmtVisitor hasJumps = new ContainsJumpsStmtVisitor();
        for (var stmt : stmts) {
            if (stmt.accept(hasJumps)) {
                return true;
            }
        }
        return false;
    }

    private Set<IRExpr> setOfDestinations(List<IRStmt> stmts) {
        return stmts.stream()
            .filter(s -> (s instanceof IRMove))
            .map(move -> ((IRMove) move).target())
            .collect(Collectors.toSet());
    }

    private Set<IRExpr> setOfUsedExpr(List<IRStmt> stmts) {
        var visitor = new TempsAccessedExprVisitor();
        return stmts.stream()
            .flatMap(s -> s.accept(visitor).stream())
            .collect(Collectors.toSet());
    }

    private boolean canDetermineEquality(List<IRStmt> stmts) {
        var visitor = new EvaluatesMemExprVisitor();
        return stmts.stream().reduce(true, (u, s) -> {
            return u && s.accept(visitor).booleanValue();
        }, (u, i) -> true);    }

    // Visitor

    @Override
    public Result visit(IRBinOp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        var leftResult = n.left().accept(this).assertSecond();
        var rightResult = n.right().accept(this).assertSecond();
        List<IRStmt> stmts = new ArrayList<>();
        if (commutes(n.left(), n.right())) {
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

    /**
     * The output will be a Pair(List of IRStmt, IRExpr)
     */
    @Override
    public Result visit(IRCall n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());

        List<IRStmt> stmts = new LinkedList<>();
        var iterator = n.args().iterator();

        int i = 0;
        while (iterator.hasNext()) {
            var nextArg = iterator.next();
            Result argResult = nextArg.accept(this);
            var resultPair = argResult.assertSecond();
            stmts.addAll(resultPair.part1());
            var argTemp = make.IRTemp(generator.argTemp(i));
            stmts.add(make.IRMove(argTemp, resultPair.part2()));
            i++;
        }

        stmts.add(make.IRCallStmt(n.target()));

        IRTemp t = make.IRTemp(generator.newTemp());
        stmts.add(make.IRMove(t, make.IRTemp(generator.retTemp(0))));

        return Result.expr(stmts, t);
    }

    @Override
    public Result visit(IRConst n) {
        return Result.expr(List.of(), n);
    }

    @Override
    public Result visit(IRESeq n) {
        List<IRStmt> stmts = new ArrayList<>();
        stmts.addAll(n.stmt().accept(this).assertFirst());
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
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRStmt> stmts = new LinkedList<>();

        var iterator = n.args().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            var nextArg = iterator.next();
            Result argResult = nextArg.accept(this);
            var resultPair = argResult.assertSecond();
            stmts.addAll(resultPair.part1());
            var argTemp = make.IRTemp(generator.argTemp(i));
            stmts.add(make.IRMove(argTemp, resultPair.part2()));
            i++;
        }
        stmts.add(make.IRCallStmt(n.target()));

        int j = 0;
        var collectors = n.collectors().iterator();
        while (collectors.hasNext()) {
            String nextResult = collectors.next();
            if (!nextResult.equals("_")) {
                stmts.add(make.IRMove(make.IRTemp(generator.newTemp()),
                        make.IRTemp(generator.retTemp(j))));
            }
            j++;
        }
        return Result.stmts(stmts);
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
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        IRCompUnit compUnit = make.IRCompUnit(n.name());
        var functions = n.functions().values();
        for (IRFuncDecl funcDecl : functions) {
            List<IRStmt> loweredStmts = funcDecl.body().accept(this).assertFirst();
            IRStmt loweredBody = make.IRSeq(loweredStmts);
            IRFuncDecl loweredFuncDecl = make.IRFuncDecl(funcDecl.name(), loweredBody);
            compUnit.appendFunc(loweredFuncDecl);
        }
        return Result.compUnit(compUnit);
    }

    @Override
    public Result visit(IRExp n) {
        return Result.stmts(n.expr().accept(this).assertSecond().part1());
    }

    @Override
    public Result visit(IRFuncDecl n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRStmt> stmts = new LinkedList<>();

        stmts.add(make.IRLabel(n.name()));
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
    public Result visit(IRReturn n) {
        return Result.stmts(List.of(n));
    }

    @Override
    public Result visit(IRMove n) {
        var handler = new LowerMoveVisitor(this, generator);
        return Result.stmts(n.accept(handler));
    }

    @Override
    public Result visit(IRSeq n) {
        return Result.stmts(n.stmts().stream().flatMap(s -> {
            return s.accept(this).assertFirst().stream();
        }).collect(Collectors.toList()));
    }

}
