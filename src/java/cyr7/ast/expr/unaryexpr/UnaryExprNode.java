package cyr7.ast.expr.unaryexpr;

import java.util.List;

import cyr7.ast.Node;
import cyr7.ast.expr.AbstractExprNode;
import cyr7.ast.expr.ExprNode;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Superclass to expressions involving one child: -[ExprNode expr] or
 * ![ExprNode expr]
 */
public abstract class UnaryExprNode extends AbstractExprNode {

    public final ExprNode expr;

    UnaryExprNode(Location location, ExprNode expr) {
        super(location);

        assert expr != null;

        this.expr = expr;
    }

    @Override
    public List<Node> getChildren() {
        return List.of(expr);
    }

    public boolean equals(Object o) {
        if (o instanceof UnaryExprNode) {
            UnaryExprNode oNode = (UnaryExprNode) o;
            return this.expr.equals(oNode.expr);
        }
        return false;
    }

}
