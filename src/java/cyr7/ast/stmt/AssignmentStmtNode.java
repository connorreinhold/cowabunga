package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * A statement of the form: lhs = rhs,
 * where lhs is an expression that is one of the following:
 * <ul>
 * <li> expr[expr]
 * <li> id
 * </ul>
 * 
 * @author ayang
 *
 */
public final class AssignmentStmtNode extends AbstractNode implements StmtNode {

    // Assign [value] to [node]
    public final ExprNode lhs;
    public final ExprNode rhs;

    /**
     * 
     * @param location The location of the assignment statement.
     * @param assign The LHS of the assignment. Must be an array access
     * to of some expression or a variable. The {@code assign} cannot be an
     * expression that begins with an open-parentheses or open-braces.
     * @param value The RHS of the assignment.
     */
    public AssignmentStmtNode(Location location,
                              ExprNode assign, ExprNode value) {
        super(location);

        assert assign != null;
        assert value != null;

        this.lhs = assign;
        this.rhs = value;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof AssignmentStmtNode) {
            AssignmentStmtNode oNode = (AssignmentStmtNode) o;
            return this.lhs.equals(oNode.lhs)
                    && this.rhs.equals(oNode.rhs);
        }
        return false;
    }


}
