package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class ArrayAssignAccessNode extends AbstractNode implements AssignAccessNode {
	final AssignAccessNode node;
	final ExprNode index;
	
	public ArrayAssignAccessNode(ComplexSymbolFactory.Location location, AssignAccessNode node, ExprNode index) {
		super(location);

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
