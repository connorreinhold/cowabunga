package cyr7.ast.expr.unaryexpr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Represents the integer negation expression: -[ExprNode expr]
 */
public class IntNegExprNode extends UnaryExprNode {

    public IntNegExprNode(ExprNode expr) {
        super(expr);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("-");

        expr.prettyPrint(printer);

        printer.endList();
    }
}
