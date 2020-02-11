package cyr7.ast.binexpr;

import cyr7.ast.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class LTEExprNode extends BinExprNode {

    public LTEExprNode(ExprNode left, ExprNode right) {
        super(left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("<=");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }
}