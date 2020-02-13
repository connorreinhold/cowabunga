package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a String literal, Ex: [contents] = "hello"
 */
public class LiteralStringExprNode extends ExprNode {

    final String contents;

    public LiteralStringExprNode(ComplexSymbolFactory.Location location, String contents) {
        super(location);
        this.contents = contents;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom("\"" + Util.unescapeString(contents) + "\"");
    }

}
