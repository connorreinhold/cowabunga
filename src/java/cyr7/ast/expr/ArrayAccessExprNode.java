package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class ArrayAccessExprNode extends ExprAccessNode {

    final ExprAccessNode accessNode;
    final ExprNode index;

    public ArrayAccessExprNode(ComplexSymbolFactory.Location location, ExprAccessNode accessNode, ExprNode index){
        super(location);
        this.accessNode = accessNode;
        this.index = index;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("[]");
        accessNode.prettyPrint(printer);
        index.prettyPrint(printer);

        printer.endList();
    }
}
