package cyr7.ast.expr.unaryexpr;

import java.util.Optional;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Superclass to expressions involving one child: -[ExprNode expr] or ![ExprNode
 * expr]
 */
public abstract class UnaryExprNode extends ExprNode {
    final ExprNode expr;

    public UnaryExprNode(ComplexSymbolFactory.Location location,
            ExprNode expr) {
        super(location);
        this.expr = expr;
    }

    public UnaryExprNode(ComplexSymbolFactory.Location location, ExprNode expr,
            Optional<Boolean> safety) {
        super(location, safety);
        this.expr = expr;
    }

    public boolean equals(Object o) {
        if (o instanceof UnaryExprNode) {
            UnaryExprNode oNode = (UnaryExprNode) o;
            return this.expr.equals(oNode.expr);
        }
        return false;
    }

}
