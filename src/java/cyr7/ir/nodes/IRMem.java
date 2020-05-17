package cyr7.ir.nodes;

import java.util.Objects;

import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.IRVisitor;
import cyr7.visitor.MyIRVisitor;
import edu.cornell.cs.cs4120.util.InternalCompilerError;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * An intermediate representation for a memory location
 * MEM(e)
 */
public class IRMem extends IRExpr_c {

    public enum MemType {
        NORMAL, IMMUTABLE;

        @Override
        public String toString() {
            switch (this) {
            case NORMAL:
                return "MEM";
            case IMMUTABLE:
                return "MEM_I";
            }
            throw new InternalCompilerError("Unknown mem type!");
        }
    };

    private IRExpr expr;
    private MemType memType;

    /**
     *
     * @param expr the address of this memory location
     */
    public IRMem(Location location, IRExpr expr) {
        this(location, expr, MemType.NORMAL);
    }

    public IRMem(Location location, IRExpr expr, MemType memType) {
        super(location);
        this.expr = expr;
        this.memType = memType;
    }

    public IRExpr expr() {
        return expr;
    }

    public MemType memType() {
        return memType;
    }

    @Override
    public String label() {
        return memType.toString();
    }

    @Override
    public IRNode visitChildren(IRVisitor v) {
        IRExpr expr = (IRExpr) v.visit(this, this.expr);

        if (expr != this.expr) return v.nodeFactory().IRMem(expr);

        return this;
    }

    @Override
    public <T> T aggregateChildren(AggregateVisitor<T> v) {
        T result = v.unit();
        result = v.bind(result, v.visit(expr));
        return result;
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.startList();
        p.printAtom(memType.toString());
        expr.printSExp(p);
        p.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRMem irMem = (IRMem) o;
        return Objects.equals(expr, irMem.expr) &&
            memType == irMem.memType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expr, memType);
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        return v.visit(this);
    }

    @Override
    public String userFriendlyString() {
        return "[ " + this.memType + " ]";
    }

    @Override
    public IRExpr clone() {
        // TODO Auto-generated method stub
        return null;
    }
}
