package cyr7.ir.lowering;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRStmt;

public class IRExprCommutableChecker {

    private final boolean commuteOptimizationEnabled;
    private final LoweringVisitor lower;

    public IRExprCommutableChecker(LoweringVisitor lower,
            boolean commuteOptimizationEnabled) {
        this.commuteOptimizationEnabled = commuteOptimizationEnabled;
        this.lower = lower;
    }

    /**
     * Checks if there exists an instruction in {@code e2} that affects
     * {@code e1}. Returns {@code true} if {@code e2} does not {@code e1}.
     */
    public boolean commutes(IRExpr e1, IRExpr e2) {
        if (!commuteOptimizationEnabled) {
            return false;
        }

        var e1Result = e1.accept(lower).assertSecond();
        var e2Result = e2.accept(lower).assertSecond();

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
        return stmts.stream().filter(s -> (s instanceof IRMove))
                .map(move -> ((IRMove) move).target())
                .collect(Collectors.toSet());
    }

    private Set<IRExpr> setOfUsedExpr(List<IRStmt> stmts) {
        var visitor = new TempsAccessedExprVisitor();
        return stmts.stream().flatMap(s -> s.accept(visitor).stream())
                .collect(Collectors.toSet());
    }

    private boolean canDetermineEquality(List<IRStmt> stmts) {
        var visitor = new EvaluatesMemExprVisitor();
        return stmts.stream().reduce(true, (u, s) -> {
            return u && s.accept(visitor).booleanValue();
        }, (u, i) -> true);
    }
}
