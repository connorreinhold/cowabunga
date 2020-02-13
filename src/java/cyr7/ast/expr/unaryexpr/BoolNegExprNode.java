package cyr7.ast.expr.unaryexpr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents the logical negation expression: ![ExprNode expr]
 */
public class BoolNegExprNode extends UnaryExprNode {

    public BoolNegExprNode(ComplexSymbolFactory.Location location, ExprNode expr) {
        super(location, expr);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("!");

        expr.prettyPrint(printer);

        printer.endList();
    }

}
