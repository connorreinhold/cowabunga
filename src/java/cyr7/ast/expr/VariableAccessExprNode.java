package cyr7.ast.expr;

import java.util.Optional;

import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.OrdinaryType;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents accessing a specific variable as an expresion. Ex: a:int = b + 2
 * b in this case would be a VariableAccessExprNode
 */
public class VariableAccessExprNode extends ExprAccessNode {

    public final String identifier;

    public VariableAccessExprNode(ComplexSymbolFactory.Location location, String id) {
        super(location);

        assert id != null;

        this.identifier = id;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(identifier);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof VariableAccessExprNode) {
            VariableAccessExprNode oNode = (VariableAccessExprNode) o;
            return this.identifier.equals(oNode.identifier);
        }
        return false;
    }

    @Override
    public ExpandedType typeCheck(Context c) throws SemanticException {
        Optional<OrdinaryType> optionalVar = c.getVar(this.identifier);
        if (optionalVar.isPresent()) {
            return optionalVar.get();
        }
        throw new SemanticException("Variable DNE");
    }
    

}
