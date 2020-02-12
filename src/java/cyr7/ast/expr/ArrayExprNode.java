package cyr7.ast.expr;

import java.util.LinkedList;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class ArrayExprNode extends ExprNode {
	final LinkedList<ExprNode> arrayVals;
	public ArrayExprNode(LinkedList<ExprNode> arrayVals) {
		this.arrayVals = arrayVals;
	}

	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.startList();
		
		for (ExprNode e: this.arrayVals) {
			e.prettyPrint(printer);
		}
		
		printer.endList();
		
	}
}
