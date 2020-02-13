package cyr7.ast.expr;

import java_cup.runtime.ComplexSymbolFactory;

import java.util.LinkedList;

public class LengthCallExprNode extends FunctionCallExprNode {

    public LengthCallExprNode(ComplexSymbolFactory.Location location, LinkedList<ExprNode> parameters) {
        super(location, "length", parameters);
    }
}
