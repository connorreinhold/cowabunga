package cyr7.ast.expr;

import java_cup.runtime.ComplexSymbolFactory;

public abstract class ExprAccessNode extends ExprNode{

    public ExprAccessNode(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
