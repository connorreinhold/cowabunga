package cyr7.ast;

import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class LiteralStringExprNode extends ExprNode {

    final String contents;

    public LiteralStringExprNode(String contents) {
        this.contents = contents;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom("\"" + Util.unescapeString(contents) + "\"");
    }

}
