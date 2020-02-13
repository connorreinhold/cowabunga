package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Node that represents the expr: [ExprNode left] *>> [ExprNode right] where the high multiplication operator
 * returns the highest 64 bit of the 128 bit multiplication operation
 */
public class HighMultExprNode extends BinExprNode {

    public HighMultExprNode(ExprNode left, ExprNode right) {
        super(left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("*>>");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }
}
