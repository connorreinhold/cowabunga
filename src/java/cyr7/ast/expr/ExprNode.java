package cyr7.ast.expr;

import cyr7.ast.AbstractNode;
import cyr7.ast.INode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a generic expression: Ex: 1+1, true, arr[2][3]
 */
public abstract class ExprNode extends AbstractNode {

    public ExprNode(ComplexSymbolFactory.Location location) {
        super(location);
    }

    public abstract ExpandedType typeCheck(Context c) throws SemanticException;

}
