package cyr7.ast.unaryexpr;

import cyr7.ast.ExprNode;

public abstract class UnaryExprNode extends ExprNode {

    final ExprNode expr;

    public UnaryExprNode(ExprNode expr) {
        this.expr = expr;
    }

}
