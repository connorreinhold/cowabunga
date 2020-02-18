package cyr7.ast.expr;

import cyr7.exceptions.SemanticException;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.OrdinaryType;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.TypeCheckUtil;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;

/**
 * Represents an Array Expression: i.e. {1,2,3,} with a list of ExprNode for
 * values
 */
public class ArrayExprNode extends ExprNode {

    public final List<ExprNode> arrayVals;

    public ArrayExprNode(ComplexSymbolFactory.Location location,
            List<ExprNode> arrayVals) {
        super(location);

        assert arrayVals != null;

        this.arrayVals = Util.immutableCopy(arrayVals);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        for (ExprNode e : this.arrayVals) {
            e.prettyPrint(printer);
        }

        printer.endList();

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayExprNode) {
            ArrayExprNode oNode = (ArrayExprNode) o;
            return this.arrayVals.equals(oNode.arrayVals);
        }
        return false;
    }

    @Override
    public ExpandedType typeCheck(Context c) throws SemanticException {
        if (arrayVals.size() == 0) {
            return new ArrayType(PrimitiveType.ANY);
        }

        ExpandedType firstType = arrayVals.get(0).typeCheck(c);
        if (firstType instanceof OrdinaryType) {
            for (ExprNode value : arrayVals) {
                // make sure all array elements have the same type as the first
                if (!TypeCheckUtil.checkTypeEquality(value.typeCheck(c),
                        firstType))
                    throw new SemanticException("Invalid array element");
            }
            return new ArrayType((OrdinaryType) firstType);
        }

        throw new SemanticException("Invalid array element");
    }
}
