package cyr7.ast.stmt;

import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.OrdinaryType;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Optional;

/**
 * Represents the [identifier] in a Variable Assignment, i.e. moo = 3 would have
 * moo represented by a VariableAssignAccessNode
 */
public final class VariableAssignAccessNode extends AssignAccessNode {

	public final String identifier;
	
	public VariableAssignAccessNode(ComplexSymbolFactory.Location location,
									String identifier) {
		super(location);

		assert identifier != null;

		this.identifier = identifier;
	}

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
	public void prettyPrint(SExpPrinter printer) {
		printer.printAtom(identifier);
	}

    public boolean equals(Object o) {
        if (o instanceof VariableAssignAccessNode) {
            VariableAssignAccessNode oNode = (VariableAssignAccessNode)o;
            return this.identifier.equals(oNode.identifier);
        }
        return false;
    }

    @Override
    public OrdinaryType typeCheck(Context c) throws SemanticException {
        Optional<OrdinaryType> maybeType = c.getVar(identifier);
        if (maybeType.isPresent()) {
            return maybeType.get();
        } else {
            throw new SemanticException("Unbound variable " + identifier);
        }
    }
	
}
