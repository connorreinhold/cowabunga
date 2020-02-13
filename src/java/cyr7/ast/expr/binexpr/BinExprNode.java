package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class BinExprNode extends ExprNode {

    final ExprNode left, right;

    public BinExprNode(ComplexSymbolFactory.Location location, ExprNode left, ExprNode right) {
        super(location);
        this.left = left;
        this.right = right;
    }
}
