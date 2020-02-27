package cyr7.ast.expr.binexpr;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Node that represents executing some binary operator on [ExprNode left] and
 * [ExprNode right]
 */
public abstract class BinExprNode extends AbstractNode implements ExprNode {

    public final ExprNode left, right;

    BinExprNode(Location location, ExprNode left,
            ExprNode right) {
        super(location);

        assert left != null;
        assert right != null;

        this.left = left;
        this.right = right;
    }
}
