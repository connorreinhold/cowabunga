package cyr7.ast.expr;

import cyr7.ast.AbstractNode;
import cyr7.ast.INode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a generic expression: Ex: 1+1, true, arr[2][3]
 */
public abstract class ExprNode implements INode {

    public ExprNode(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
