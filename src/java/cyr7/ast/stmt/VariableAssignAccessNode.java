package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents the [identifier] in a Variable Assignment, i.e. moo = 3 would have
 * moo represented by a VariableAssignAccessNode
 */
public class VariableAssignAccessNode extends AbstractNode implements AssignAccessNode {

	public final String identifier;
	
	public VariableAssignAccessNode(ComplexSymbolFactory.Location location,
									String identifier) {
		super(location);

		assert identifier != null;

		this.identifier = identifier;
	}
	
	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.printAtom(identifier);
	}

    public boolean equals(Object o) {
        if (o instanceof VariableAssignAccessNode) {
            VariableAssignAccessNode oNode = (VariableAssignAccessNode)o;
            return this.identifier.equals(oNode.identifier);
        }
        return false;
    }
	
}
