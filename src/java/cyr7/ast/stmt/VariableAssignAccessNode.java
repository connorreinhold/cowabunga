package cyr7.ast.stmt;

import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Represents the [identifier] in a Variable Assignment, i.e. moo = 3 would have
 * moo represented by a VariableAssignAccessNode
 */
public class VariableAssignAccessNode extends AssignAccessNode{
	final String identifier;
	
	public VariableAssignAccessNode(String identifier) {
		this.identifier = identifier;
	}
	
	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.printAtom(identifier);
	}

}
