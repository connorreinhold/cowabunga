package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.FunctionType;
import cyr7.semantics.VariableType;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.Optional;

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

	@Override
	public ExpandedType typeCheck(Context c) throws SemanticException {
		Optional<FunctionType> optionalVar = c.getFn(this.identifier);
		if (optionalVar.isPresent()) {
			return optionalVar.get();
		}
		return null;
	}
}
