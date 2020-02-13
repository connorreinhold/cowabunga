package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
/**
 * Represents an integer literal, Ex: [contents] = "20"
 */
public class LiteralIntExprNode extends ExprNode {

    final String contents;

    public LiteralIntExprNode(String contents) {
        this.contents = contents;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(contents);
    }
}
