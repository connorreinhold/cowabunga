package cyr7.ast.expr;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cyr7.ast.Node;
import cyr7.semantics.types.FunctionType;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Represents a call to a function (which is still an expression). Contains the
 * identifier of the function as well as a list of parameters. Ex: fib(4) would
 * have identifier = fib and parameters = {4}
 */
public final class FunctionCallExprNode extends AbstractExprNode {

    public final String identifier;
    public final List<ExprNode> parameters;

    private Optional<FunctionType> functionType = Optional.empty();

    public FunctionCallExprNode(Location location,
                                String id, List<ExprNode> parameters) {
        super(location);

        assert id != null;
        assert parameters != null;

        this.identifier = id;
        this.parameters = Util.immutableCopy(parameters);
    }

    public Optional<FunctionType> getFunctionType() {
        return functionType;
    }

    public void setFunctionType(FunctionType type) {
        this.functionType = Optional.of(type);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.unmodifiableList(parameters);
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
