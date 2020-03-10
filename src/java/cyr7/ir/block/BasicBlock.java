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

    /**
     * An arbitrary instance of a basic block with no statements.
     */
    public static BasicBlock EMPTY = new BasicBlock();

    public final List<IRStmt> stmts;
    public Optional<IRLabel> first;
    private Optional<IRStmt> last;

    public BasicBlock(List<IRStmt> stmts) {
        this.stmts = new ArrayList<>(stmts);

        IRStmt first = this.stmts.get(0);
        if (first instanceof IRLabel) {
            this.first = Optional.of((IRLabel) first);
        } else {
            this.first = Optional.empty();
        }

        this.last = Optional.of(this.stmts.get(this.stmts.size() - 1));
    }

    private BasicBlock() {
        this.first = Optional.empty();
        this.stmts = List.of();
    }

    public boolean hasStartLabel() {
        return this.first.isPresent();
    }

    public IRStmt last() {
        if (this.last.isPresent())
            return this.last.get();
        else
            throw new UnsupportedOperationException();
    }

    /**
     * Returns a list of labels that can be reached from the end of this block,
     * such as via a jump statement or conditional jump.
     *
     */
    public List<String> getJumpLabels() {
        if (this.last.isPresent())
            return this.last.get().accept(LabelsInJumpStmtsVisitor.instance);
        else
            throw new UnsupportedOperationException();
    }

    public void replaceLastStmt(List<IRStmt> replacement) {
        this.stmts.remove(this.stmts.size() - 1);

        this.stmts.addAll(replacement);
        if (!this.stmts.isEmpty()) {
            this.last = Optional.of(this.stmts.get(this.stmts.size() - 1));
            IRStmt first = this.stmts.get(0);
            if (first instanceof IRLabel) {
                this.first = Optional.of((IRLabel) first);
            } else {
                this.first = Optional.empty();
            }
        }
        else {
            this.last = Optional.empty();
            this.first = Optional.empty();
        }

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
