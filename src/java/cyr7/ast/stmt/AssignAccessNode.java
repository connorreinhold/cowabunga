package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.OrdinaryType;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Represents the LHS of an Assignment Statement where the variable has 
 * already been declared: 
 * Ex: r = 4
 */

public abstract class AssignAccessNode extends AbstractNode {

    public AssignAccessNode(Location location) {
        super(location);
    }
    
    public abstract OrdinaryType typeCheck(Context c) throws SemanticException; 

}
