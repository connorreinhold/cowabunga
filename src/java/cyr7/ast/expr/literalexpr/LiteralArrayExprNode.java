package cyr7.ast.expr.literalexpr;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.List;

/**
 * Represents an Array Expression: i.e. {1,2,3,} with a list of ExprNode for
 * values
 */
public final class LiteralArrayExprNode extends AbstractNode implements ExprNode {

    public final List<ExprNode> arrayVals;

    public LiteralArrayExprNode(Location location, List<ExprNode> arrayVals) {
        super(location);

        assert arrayVals != null;

        this.arrayVals = Util.immutableCopy(arrayVals);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LiteralArrayExprNode) {
            LiteralArrayExprNode oNode = (LiteralArrayExprNode) o;
            return this.arrayVals.equals(oNode.arrayVals);
        }
        return false;
    }

}
