package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class RemExprNode extends BinExprNode {

    public RemExprNode(ExprNode left, ExprNode right) {
        super(left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("%");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }

}
