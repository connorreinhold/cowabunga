package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.ParserIntegerOverflowException;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Node that represents the expression [ExprNode left] % [ExprNode right], or
 * the remainder when [ExprNode left] is divided by [ExprNode right]
 */
public class RemExprNode extends BinExprNode {

    public RemExprNode(ComplexSymbolFactory.Location location, ExprNode left,
            ExprNode right) throws ParserIntegerOverflowException {
        super(location, left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("%");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RemExprNode) {
            RemExprNode oNode = (RemExprNode) o;
            return this.left.equals(oNode.left) && this.right.equals(
                    oNode.right);
        }
        return false;
    }

}
