package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Represents a single access to an Array at Expr value [index], and a child object [accessNode] 
 * potentially representing 'nearer' array accesses. 
 * Ex: ArrayAccessExprNode(ArrayAccessExprNode(VarAccessExprNode("arr"), 3), 4) = arr[3][4]
 */
public class ArrayAccessExprNode extends ExprAccessNode {

    final ExprAccessNode accessNode;
    final ExprNode index;

    public ArrayAccessExprNode(ExprAccessNode accessNode, ExprNode index){
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
