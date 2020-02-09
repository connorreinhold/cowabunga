package cyr7.ast;

import java.util.Iterator;
import java.util.List;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class FunctionDeclNode implements NodeInterface {
	final String identifier;
	final List<FunctionArgNode> args;
	final List<TypeNode> returnTypes;

	public FunctionDeclNode(String id, List<FunctionArgNode> args, List<TypeNode> returnTypes) {
		this.identifier = id;
		this.args = args;
		this.returnTypes = returnTypes;
	}

	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.startUnifiedList();
		;

		printer.printAtom(this.identifier);

		printer.startList();
		args.forEach(a -> {
			a.prettyPrint(printer);
		});
		printer.endList();

		printer.startList();
		returnTypes.forEach(r -> {
			r.prettyPrint(printer);
		});
		printer.endList();
		printer.endList();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FunctionDeclNode) {
			FunctionDeclNode oNode = (FunctionDeclNode) o;
			return this.identifier.equals(oNode.identifier) 
			        && this.args.equals(oNode.args)
					&& this.returnTypes.equals(oNode.returnTypes);
		}
		return false;
	}
}
