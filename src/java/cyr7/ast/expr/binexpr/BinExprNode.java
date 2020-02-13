package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;

/**
 * Node that represents executing some binary operator on [ExprNode left] and [ExprNode right]
 */
public abstract class BinExprNode extends ExprNode {

    final ExprNode left, right;

    public BinExprNode(ExprNode left, ExprNode right) {
        this.left = left;
        this.right = right;
    }
}
