package cyr7.ir.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cyr7.ir.block.util.LabelsInJumpStmtsVisitor;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRStmt;

public class BasicBlock {

    @Override
    public String toString() {
        return this.stmts.toString();
    }

    public final List<IRStmt> stmts;

    public final Optional<IRLabel> first;
    private IRStmt last;

    public BasicBlock(List<IRStmt> stmts) {
        this.stmts = new ArrayList<>(stmts);

        IRStmt first = this.stmts.get(0);
        if (first instanceof IRLabel) {
            this.first = Optional.of((IRLabel) first);
        } else {
            this.first = Optional.empty();
        }

        this.last = this.stmts.get(this.stmts.size() - 1);
    }

    public boolean hasStartLabel() {
        return this.first.isPresent();
    }

    public IRStmt last() {
        return this.last;
    }

    /**
     * Returns a list of labels that can be reached from the end of this block,
     * such as via a jump statement or conditional jump.
     *
     */
    public List<String> getJumpLabels() {
        return this.last.accept(LabelsInJumpStmtsVisitor.instance);
    }

    public void replaceLastStmt(List<IRStmt> replacement) {
        this.stmts.remove(this.stmts.size() - 1);
        this.stmts.addAll(replacement);
        this.last = this.stmts.get(this.stmts.size() - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BasicBlock)) {
            return false;
        }
        BasicBlock other = (BasicBlock) obj;
        return Objects.equals(first, other.first)
                && Objects.equals(last, other.last)
                && Objects.equals(stmts, other.stmts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last, stmts);
    }
}
