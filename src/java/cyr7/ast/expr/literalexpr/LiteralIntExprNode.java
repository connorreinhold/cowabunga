package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.Type;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents an integer literal, Ex: [contents] = "20"
 */
public class LiteralIntExprNode extends ExprNode {

    final String contents;

    public LiteralIntExprNode(ComplexSymbolFactory.Location location, String contents) {
        super(location);

        assert contents != null;

        this.contents = contents;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(contents);
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof LiteralIntExprNode) {
            LiteralIntExprNode oNode = (LiteralIntExprNode) o;
            return this.contents.equals(oNode.contents);
        }
        return false;    
    }

    @Override
    public Type typeCheck(Context c) {
        return PrimitiveType.INT;
    }
}
