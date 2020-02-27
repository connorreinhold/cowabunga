package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Node that represents taking [ExprNode] and dividing (integer division) by
 * [ExprNode right]
 */
public final class DivExprNode extends BinExprNode {

    public DivExprNode(Location location, ExprNode left,
                       ExprNode right) {
        super(location, left, right);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DivExprNode) {
            DivExprNode oNode = (DivExprNode) o;
            return this.left.equals(oNode.left)
                && this.right.equals(oNode.right);
        }
        return false;
    }

}
