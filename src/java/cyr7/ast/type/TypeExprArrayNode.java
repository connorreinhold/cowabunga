package cyr7.ast.type;

import cyr7.ast.AbstractNode;
import cyr7.ast.FunctionHeaderDeclNode;
import cyr7.ast.expr.ExprNode;
import cyr7.semantics.Context;
import cyr7.semantics.Type;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Optional;

/**
 * Represents an XI array type. Each TypeExprArrayNode contains an optional
 * size of the array at the baseline
 * dimension along with children who representing 'further away' array
 * dimensions.
 * <p>
 * Example: the type int[4][] would be represented as:
 * TypeExprArrayNode(TypeExprArrayNode(PrimitiveEnumNode(int), Optional
 * .em`pty()), Optional.of(4))
 */
public final class TypeExprArrayNode extends AbstractNode implements ITypeExprNode {

    public final ITypeExprNode child;
    public final Optional<ExprNode> size;

    public TypeExprArrayNode(ComplexSymbolFactory.Location location,
                             ITypeExprNode child, Optional<ExprNode> size) {
        super(location);

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
    public Type typeCheck(Context c) {
        return null;
    }

}
