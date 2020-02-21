package cyr7.ast.expr;

import cyr7.exceptions.SemanticException;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.PrimitiveType;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a single access to an Array at Expr value [index], and a child
 * object [accessNode] potentially representing 'nearer' array accesses. Ex:
 * ArrayAccessExprNode(ArrayAccessExprNode(VarAccessExprNode("arr"), 3), 4) =
 * arr[3][4]
 */
public final class ArrayAccessExprNode extends ExprAccessNode {

    public final ExprAccessNode accessNode;
    public final ExprNode index;

    public ArrayAccessExprNode(ComplexSymbolFactory.Location location,
            ExprAccessNode accessNode, ExprNode index) {
        super(location);

        assert accessNode != null;
        assert index != null;

        this.accessNode = accessNode;
        this.index = index;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayAccessExprNode) {
            ArrayAccessExprNode oNode = (ArrayAccessExprNode) o;
            return this.accessNode.equals(oNode.accessNode)
                    && this.index.equals(oNode.index);
        }
        return false;
    }

}
