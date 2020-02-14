package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.LinkedList;

/**
 * Represents a call to a function (which is still an expression). Contains the identifier of the
 * function as well as a list of parameters. Ex: fib(4) would have identifier = fib and parameters = {4}
 */
public class FunctionCallExprNode extends ExprNode {

    final String identifier;
    final LinkedList<ExprNode> parameters;

    public FunctionCallExprNode(ComplexSymbolFactory.Location location, String id, LinkedList<ExprNode> parameters) {
        super(location);
        this.identifier = id;
        this.parameters = parameters;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
    	printer.startList();

        printer.printAtom(identifier);

        for (ExprNode node : parameters) {
            node.prettyPrint(printer);
        }
        
        printer.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FunctionCallExprNode) {
            FunctionCallExprNode oNode = (FunctionCallExprNode) o;
            return this.identifier.equals(oNode.identifier)
                    && this.parameters.equals(oNode.parameters);
        }
        return false;
    }
}
