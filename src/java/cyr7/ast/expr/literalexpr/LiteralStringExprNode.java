package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.Type;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a String literal, Ex: [contents] = "hello"
 */
public class LiteralStringExprNode extends ExprNode {

    public final String contents;

    public LiteralStringExprNode(ComplexSymbolFactory.Location location, String contents) {
        super(location);

        assert contents != null;

        this.contents = contents;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom("\"" + Util.unescapeString(contents) + "\"");
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
    public Type typeCheck(Context c) {
        return new ArrayType(PrimitiveType.INT);
    }

}
