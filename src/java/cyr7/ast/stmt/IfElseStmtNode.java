package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Optional;

/**
 * Represents an if/else statement. Contains [ExprNode guard], a body [ifBlock],
 * and an optional [elseBlock]
 */
public final class IfElseStmtNode extends AbstractNode implements StmtNode {

    public final ExprNode guard;
    public final StmtNode ifBlock;
    public final Optional<StmtNode> elseBlock;

    public IfElseStmtNode(Location location, ExprNode guard, StmtNode ifBlock,
                          Optional<StmtNode> elseBlock) {
        super(location);

        assert guard != null;
        assert ifBlock != null;
        assert elseBlock != null;

        this.guard = guard;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof IfElseStmtNode) {
            IfElseStmtNode oNode = (IfElseStmtNode) o;
            return this.guard.equals(oNode.guard)
                && this.ifBlock.equals(oNode.ifBlock)
                && this.elseBlock.equals(oNode.elseBlock);
        }
        return false;
    }

}
