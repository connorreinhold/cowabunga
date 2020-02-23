package cyr7.ast.expr;

import cyr7.ast.AbstractNode;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.List;

/**
 * Represents a call to a function (which is still an expression). Contains the
 * identifier of the function as well as a list of parameters. Ex: fib(4) would
 * have identifier = fib and parameters = {4}
 */
public final class FunctionCallExprNode extends AbstractNode implements ExprNode {

    public final String identifier;
    public final List<ExprNode> parameters;

    public FunctionCallExprNode(Location location,
            String id, List<ExprNode> parameters) {
        super(location);

        assert id != null;
        assert parameters != null;

        this.identifier = id;
        this.parameters = Util.immutableCopy(parameters);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
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

}
