package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.Type;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Node that represents the expression [ExprNode left] - [ExprNode right]
 */
public class SubExprNode extends BinExprNode {

    public SubExprNode(ComplexSymbolFactory.Location location, ExprNode left, ExprNode right) {
        super(location, left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("-");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof SubExprNode) {
            SubExprNode oNode = (SubExprNode) o;
            return this.left.equals(oNode.left) && this.right.equals(
                    oNode.right);
        }
        return false;    
    }

    @Override
    public Type typeCheck(Context c) throws SemanticException {
        if (left.typeCheck(c) == PrimitiveType.INT && right.typeCheck(c) == PrimitiveType.INT) {
            return PrimitiveType.INT;
        }
        throw new SemanticException("Failed type check at SUB");
    }
}
