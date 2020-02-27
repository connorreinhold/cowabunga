package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Represents a while statement, with [ExprNode guard] and a [block]
 * representing the body of the while
 * loop
 */
public final class WhileStmtNode extends AbstractNode implements StmtNode {

    public final ExprNode guard;
    public final StmtNode block;

    public WhileStmtNode(Location location, ExprNode guard, StmtNode block) {
        super(location);

        assert guard != null;
        assert block != null;
        this.guard = guard;
        this.block = block;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof WhileStmtNode) {
            WhileStmtNode oNode = (WhileStmtNode) o;
            return this.guard.equals(oNode.guard)
                && this.block.equals(oNode.block);
        }
        return false;
    }

}
