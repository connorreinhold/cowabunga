package cyr7.ast.expr.unaryexpr;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Superclass to expressions involving one child: -[ExprNode expr] or ![ExprNode expr]
 */
public abstract class UnaryExprNode extends AbstractNode implements ExprNode {

    public final ExprNode expr;

    public UnaryExprNode(ComplexSymbolFactory.Location location, ExprNode expr) {
        super(location);

        assert expr != null;

        this.expr = expr;
    }
    
    public boolean equals(Object o) {
        if (o instanceof UnaryExprNode) {
            UnaryExprNode oNode = (UnaryExprNode) o;
            return this.expr.equals(oNode.expr);
        }
        return false;
    }

}
