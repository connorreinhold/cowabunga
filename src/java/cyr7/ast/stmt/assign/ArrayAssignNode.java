package cyr7.ast.stmt.assign;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Objects;

public final class ArrayAssignNode extends AbstractNode implements AssignNode {

    public final AssignNode child;
    public final ExprNode index;

    public ArrayAssignNode(Location location, AssignNode child, ExprNode index) {
        super(location);

        assert child != null;
        assert index != null;

        this.child = child;
        this.index = index;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayAssignNode that = (ArrayAssignNode) o;
        return Objects.equals(child, that.child) &&
            Objects.equals(index, that.index);
    }

}
