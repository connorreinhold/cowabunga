package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class SingleVarArrayDimensionNode extends SingleVarInitNode {

    final SingleVarInitNode child;
    final ExprNode size;

    public SingleVarArrayDimensionNode(SingleVarInitNode child, ExprNode size) {
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
