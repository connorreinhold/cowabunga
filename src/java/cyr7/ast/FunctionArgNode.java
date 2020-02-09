package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class FunctionArgNode implements NodeInterface {
    final String identifier;
    final TypeNode type;

    public FunctionArgNode(String id, TypeNode type) {
        this.identifier = id;
        this.type = type;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom(this.identifier);
        type.prettyPrint(printer);
        printer.endList();
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o instanceof FunctionArgNode) {
    		return this.identifier.equals(((FunctionArgNode) o).identifier) && 
    				this.type.equals(((FunctionArgNode) o).type);
    	}
    	return false;
    }
}
