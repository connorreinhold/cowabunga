package cyr7.ir.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import cyr7.ir.visit.IRVisitor;
import cyr7.visitor.MyIRVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * An intermediate representation for a call statement.
 * t_1, t_2, _, t_4 = CALL(e_target, e_1, ..., e_n)
 */
public class IRCallStmt extends IRStmt {
    protected List<String> collectors;
    protected IRExpr target;
    protected List<IRExpr> args;
    public final int numOfReturnValues;

    /**
     * @param collectors a list of temporary names to assign to.
     * @param target address of the code for this function call
     * @param args arguments of this function call
     */
    public IRCallStmt(Location location, List<String> collectors, IRExpr target, IRExpr... args) {
        this(location, collectors, target, Arrays.asList(args), 10);
    }

    /**
     * @param collectors a list of temporary names to assign to.
     * @param target address of the code for this function call
     * @param args arguments of this function call
     */
    public IRCallStmt(Location location, List<String> collectors, IRExpr target,
            List<IRExpr> args, int numOfReturnValues) {
        super(location);
        this.collectors = collectors;
        this.target = target;
        this.args = args;
        this.numOfReturnValues = numOfReturnValues;
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

        if (modified)
            return v.nodeFactory().IRCallStmt(collectors, target, results,
                    this.numOfReturnValues);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRCallStmt that = (IRCallStmt) o;
        return Objects.equals(collectors, that.collectors) &&
            Objects.equals(target, that.target) &&
            Objects.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectors, target, args);
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        return v.visit(this);
    }
}
