package cyr7.ir.nodes;

import java.util.Objects;

import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import cyr7.ir.visit.IRVisitor;
import cyr7.visitor.MyIRVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * An intermediate representation for a conditional transfer of control
 * CJUMP(expr, trueLabel, falseLabel)
 */
public class IRCJump extends IRStmt {
    private IRExpr cond;
    private String trueLabel;
    private String falseLabel;

    /**
     *
     * @param cond the condition for the jump
     * @param trueLabel the destination of the jump if {@code expr} evaluates
     *          to true
     * @param falseLabel the destination of the jump if {@code expr} evaluates
     *          to false
     */
    public IRCJump(Location location, IRExpr cond, String trueLabel, String falseLabel) {
        super(location);
        this.cond = cond;
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
    }

    public IRExpr cond() {
        return cond;
    }

    public String trueLabel() {
        return trueLabel;
    }

    public String falseLabel() {
        return falseLabel;
    }


    @Override
    public String label() {
        return "CJUMP";
    }

    @Override
    public IRNode visitChildren(IRVisitor v) {
        IRExpr expr = (IRExpr) v.visit(this, this.cond);

        if (expr != this.cond)
            return v.nodeFactory().IRCJump(expr, trueLabel, falseLabel);

        return this;
    }

    public boolean hasFalseLabel() {
        return falseLabel != null;
    }

    @Override
    public <T> T aggregateChildren(AggregateVisitor<T> v) {
        T result = v.unit();
        result = v.bind(result, v.visit(cond));
        return result;
    }

    @Override
    public boolean isCanonical(CheckCanonicalIRVisitor v) {
        return !hasFalseLabel();
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.startList();
        p.printAtom("CJUMP");
        cond.printSExp(p);
        p.printAtom(trueLabel);
        if (hasFalseLabel())
            p.printAtom(falseLabel);
        p.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRCJump ircJump = (IRCJump) o;
        return Objects.equals(cond, ircJump.cond) &&
            Objects.equals(trueLabel, ircJump.trueLabel) &&
            Objects.equals(falseLabel, ircJump.falseLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cond, trueLabel, falseLabel);
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        return v.visit(this);
    }
}
