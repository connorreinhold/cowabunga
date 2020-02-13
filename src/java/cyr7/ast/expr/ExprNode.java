package cyr7.ast.expr;

import cyr7.ast.AbstractNode;
import cyr7.ast.INode;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class ExprNode extends AbstractNode {

    public ExprNode(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
