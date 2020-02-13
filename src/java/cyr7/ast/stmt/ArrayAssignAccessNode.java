package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Represents assigning an array index to a value. Ex: the LHS of arr[3][4] = 3 would be represented by
 * ArrayAssignAccessNode(ArrayAssignAccessNode(VariableAssignAccessNode("arr"), 3), 4)
 */
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
