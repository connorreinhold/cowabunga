package cyr7.ast;

import java.util.List;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class FunctionDeclNode implements NodeInterface {
    final String identifier;
    final List<FunctionArgNode> args;
    final List<TypeNode> returnTypes;

    public FunctionDeclNode(String id, List<FunctionArgNode> args,
            List<TypeNode> returnTypes) {
        this.identifier = id;
        this.args = args;
        this.returnTypes = returnTypes;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startUnifiedList();;

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
    		if (this.identifier.equals(oNode.identifier) && this.args.size() == oNode.args.size() &&
    				this.returnTypes.size() == oNode.returnTypes.size()) {
    			for (int i = 0; i < this.args.size(); i++) {
    				if (!this.args.get(i).equals(oNode.args.get(i))) {
    					return false;
    				}
    			}
    			for (int i = 0; i < this.returnTypes.size(); i++) {
    				if (!this.returnTypes.get(i).equals(oNode.returnTypes.get(i))) {
    					return false;
    				}
    			}
    			return true;
    		}
    	}
        return false;
    }
}
