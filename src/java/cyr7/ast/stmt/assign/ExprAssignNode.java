package cyr7.ast.stmt.assign;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Objects;

public final class ExprAssignNode extends AbstractNode implements AssignNode {

    public final ExprNode expr;

    public ExprAssignNode(Location location, ExprNode expr) {
        super(location);

        assert expr != null;

        this.expr = expr;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExprAssignNode that = (ExprAssignNode) o;
        return Objects.equals(expr, that.expr);
    }

}
