package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class LiteralCharExprNode extends ExprNode {

    final String contents;

    public LiteralCharExprNode(String contents) {
        this.contents = contents;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom("'" + Util.unescapeString(contents) + "'");
    }
}
