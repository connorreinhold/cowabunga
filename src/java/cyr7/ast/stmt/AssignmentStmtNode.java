package cyr7.ast.stmt;


import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class AssignmentStmtNode extends AbstractNode implements StmtNode {

	// Assign [node] to [value]
	public final AssignAccessNode node;
	public final ExprNode value;

	public AssignmentStmtNode(ComplexSymbolFactory.Location location, AssignAccessNode node, ExprNode value) {
		super(location);

		assert node != null;
		assert value != null;

		this.node = node;
		this.value = value;
	}
	
    public boolean equals(Object o) {
        if (o instanceof AssignmentStmtNode) {
            AssignmentStmtNode oNode = (AssignmentStmtNode) o;
            return this.node.equals(oNode.node)
                    && this.value.equals(oNode.value);
        }
        return false;
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
