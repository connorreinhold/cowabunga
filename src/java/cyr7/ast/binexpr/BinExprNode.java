package cyr7.ast.binexpr;

import cyr7.ast.ExprNode;

public abstract class BinExprNode extends ExprNode {

    final ExprNode left, right;

    public BinExprNode(ExprNode left, ExprNode right) {
        this.left = left;
        this.right = right;
    }
}
