package cyr7.ast.stmt.assign;

import cyr7.ast.AbstractNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Objects;

public final class VariableAssignNode extends AbstractNode implements AssignNode {

    public final String identifier;

    public VariableAssignNode(Location location,
                              String id) {
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
        VariableAssignNode that = (VariableAssignNode) o;
        return Objects.equals(identifier, that.identifier);
    }

}
