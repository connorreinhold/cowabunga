package cyr7.ast;

import cyr7.ast.type.TypeNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class VarDeclNode implements NodeInterface {

    final String identifier;
    final TypeNode type;

    public VarDeclNode(String id, TypeNode type) {
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
    	if (o instanceof VarDeclNode) {
    		return this.identifier.equals(((VarDeclNode) o).identifier) &&
    				this.type.equals(((VarDeclNode) o).type);
    	}
    	return false;
    }
}
