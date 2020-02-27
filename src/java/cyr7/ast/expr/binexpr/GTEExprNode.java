package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Node that represents the expression: [ExprNode left] >= [ExprNode right]
 */
public final class GTEExprNode extends BinExprNode {

    public GTEExprNode(Location location, ExprNode left, ExprNode right) {
        super(location, left, right);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GTEExprNode) {
            GTEExprNode oNode = (GTEExprNode) o;
            return this.left.equals(oNode.left)
                && this.right.equals(oNode.right);
        }
        return false;
    }

}