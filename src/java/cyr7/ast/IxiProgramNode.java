package cyr7.ast;

import java.util.LinkedList;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class IxiProgramNode extends AbstractNode {
    final LinkedList<FunctionHeaderDeclNode> functionDeclarations;

    public IxiProgramNode(ComplexSymbolFactory.Location location, LinkedList<FunctionHeaderDeclNode> lst) {
        super(location);

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
    		return this.functionDeclarations.equals(oNode.functionDeclarations);
    	}
    	return false;
    }

}
