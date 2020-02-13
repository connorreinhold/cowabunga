package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Represents a boolean literal of either true or false
 */
public class LiteralBoolExprNode extends ExprNode{

	final boolean contents;
	
	public LiteralBoolExprNode(boolean contents) {
		this.contents = contents;
	}
	
	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.printAtom(String.valueOf(contents));
	}
}
