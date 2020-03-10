package cyr7.ast.expr.unaryexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.semantics.types.ExpandedType;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public final class LengthExprNode extends UnaryExprNode {

    public LengthExprNode(Location location, ExprNode expr) {
        super(location, expr);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
