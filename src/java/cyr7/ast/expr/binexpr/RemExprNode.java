package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class RemExprNode extends BinExprNode {

    public RemExprNode(ComplexSymbolFactory.Location location, ExprNode left, ExprNode right) {
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

}
