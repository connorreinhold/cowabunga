package cyr7.ir.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cyr7.ir.nodes.IRStmt;

public class BasicBlock {

    private final List<IRStmt> stmts;

    public BasicBlock(List<IRStmt> stmts) {
        this.stmts = Collections.unmodifiableList(new ArrayList<>(stmts));
    }

    public List<IRStmt> stmts() {
        return this.stmts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stmts);
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


}
