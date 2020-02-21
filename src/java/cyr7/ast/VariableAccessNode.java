package cyr7.ast;

import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Objects;

public final class VariableAccessNode extends AbstractNode implements AccessNode {

    public final String identifier;

    public VariableAccessNode(ComplexSymbolFactory.Location location,
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
        VariableAccessNode that = (VariableAccessNode) o;
        return Objects.equals(identifier, that.identifier);
    }

}

