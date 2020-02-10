package cyr7.ast;

import java.util.LinkedList;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class FunctionDeclNode implements NodeInterface {
	final String identifier;
	final LinkedList<FunctionArgNode> args;
	final LinkedList<TypeNode> returnTypes;

	public FunctionDeclNode(String id, LinkedList<FunctionArgNode> args, 
	        LinkedList<TypeNode> returnTypes) {
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
