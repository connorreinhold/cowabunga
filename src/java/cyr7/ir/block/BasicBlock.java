package cyr7.ir.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRStmt;

public class BasicBlock {

    private final List<IRStmt> stmts;
    private final Optional<String> startLabel;
    private boolean marked;

    public BasicBlock(List<IRStmt> stmts) {
        this.stmts = Collections.unmodifiableList(new ArrayList<>(stmts));
        IRStmt first = this.stmts.get(0);
        if (first instanceof IRLabel) {
            this.startLabel = Optional.of(((IRLabel) first).name());
        } else {
            this.startLabel = Optional.empty();
        }
        this.marked = false;
    }

    public boolean hasStartLabel() {
        return this.startLabel.isPresent();
    }

    public List<IRStmt> stmts() {
        return this.stmts;
    }

    public void mark() {
        this.marked = true;
    }

    public void unmark() {
        this.marked = false;
    }

    public boolean isMarked() {
        return this.marked;
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
        return Objects.equals(stmts, other.stmts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stmts);
    }

}
