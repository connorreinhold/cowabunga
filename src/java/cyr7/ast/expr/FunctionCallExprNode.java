package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.*;
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
        Optional<FunctionType> optionalFn = c.getFn(this.identifier);

        if (optionalFn.isPresent()) {
            FunctionType function = optionalFn.get();

            if (function.input == UnitType.UNIT) {
                if (this.parameters.isEmpty()) return function.output;
            } else if (function.input instanceof OrdinaryType) {
                if (this.parameters.size() == 1 && this.parameters.get(0).typeCheck(c) == function.input) {
                    return function.output;
                }
            } else if (function.input instanceof TupleType) {
                TupleType paramTypes = (TupleType) function.input;

                if (this.parameters.size() != paramTypes.elements.size()) {
                    throw new SemanticException("Param Size Mismatch");
                }

                for (int i = 0; i < this.parameters.size(); i++) {
                    if (paramTypes.elements.get(i) != this.parameters.get(i).typeCheck(c)) {
                        throw new SemanticException("Param Type Mismatch");
                    }
                }

                return function.output;
            }

            throw new SemanticException("FUNCTION BAD");
        }

        throw new SemanticException("FUNCTION DNE");
    }
}
