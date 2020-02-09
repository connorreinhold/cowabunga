package cyr7.ast;

import java.util.List;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class IxiProgramNode extends ProgramNode implements NodeInterface {
    final List<FunctionDeclNode> functionDeclarations;

    public IxiProgramNode(List<FunctionDeclNode> lst) {
        this.functionDeclarations = lst;
    }

    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("");
        printer.startUnifiedList();
        this.functionDeclarations.forEach(f -> {
            f.prettyPrint(printer);
        });
        printer.endList();
        printer.printAtom("");
        printer.endList();
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o instanceof IxiProgramNode) {
    		IxiProgramNode oNode = (IxiProgramNode) o;
    		if (this.functionDeclarations.size() == oNode.functionDeclarations.size()) {
    			for (int i = 0; i < this.functionDeclarations.size(); i++) {
    				if (!this.functionDeclarations.get(i).equals(oNode.functionDeclarations.get(i))) {
    					return false;
    				}
    			}
    			return true;
    		}
    	}
    	return false;
    }

}
