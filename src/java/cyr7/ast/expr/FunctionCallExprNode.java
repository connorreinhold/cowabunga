package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a call to a function (which is still an expression). Contains the identifier of the
 * function as well as a list of parameters. Ex: fib(4) would have identifier = fib and parameters = {4}
 */
public class FunctionCallExprNode extends ExprNode {

    public final String identifier;
    public final List<ExprNode> parameters;

    public FunctionCallExprNode(ComplexSymbolFactory.Location location, String id, List<ExprNode> parameters) {
        super(location);

        assert id != null;
        assert parameters != null;

        this.identifier = id;
        this.parameters = Util.immutableCopy(parameters);
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
