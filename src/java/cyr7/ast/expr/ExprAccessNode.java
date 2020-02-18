package cyr7.ast.expr;

import java_cup.runtime.ComplexSymbolFactory;

/**
 * Recursively represents accessing the value of some variable, either through a
 * singular variable 'a', or through indexing into an array: a[2][3]
 */
public abstract class ExprAccessNode extends ExprNode {

    public ExprAccessNode(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
