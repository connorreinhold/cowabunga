package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public class VariableAssignAccessNode extends AbstractNode implements AssignAccessNode {

	final String identifier;
	
	public VariableAssignAccessNode(ComplexSymbolFactory.Location location,
									String identifier) {
		super(location);

		this.identifier = identifier;
	}
	
	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.printAtom(identifier);
	}

}
