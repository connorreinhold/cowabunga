package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.AccessNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public final class AssignmentStmtNode extends AbstractNode implements StmtNode {

    // Assign [value] to [node]
    public final AccessNode access;
    public final ExprNode value;

    public AssignmentStmtNode(Location location,
                              AccessNode access, ExprNode value) {
        super(location);

        assert access != null;
        assert value != null;

        this.access = access;
        this.value = value;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof AssignmentStmtNode) {
            AssignmentStmtNode oNode = (AssignmentStmtNode) o;
            return this.access.equals(oNode.access)
                    && this.value.equals(oNode.value);
        }
        return false;
    }


}
