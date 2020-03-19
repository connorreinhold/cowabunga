package cyr7.ast.expr.unaryexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Represents the logical negation expression: ![ExprNode expr]
 */
public final class BoolNegExprNode extends UnaryExprNode {

    public BoolNegExprNode(Location location, ExprNode expr) {
        super(location, expr);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof BoolNegExprNode) {
            return super.equals(o);
        }
        return false;
    }

}