package cyr7.ir.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import cyr7.ir.visit.IRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * An intermediate representation for a sequence of statements
 * SEQ(s1,...,sn)
 */
public class IRSeq extends IRStmt {
    private List<IRStmt> stmts;

    /**
     * @param stmts the statements
     */
    public IRSeq(Location location, IRStmt... stmts) {
        this(location, Arrays.asList(stmts));
    }

    /**
     * Create a SEQ from a list of statements.
     * The list should not be modified subsequently.
     * @param stmts the sequence of statements
     */
    public IRSeq(Location location, List<IRStmt> stmts) {
        super(location);
        this.stmts = stmts;
    }

    public List<IRStmt> stmts() {
        return stmts;
    }

    @Override
    public String label() {
        return "SEQ";
    }

    @Override
    public IRNode visitChildren(IRVisitor v) {
        boolean modified = false;

        List<IRStmt> results = new ArrayList<>(stmts.size());
        for (IRStmt stmt : stmts) {
            IRStmt newStmt = (IRStmt) v.visit(this, stmt);
            if (newStmt != stmt) modified = true;
            results.add(newStmt);
        }

        if (modified) return v.nodeFactory().IRSeq(results);

        return this;
    }

    @Override
    public <T> T aggregateChildren(AggregateVisitor<T> v) {
        T result = v.unit();
        for (IRStmt stmt : stmts)
            result = v.bind(result, v.visit(stmt));
        return result;
    }

    @Override
    public CheckCanonicalIRVisitor checkCanonicalEnter(
            CheckCanonicalIRVisitor v) {
        return v.enterSeq();
    }

    @Override
    public boolean isCanonical(CheckCanonicalIRVisitor v) {
        return !v.inSeq();
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.startUnifiedList();
        p.printAtom("SEQ");
        for (IRStmt stmt : stmts)
            stmt.printSExp(p);
        p.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRSeq irSeq = (IRSeq) o;
        return Objects.equals(stmts, irSeq.stmts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stmts);
    }
}
