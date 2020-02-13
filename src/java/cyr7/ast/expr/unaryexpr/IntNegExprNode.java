package cyr7.ast.expr.unaryexpr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents the integer negation expression: -[ExprNode expr]
 */
public class IntNegExprNode extends UnaryExprNode {

    public IntNegExprNode(ComplexSymbolFactory.Location location, ExprNode expr) {
        super(location, expr);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("-");

        expr.prettyPrint(printer);

        printer.endList();
    }
}
