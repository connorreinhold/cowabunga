package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.binexpr.SubExprNode;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ExpandedType;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a boolean literal of either true or false
 */
public final class LiteralBoolExprNode extends ExprNode {

	public final boolean contents;
	
	public LiteralBoolExprNode(ComplexSymbolFactory.Location location, boolean contents) {
		super(location);

		this.contents = contents;
	}

	@Override
	public <T> T accept(AbstractVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.printAtom(String.valueOf(contents));
	}
	
    @Override
    public boolean equals(Object o) {
        if (o instanceof LiteralBoolExprNode) {
            LiteralBoolExprNode oNode = (LiteralBoolExprNode) o;
            return this.contents == oNode.contents;
        }
        return false;    
    }

	@Override
	public ExpandedType typeCheck(Context c) {
		return PrimitiveType.BOOL;
	}
}
