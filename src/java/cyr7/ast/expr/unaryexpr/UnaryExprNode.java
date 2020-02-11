package cyr7.ast.expr.unaryexpr;

import cyr7.ast.expr.ExprNode;

public abstract class UnaryExprNode extends ExprNode {
    final ExprNode expr;

    public UnaryExprNode(ExprNode expr) {
        this.expr = expr;
    }

}
