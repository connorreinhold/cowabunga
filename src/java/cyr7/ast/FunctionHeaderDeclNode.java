package cyr7.ast;

import java.util.LinkedList;

import cyr7.ast.type.TypeNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class FunctionHeaderDeclNode implements NodeInterface {
	final String identifier;
	final LinkedList<VarDeclNode> args;
	final LinkedList<TypeNode> returnTypes;

	public FunctionHeaderDeclNode(String id, LinkedList<VarDeclNode> args,
								  LinkedList<TypeNode> returnTypes) {
		this.identifier = id;
		this.args = args;
		this.returnTypes = returnTypes;
	}

	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.startUnifiedList();

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
		if (o instanceof FunctionHeaderDeclNode) {
			FunctionHeaderDeclNode oNode = (FunctionHeaderDeclNode) o;
			return this.identifier.equals(oNode.identifier) 
			        && this.args.equals(oNode.args)
					&& this.returnTypes.equals(oNode.returnTypes);
		}
		return false;
	}
}
