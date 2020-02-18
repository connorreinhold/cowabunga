package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ExpandedType;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Node that represents the expression: [ExprNode left] > [ExprNode right]
 */
public class GTExprNode extends BinExprNode {

    public GTExprNode(ComplexSymbolFactory.Location location, ExprNode left, ExprNode right) {
        super(location, left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom(">");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof GTExprNode) {
            GTExprNode oNode = (GTExprNode) o;
            return this.left.equals(oNode.left) && this.right.equals(
                    oNode.right);
        }
        return false;    
    }

    @Override
    public ExpandedType typeCheck(Context c) throws SemanticException {
        if (left.typeCheck(c) == PrimitiveType.INT && right.typeCheck(c) == PrimitiveType.INT) {
            return PrimitiveType.BOOL;
        }
        throw new SemanticException("Failed type check at GT");
    }
}
