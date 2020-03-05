package cyr7.ir.nodes;

import java.util.Objects;

import cyr7.ir.fold.MyIRVisitor;
import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.IRVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * An intermediate representation for a move statement
 * MOVE(target, expr)
 */
public class IRMove extends IRStmt {
    private IRExpr target;
    private IRExpr src;

    /**
     *
     * @param target the destination of this move
     * @param src the expression whose value is to be moved
     */
    public IRMove(Location location, IRExpr target, IRExpr src) {
        super(location);
        this.target = target;
        this.src = src;
    }

    public IRExpr target() {
        return target;
    }

    public IRExpr source() {
        return src;
    }

    @Override
    public String label() {
        return "MOVE";
    }

    @Override
    public IRNode visitChildren(IRVisitor v) {
        IRExpr target = (IRExpr) v.visit(this, this.target);
        IRExpr expr = (IRExpr) v.visit(this, src);

        if (target != this.target || expr != src)
            return v.nodeFactory().IRMove(target, expr);

        return this;
    }

    @Override
    public <T> T aggregateChildren(AggregateVisitor<T> v) {
        T result = v.unit();
        result = v.bind(result, v.visit(target));
        result = v.bind(result, v.visit(src));
        return result;
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.startList();
        p.printAtom("MOVE");
        target.printSExp(p);
        src.printSExp(p);
        p.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRMove irMove = (IRMove) o;
        return Objects.equals(target, irMove.target) &&
            Objects.equals(src, irMove.src);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, src);
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        return v.visit(this);
    }
}
