package cyr7.ast.type;

import cyr7.ast.INode;
import cyr7.ast.expr.ExprNode;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ITypeExprNode extends INode {

    static ITypeExprNode fromDimensionList(PrimitiveEnumNode primitive, List<Optional<ExprNode>> dimensionList) {
        ITypeExprNode node = primitive;
        Collections.reverse(dimensionList);
        for (Optional<ExprNode> e : dimensionList) {
            // e may be null
            node = new TypeExprArrayNode(node, e);
        }
        return node;
    }

}
