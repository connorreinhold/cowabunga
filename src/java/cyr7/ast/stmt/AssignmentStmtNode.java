package cyr7.ast.stmt;


import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class AssignmentStmtNode extends StmtNode{
	// Assign [node] to [value]
	final AssignAccessNode node;
	final ExprNode value;

	public AssignmentStmtNode(AssignAccessNode node, ExprNode value) {
		this.node = node;
		this.value = value;
	}
	
	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.startList();
		printer.printAtom("=");
		node.prettyPrint(printer);
		value.prettyPrint(printer);
		printer.endList();
		
	}
}
