package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ExpandedType;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Node that represents the expr: [ExprNode left] *>> [ExprNode right] where the
 * high multiplication operator returns the highest 64 bit of the 128 bit
 * multiplication operation
 */
public final class HighMultExprNode extends BinExprNode {

    public HighMultExprNode(ComplexSymbolFactory.Location location,
            ExprNode left, ExprNode right) {
        super(location, left, right);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HighMultExprNode) {
            HighMultExprNode oNode = (HighMultExprNode) o;
            return this.left.equals(oNode.left)
                    && this.right.equals(oNode.right);
        }
        return false;
    }

}
