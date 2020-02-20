package cyr7.ast;

import java.util.List;

import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class IxiProgramNode extends AbstractNode {

    public final List<FunctionHeaderDeclNode> functionDeclarations;

    public IxiProgramNode(ComplexSymbolFactory.Location location, List<FunctionHeaderDeclNode> lst) {
        super(location);

        assert lst != null;

        this.functionDeclarations = Util.immutableCopy(lst);
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

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
