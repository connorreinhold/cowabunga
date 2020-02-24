package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.stmt.assign.AssignNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public final class AssignmentStmtNode extends AbstractNode implements StmtNode {

    // Assign [value] to [node]
    public final AssignNode assign;
    public final ExprNode value;

    public AssignmentStmtNode(Location location,
                              AssignNode assign, ExprNode value) {
        super(location);

        assert assign != null;
        assert value != null;

        this.assign = assign;
        this.value = value;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof AssignmentStmtNode) {
            AssignmentStmtNode oNode = (AssignmentStmtNode) o;
            return this.assign.equals(oNode.assign)
                    && this.value.equals(oNode.value);
        }
        return false;
    }


}
