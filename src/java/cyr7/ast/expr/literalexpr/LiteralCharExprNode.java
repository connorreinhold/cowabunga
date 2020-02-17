package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.Type;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a character literal, Ex: [contents] ='r'
 */
public class LiteralCharExprNode extends ExprNode {

    final String contents;

    public LiteralCharExprNode(ComplexSymbolFactory.Location location, String contents) {
        super(location);

        assert contents != null;

        this.contents = contents;
    }
    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom("'" + Util.unescapeString(contents) + "'");
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof LiteralCharExprNode) {
            LiteralCharExprNode oNode = (LiteralCharExprNode) o;
            return this.contents.equals(oNode.contents);
        }
        return false;    
    }

    @Override
    public Type typeCheck(Context c) {
        return PrimitiveType.INT;
    }
}
