package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.VariableAccessExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.Type;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Node that represents the expression [ExprNode left] + [ExprNode right]
 */
public class AddExprNode extends BinExprNode {

    public AddExprNode(ComplexSymbolFactory.Location location, ExprNode left, ExprNode right) {
        super(location, left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("+");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AddExprNode) {
            AddExprNode oNode = (AddExprNode) o;
            return this.left.equals(oNode.left)
                    && this.right.equals(oNode.right);
        }
        return false;
    }

    @Override
    public Type typeCheck(Context c) throws SemanticException {
        Type leftType = left.typeCheck(c);
        Type rightType = right.typeCheck(c);

        if (leftType != PrimitiveType.BOOL &&  rightType != PrimitiveType.BOOL) {
            if (leftType.equals(rightType)){
                return leftType;
            }
        }
        throw new SemanticException("Failed type check at ADD");
    }
}
