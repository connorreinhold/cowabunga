package cyr7.ast;

import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Objects;

public final class ArrayAccessNode extends AbstractNode implements AccessNode {

    public final AccessNode node;
    public final ExprNode index;

    public ArrayAccessNode(ComplexSymbolFactory.Location location, AccessNode node,
                                 ExprNode index) {
        super(location);

        assert node != null;
        assert index != null;

        this.node = node;
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
        return Objects.equals(node, that.node) &&
            Objects.equals(index, that.index);
    }

}
