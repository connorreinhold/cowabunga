package cyr7.ast.type;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.OrdinaryType;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Optional;

/**
 * Represents an XI array type. Each TypeExprArrayNode contains an optional
 * size of the array at the baseline
 * dimension along with children who representing 'further away' array
 * dimensions.
 * <p>
 * Example: the type int[4][] would be represented as:
 * TypeExprArrayNode(TypeExprArrayNode(PrimitiveEnumNode(int), Optional
 * .empty()), Optional.of(4))
 */
public final class TypeExprArrayNode extends TypeExprNode {

    public final TypeExprNode child;
    public final Optional<ExprNode> size;

    public TypeExprArrayNode(Location location,
                             TypeExprNode child, Optional<ExprNode> size) {
        super(location);

        this.child = child;
        this.size = size;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TypeExprArrayNode) {
            TypeExprArrayNode oNode = (TypeExprArrayNode) o;
            return this.child.equals(oNode.child)
                    && this.size.equals(oNode.size);
        }
        return false;

    }

    @Override
    public OrdinaryType typeCheck(Context c) throws SemanticException {
        OrdinaryType type = this.child.typeCheck(c);
        return new ArrayType(type);
    }

}
