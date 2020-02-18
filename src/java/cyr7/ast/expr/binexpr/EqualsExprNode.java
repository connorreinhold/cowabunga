package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.OrdinaryType;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.TypeCheckUtil;
import cyr7.semantics.ExpandedType;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Node that represents taking the equality operator on [ExprNode left] &
 * [ExprNode right]. Evaluates to true or false
 */
public class EqualsExprNode extends BinExprNode {

    public EqualsExprNode(ComplexSymbolFactory.Location location, ExprNode left,
            ExprNode right) {
        super(location, left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("==");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EqualsExprNode) {
            EqualsExprNode oNode = (EqualsExprNode) o;
            return this.left.equals(oNode.left)
                    && this.right.equals(oNode.right);
        }
        return false;
    }

    @Override
    public ExpandedType typeCheck(Context c) throws SemanticException {
        ExpandedType leftType = left.typeCheck(c);
        ExpandedType rightType = right.typeCheck(c);
        if (leftType instanceof OrdinaryType
                && rightType instanceof OrdinaryType && TypeCheckUtil
                        .checkTypeEquality(leftType, rightType)) {
            return PrimitiveType.BOOL;
        }
        throw new SemanticException("Failed type check at EQUALS");
    }
}