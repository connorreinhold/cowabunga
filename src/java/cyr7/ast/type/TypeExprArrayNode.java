package cyr7.ast.type;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.Collections;
import java.util.List;

public class TypeExprArrayNode implements ITypeExprNode {

    public static ITypeExprNode fromDimensionList(PrimitiveEnumNode primitive, List<ExprNode> dimensionList) {
        ITypeExprNode node = primitive;
        Collections.reverse(dimensionList);
        for (ExprNode e : dimensionList) {
            // e may be null
            node = new TypeExprArrayNode(node, e);
        }
        return node;
    }

    final ITypeExprNode child;

    /// An optional size
    final ExprNode size;

    public TypeExprArrayNode(ITypeExprNode child, ExprNode size) {
        this.child = child;
        this.size = size;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("[]");

        child.prettyPrint(printer);

        if (size != null) {
            size.prettyPrint(printer);
        }

        printer.endList();
    }

}
