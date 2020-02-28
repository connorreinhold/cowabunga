package cyr7.ast.toplevel;

import cyr7.ast.AbstractNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a single [use] statement at the top of XI files. Contains the
 * name of the interface
 * that the XI file references
 */
public final class UseNode extends AbstractNode {

    public final String interfaceName;

    public UseNode(ComplexSymbolFactory.Location location,
                   String interfaceName) {
        super(location);

        assert interfaceName != null;

        this.interfaceName = interfaceName;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UseNode) {
            UseNode oNode = (UseNode) o;
            return this.interfaceName.equals(oNode.interfaceName);
        }
        return false;
    }

}
