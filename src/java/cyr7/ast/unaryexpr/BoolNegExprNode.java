package cyr7.ast.unaryexpr;

import cyr7.ast.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class BoolNegExprNode extends UnaryExprNode {

    public BoolNegExprNode(ExprNode expr) {
        super(expr);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("!");

        expr.prettyPrint(printer);

        printer.endList();
    }

}
