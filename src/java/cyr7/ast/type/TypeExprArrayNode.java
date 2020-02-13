package cyr7.ast.type;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.Optional;

/**
 * Represents an XI array type. Each TypeExprArrayNode contains an optional size of the array at the baseline
 * dimension along with children who representing 'further away' array dimensions.
 * 
 * Example: the type int[4][] would be represented as: 
 * 	TypeExprArrayNode(TypeExprArrayNode(PrimitiveEnumNode(int), Optional.empty()), Optional.of(4))
 */
public final class TypeExprArrayNode implements ITypeExprNode {

    final ITypeExprNode child;
    final Optional<ExprNode> size;

    public TypeExprArrayNode(ITypeExprNode child, Optional<ExprNode> size) {
        this.child = child;
        this.size = size;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("[]");

        child.prettyPrint(printer);

        size.ifPresent(exprNode -> exprNode.prettyPrint(printer));

        printer.endList();
    }

}
