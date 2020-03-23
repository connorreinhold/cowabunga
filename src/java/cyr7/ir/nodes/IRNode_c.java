package cyr7.ir.nodes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import cyr7.ir.visit.CheckConstFoldedIRVisitor;
import cyr7.ir.visit.IRVisitor;
import cyr7.ir.visit.InsnMapsBuilder;
import cyr7.x86.TilerData;
import cyr7.x86.asm.ASMInstr;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * A node in an intermediate-representation abstract syntax tree.
 */
public abstract class IRNode_c implements IRNode {

    private final Location location;

    private Optional<TilerData> optimalTiling;

    public IRNode_c(Location location) {
        assert location != null;
        this.location = location;
        this.optimalTiling = Optional.empty();
    }

    private void setOptimalTilingOnce(TilerData tilerData) {
        if (optimalTiling.isPresent()) {
            throw new UnsupportedOperationException();
        }

        optimalTiling = Optional.of(tilerData);
    }

    @Override
    public IRNode visitChildren(IRVisitor v) {
        return this;
    }

    @Override
    public <T> T aggregateChildren(AggregateVisitor<T> v) {
        return v.unit();
    }

    @Override
    public InsnMapsBuilder buildInsnMapsEnter(InsnMapsBuilder v) {
        return v;
    }

    @Override
    public IRNode buildInsnMaps(InsnMapsBuilder v) {
        v.addInsn(this);
        return this;
    }

    @Override
    public CheckCanonicalIRVisitor checkCanonicalEnter(
            CheckCanonicalIRVisitor v) {
        return v;
    }

    @Override
    public boolean isCanonical(CheckCanonicalIRVisitor v) {
        return true;
    }

    @Override
    public boolean isConstFolded(CheckConstFoldedIRVisitor v) {
        return true;
    }

    @Override
    public abstract String label();

    @Override
    public abstract void printSExp(SExpPrinter p);

    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw);
             SExpPrinter sp = new CodeWriterSExpPrinter(pw)) {
            printSExp(sp);
        }
        return sw.toString();
    }

    @Override
    public Location location() {
        return this.location;
    }

    @Override
    public abstract int hashCode();
}
