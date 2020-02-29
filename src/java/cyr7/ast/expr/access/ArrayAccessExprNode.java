package cyr7.ast.expr.access;

import java.util.Objects;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * An expression of the form {@code a[0] }, {@code f()[0] }, or {@code b[i][j] }
 */
public final class ArrayAccessExprNode extends AbstractNode implements ExprNode {

    public final ExprNode child;
    public final ExprNode index;

    public ArrayAccessExprNode(Location location, ExprNode child,
            ExprNode index) {
        super(location);
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
        ArrayAccessExprNode that = (ArrayAccessExprNode) o;
        return Objects.equals(child, that.child) &&
                Objects.equals(index, that.index);
    }

}