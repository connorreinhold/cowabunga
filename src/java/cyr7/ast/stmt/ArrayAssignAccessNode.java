package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class ArrayAssignAccessNode extends AssignAccessNode {
	final AssignAccessNode node;
	final ExprNode index;
	
	public ArrayAssignAccessNode(AssignAccessNode node, ExprNode index) {
		this.node = node;
		this.index = index;
	}
	
	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.startList();
		printer.printAtom("[]");
		node.prettyPrint(printer);
		index.prettyPrint(printer);
		printer.endList();
		
	}

}
