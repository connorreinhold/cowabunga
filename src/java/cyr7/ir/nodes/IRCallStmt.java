package cyr7.ir.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import cyr7.ir.visit.IRVisitor;

/**
 * An intermediate representation for a call statement.
 * t_1, t_2, _, t_4 = CALL(e_target, e_1, ..., e_n)
 */
public class IRCallStmt extends IRStmt {
    protected List<String> collectors;
    protected IRExpr target;
    protected List<IRExpr> args;

    /**
     * @param collectors a list of temporary names to assign to.
     * @param target address of the code for this function call
     * @param args arguments of this function call
     */
    public IRCallStmt(List<String> collectors, IRExpr target, IRExpr... args) {
        this(collectors, target, Arrays.asList(args));
    }

    /**
     * @param collectors a list of temporary names to assign to.
     * @param target address of the code for this function call
     * @param args arguments of this function call
     */
    public IRCallStmt(List<String> collectors, IRExpr target, List<IRExpr> args) {
        this.collectors = collectors;
        this.target = target;
        this.args = args;
    }

    public List<String> collectors() {
        return collectors;
    }

    public IRExpr target() {
        return target;
    }

    public List<IRExpr> args() {
        return args;
    }

    @Override
    public String label() {
        return "CALL_STMT";
    }

    @Override
    public IRNode visitChildren(IRVisitor v) {
        boolean modified = false;

        IRExpr target = (IRExpr) v.visit(this, this.target);
        if (target != this.target) modified = true;

        List<IRExpr> results = new ArrayList<>(args.size());
        for (IRExpr arg : args) {
            IRExpr newExpr = (IRExpr) v.visit(this, arg);
            if (newExpr != arg) modified = true;
            results.add(newExpr);
        }

        if (modified) return v.nodeFactory().IRCallStmt(collectors, target, results);

        return this;
    }

    @Override
    public <T> T aggregateChildren(AggregateVisitor<T> v) {
        T result = v.unit();
        result = v.bind(result, v.visit(target));
        for (IRExpr arg : args)
            result = v.bind(result, v.visit(arg));
        return result;
    }

    @Override
    public boolean isCanonical(CheckCanonicalIRVisitor v) {
        return !v.inExpr();
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.startList();
        p.printAtom("CALL_STMT");
        for (String collector : collectors) {
            p.printAtom(collector);
        }
        target.printSExp(p);
        for (IRExpr arg : args)
            arg.printSExp(p);
        p.endList();
    }
}