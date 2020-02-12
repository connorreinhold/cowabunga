package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class ArrayInitDimensionNode extends ArrayInitNode {

    final ArrayInitNode child;
    final ExprNode size;

    public ArrayInitDimensionNode(ArrayInitNode child, ExprNode size) {
        this.child = child;
        this.size = size;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("[]");

        child.prettyPrint(printer);

        size.prettyPrint(printer);

        printer.endList();
    }

}
