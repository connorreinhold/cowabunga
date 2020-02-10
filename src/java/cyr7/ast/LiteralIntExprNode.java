package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

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
