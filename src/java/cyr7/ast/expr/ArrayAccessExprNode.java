package cyr7.ast.expr;

import cyr7.ast.XiProgramNode;
import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a single access to an Array at Expr value [index], and a child object [accessNode] 
 * potentially representing 'nearer' array accesses. 
 * Ex: ArrayAccessExprNode(ArrayAccessExprNode(VarAccessExprNode("arr"), 3), 4) = arr[3][4]
 */
public class ArrayAccessExprNode extends ExprAccessNode {

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayAccessExprNode) {
            ArrayAccessExprNode oNode = (ArrayAccessExprNode) o;
            return this.accessNode.equals(oNode.accessNode)
                    && this.index.equals(oNode.index);
        }
        return false;
    }
}
