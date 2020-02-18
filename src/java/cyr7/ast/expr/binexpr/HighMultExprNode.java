package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.TypeCheckUtil;
import cyr7.semantics.ExpandedType;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Node that represents the expr: [ExprNode left] *>> [ExprNode right] where the
 * high multiplication operator returns the highest 64 bit of the 128 bit
 * multiplication operation
 */
public class HighMultExprNode extends BinExprNode {

    public HighMultExprNode(ComplexSymbolFactory.Location location,
            ExprNode left, ExprNode right) {
        super(location, left, right);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("*>>");
        left.prettyPrint(printer);
        right.prettyPrint(printer);
        printer.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HighMultExprNode) {
            HighMultExprNode oNode = (HighMultExprNode) o;
            return this.left.equals(oNode.left)
                    && this.right.equals(oNode.right);
        }
        return false;
    }

    @Override
    public ExpandedType typeCheck(Context c) throws SemanticException {
        if (TypeCheckUtil.checkTypeEquality(left.typeCheck(c),
                PrimitiveType.INT)
                && TypeCheckUtil.checkTypeEquality(right.typeCheck(c),
                        PrimitiveType.INT)) {
            return PrimitiveType.INT;
        }
        throw new SemanticException("Failed type check at HIGH MULT");
    }
}
