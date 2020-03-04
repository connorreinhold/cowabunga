package cyr7.ast.expr.access;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.AbstractExprNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Objects;

public final class VariableAccessExprNode extends AbstractExprNode {

    public final String identifier;

    public VariableAccessExprNode(Location location, String id) {
        super(location);
        assert id != null;
        this.identifier = id;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableAccessExprNode that = (VariableAccessExprNode) o;
        return Objects.equals(identifier, that.identifier);
    }

}

