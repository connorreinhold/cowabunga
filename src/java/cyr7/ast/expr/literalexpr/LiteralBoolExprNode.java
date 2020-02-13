package cyr7.ast.expr.literalexpr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class LiteralBoolExprNode extends ExprNode{

	final boolean contents;
	
	public LiteralBoolExprNode(ComplexSymbolFactory.Location location, boolean contents) {
		super(location);
		this.contents = contents;
	}
	
	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.startList();
		printer.printAtom(""+contents);
		printer.endList();
	}
}
