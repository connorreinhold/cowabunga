package cyr7.ir.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import cyr7.ir.visit.IRVisitor;
import cyr7.visitor.MyIRVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * An intermediate representation for a function call
 * CALL(e_target, e_1, ..., e_n)
 */
public class IRCall extends IRExpr_c {

    protected final IRExpr target;
    protected final List<IRExpr> args;
    public final int numOfReturnValues;

    /**
     *
     * @param target address of the code for this function call
     * @param args arguments of this function call
     */
    public IRCall(Location location, IRExpr target, IRExpr... args) {
        this(location, target, Arrays.asList(args), 10);
    }

    /**
     *
     * @param target address of the code for this function call
     * @param args arguments of this function call
     */
    public IRCall(Location location, IRExpr target, List<IRExpr> args,
            int numOfReturnValues) {
        super(location);
        this.target = target;
        this.args = args;
        this.numOfReturnValues = numOfReturnValues;
    }

    public IRExpr target() {
        return target;
    }

    public List<IRExpr> args() {
        return args;
    }

    @Override
    public String label() {
        return "CALL";
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
            return v.nodeFactory().IRCall(target, results,
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRCall irCall = (IRCall) o;
        return Objects.equals(target, irCall.target) &&
            Objects.equals(args, irCall.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, args);
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.startList();
        p.printAtom("CALL");
        target.printSExp(p);
        for (IRExpr arg : args)
            arg.printSExp(p);
        p.endList();
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        return v.visit(this);
    }

    @Override
    public String userFriendlyString() {
        String functionName = this.target.userFriendlyString();
        String arguments = String.join(", ", this.args.stream()
                .map(a -> a.userFriendlyString()).collect(Collectors.toList()));
        return functionName + "(" + arguments + ")";
    }
}
