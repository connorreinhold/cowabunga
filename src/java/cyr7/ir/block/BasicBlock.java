package cyr7.ir.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cyr7.ir.block.util.LabelsInJumpStmtsVisitor;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRStmt;
import cyr7.util.Util;

import javax.swing.text.html.Option;

final class BasicBlock {

    public final List<IRStmt> stmts;

    /**
     * @param stmts Must have at least one statement.
     */
    public BasicBlock(List<IRStmt> stmts) {
        assert !stmts.isEmpty();
        assert stmts.get(0) instanceof IRLabel;
        this.stmts = Util.immutableCopy(stmts);
    }

    public Optional<IRLabel> first() {
        IRStmt first = stmts.get(0);
        if (first instanceof IRLabel) {
            return Optional.of((IRLabel) first);
        } else {
            return Optional.empty();
        }
    }

    public IRStmt last() {
        return this.stmts.get(this.stmts.size() - 1);
    }

    /**
     * Returns a list of labels that can be reached from the end of this block,
     * such as via a jump statement or conditional jump.
     */
    public List<String> getJumpLabels() {
        return last().accept(LabelsInJumpStmtsVisitor.instance);
    }

    /**
     * Used to replace redundant statement sequences such as JUMP(l1);
     * LABEL(l1);
     *
     * Updates first and last fields.
     *
     * @param replacement
     */
    public BasicBlock replacingLastStmtWith(List<IRStmt> replacement) {
        List<IRStmt> modifiedStmts = new ArrayList<>(stmts);
        modifiedStmts.remove(modifiedStmts.size() - 1);
        modifiedStmts.addAll(replacement);
        return new BasicBlock(modifiedStmts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicBlock that = (BasicBlock) o;
        return Objects.equals(stmts, that.stmts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stmts);
    }

    @Override
    public String toString() {
        return this.stmts.toString();
    }

}
