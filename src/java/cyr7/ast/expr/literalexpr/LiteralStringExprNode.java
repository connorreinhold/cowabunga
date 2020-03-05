package cyr7.ast.expr.literalexpr;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.ast.expr.AbstractExprNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.List;

/**
 * Represents a String literal, Ex: [contents] = "hello"
 */
public final class LiteralStringExprNode extends AbstractExprNode {

    public final String contents;

    public LiteralStringExprNode(Location location, String contents) {
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
        if (o instanceof LiteralStringExprNode) {
            LiteralStringExprNode oNode = (LiteralStringExprNode) o;
            return this.contents.equals(oNode.contents);
        }
        return false;
    }

}
