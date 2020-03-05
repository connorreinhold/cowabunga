package cyr7.ast.type;

import cyr7.ast.Node;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.List;

public final class PrimitiveTypeNode extends TypeExprNode {

    public final PrimitiveEnum type;

    public PrimitiveTypeNode(Location location, PrimitiveEnum type) {
        super(location);
        this.type = type;
    }

    @Override
    public List<Node> getChildren() {
        return List.of();
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof PrimitiveTypeNode) {
            PrimitiveTypeNode oNode = (PrimitiveTypeNode) o;
            return this.type.equals(oNode.type);
        }
        return false;
    }

}
