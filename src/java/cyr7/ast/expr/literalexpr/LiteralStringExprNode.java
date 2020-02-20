package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ExpandedType;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a String literal, Ex: [contents] = "hello"
 */
public final class LiteralStringExprNode extends ExprNode {

    public final String contents;

    public LiteralStringExprNode(ComplexSymbolFactory.Location location, String contents) {
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
        if (o instanceof LiteralStringExprNode) {
            LiteralStringExprNode oNode = (LiteralStringExprNode) o;
            return this.contents.equals(oNode.contents);
        }
        return false;    
    }

    @Override
    public ExpandedType typeCheck(Context c) {
        return new ArrayType(PrimitiveType.INT);
    }

}
