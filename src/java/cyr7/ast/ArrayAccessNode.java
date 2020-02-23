package cyr7.ast;

import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Objects;

/**
 * Example Expression: arr[1][4][2]
 * @author ayang
 *
 */
public final class ArrayAccessNode extends AbstractNode implements AccessNode {

    public final AccessNode child;
    public final ExprNode index;

    public ArrayAccessNode(Location location, AccessNode child,
                                 ExprNode index) {
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
        ArrayAccessNode that = (ArrayAccessNode) o;
        return Objects.equals(child, that.child) &&
            Objects.equals(index, that.index);
    }

}
