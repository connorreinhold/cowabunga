package cyr7.ast.binexpr;

import cyr7.ast.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class DivExprNode extends BinExprNode {

    public DivExprNode(ExprNode left, ExprNode right) {
        super(left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("//");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }
}
