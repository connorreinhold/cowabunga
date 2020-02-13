package cyr7.ast.type;

import cyr7.ast.INode;
import cyr7.ast.expr.ExprNode;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Represents an explicit variable type
 */
public interface ITypeExprNode extends INode {

	/**
	 * @param - primitive type of the larger type
	 * @param - dimensionList - the list of array dimensions associated with the type, with Optional.empty()
	 * representing no size was given for that dimension. dimensionList must be passed in order, i.e. to create
	 * the type for int[4][3][], we pass in: {Optional.of(4), Optional.of(3), Optional.empty()}
	 * @return [node] - an ITypeExprNode representing a recursive definition of the type of the object
	 */
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
