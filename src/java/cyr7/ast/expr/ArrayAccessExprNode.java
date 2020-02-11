package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class ArrayAccessExprNode extends ExprNode {

    final String identifier;
    final ExprNode index;

    public ArrayAccessExprNode(String id, ExprNode index){
        this.identifier = id;
        this.index = index;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("[]");
        printer.printAtom(identifier);
        index.prettyPrint(printer);

        printer.endList();
    }
}
