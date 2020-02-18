package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents assigning an array index to a value. Ex: the LHS of arr[3][4] = 3 
 * would be represented by 
 * ArrayAssignAccessNode(
 *      ArrayAssignAccessNode(VariableAssignAccessNode("arr"), 3), 4)
 */
public class ArrayAssignAccessNode extends AssignAccessNode {
	public final AssignAccessNode node;
	public final ExprNode index;
	
	public ArrayAssignAccessNode(ComplexSymbolFactory.Location location, AssignAccessNode node, ExprNode index) {
		super(location);

		assert node != null;
		assert index != null;

		this.node = node;
		this.index = index;
	}
	
    public boolean equals(Object o) {
        if (o instanceof ArrayAssignAccessNode) {
            ArrayAssignAccessNode oNode = (ArrayAssignAccessNode) o;
            return this.node.equals(oNode.node)
                    && this.index.equals(oNode.index)
                    && this.node.equals(oNode.node);
        }
        return false;
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
