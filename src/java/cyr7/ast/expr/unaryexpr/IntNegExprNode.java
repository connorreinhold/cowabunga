package cyr7.ast.expr.unaryexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.Type;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents the integer negation expression: -[ExprNode expr]
 */
public class IntNegExprNode extends UnaryExprNode {

    public IntNegExprNode(ComplexSymbolFactory.Location location, ExprNode expr) {
        super(location, expr);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("-");

        expr.prettyPrint(printer);

        printer.endList();
    }
    
    public boolean equals(Object o) {
        if (o instanceof IntNegExprNode) {
            return super.equals(o);
        }
        return false;
    }

    @Override
    public Type typeCheck(Context c) throws SemanticException {
        if (expr.typeCheck(c) == PrimitiveType.INT) return PrimitiveType.BOOL;
        throw new SemanticException("Failed at INT NEG");
    }
}
