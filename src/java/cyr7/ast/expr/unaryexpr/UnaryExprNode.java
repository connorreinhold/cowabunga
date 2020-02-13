package cyr7.ast.expr.unaryexpr;

import cyr7.ast.expr.ExprNode;

/**
 * Superclass to expressions involving one child: -[ExprNode expr] or ![ExprNode expr]
 */
public abstract class UnaryExprNode extends ExprNode {
    final ExprNode expr;

    public UnaryExprNode(ExprNode expr) {
        this.expr = expr;
    }

}
