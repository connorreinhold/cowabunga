package cyr7.ast.expr.literalexpr;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Represents a character literal, Ex: [contents] ='r'
 */
public final class LiteralCharExprNode extends AbstractNode 
                                                implements ExprNode {

    public final String contents;

    public LiteralCharExprNode(Location location, String contents) {
        super(location);
        assert contents != null;
        this.contents = contents;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LiteralCharExprNode) {
            LiteralCharExprNode oNode = (LiteralCharExprNode) o;
            return this.contents.equals(oNode.contents);
        }
        return false;
    }

}
