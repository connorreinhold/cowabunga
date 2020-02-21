package cyr7.ast.expr.unaryexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ExpandedType;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents the integer negation expression: -[ExprNode expr]
 */
public final class IntNegExprNode extends UnaryExprNode {

    public IntNegExprNode(ComplexSymbolFactory.Location location,
            ExprNode expr) {
        super(location, expr);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof IntNegExprNode) {
            return super.equals(o);
        }
        return false;
    }

}
