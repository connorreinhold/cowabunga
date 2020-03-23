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
import cyr7.x86.asm.ASMInstr;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * A node in an intermediate-representation abstract syntax tree.
 */
public abstract class IRNode_c implements IRNode {

    private final Location location;

    /**
     * The cost of the optimal tile formed with {@code this} node as the root of
     * an IR tree.
     */
    private Optional<Integer> tileCost;

    /**
     * The optimal list of instructions representing the optimal tile formed
     * with {@code this} node as the root of an IR tree.
     * <p>
     * The instructions are in order from lowest index to highest index. This
     * means the first instruction of the list is the first instruction executed
     * in assembly.
     */
    private Optional<List<ASMInstr>> optimalInstructions;

    public IRNode_c(Location location) {
        assert location != null;
        this.location = location;
        this.tileCost = Optional.empty();
        this.optimalInstructions = Optional.empty();
    }

    /**
     * Assigns the cost of the optimal tile formed with {@code this} node as the
     * root of an IR tree.
     *
     */
    final public void assignCost(int cost) {
        if (this.tileCost.isPresent()) {
            throw new RuntimeException(
                    "Cost already given: " + this.tileCost.get());
        } else {
            this.tileCost = Optional.of(cost);
        }
    }

    /**
     * Returns the cost of the optimal tile formed with {@code this} node as the
     * root of an IR tree.
     *
     * <p>
     * <b>Required: </b> {@link #assignCost(int)} must had been called once.
     *
     */
    final public int cost() {
        return this.tileCost.map(c -> c)
                .orElseThrow(() -> new RuntimeException("Cost is negative"));
    }

    /**
     * Set {@code instructions} to be the optimal list of instructions
     * representing the optimal tile formed with {@code this} node as the root
     * of an IR tree.
     *
     * @param instructions The instructions are in order from lowest index to
     *                     highest index. This means the first instruction of
     *                     the list is the first instruction executed in
     *                     assembly.
     * @throws RuntimeException If a list of instructions has already been set.
     */
    final public void setAssemblyInstructions(List<ASMInstr> instructions) {
        if (this.optimalInstructions.isPresent()) {
            throw new RuntimeException(
                    "List already given: " + this.optimalInstructions.get());
        } else {
            this.optimalInstructions = Optional.of(Collections
                    .unmodifiableList(new ArrayList<>(instructions)));
        }
    }

    /**
     * Returns a list of instructions that represents the optimal tile formed
     * with {@code this} node as the root of an IR tree.
     *
     * @return The instructions are in order from lowest index to highest index.
     *         This means the first instruction of the list is the first
     *         instruction executed in assembly.
     */
    final public List<ASMInstr> optimalInstructions() {
        return this.optimalInstructions.map(opt -> opt)
                .orElseThrow(() -> new RuntimeException("Cost is negative"));
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
