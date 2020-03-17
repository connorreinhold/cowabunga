package cyr7.ast.expr.literalexpr;

import java.util.List;

import cyr7.ast.Node;
import cyr7.ast.expr.AbstractExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Represents a character literal, Ex: [contents] ='r'
 */
public final class LiteralCharExprNode extends AbstractExprNode {

    public final String contents;

    public LiteralCharExprNode(Location location, String contents) {
        super(location);
        assert contents != null;
        this.contents = contents;
    }

    @Override
    public List<Node> getChildren() {
        return List.of();
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
