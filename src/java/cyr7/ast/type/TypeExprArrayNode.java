package cyr7.ast.type;

import cyr7.ast.AbstractNode;
import cyr7.ast.FunctionHeaderDeclNode;
import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.OrdinaryType;
import cyr7.semantics.PrimitiveType;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

import com.sun.org.apache.bcel.internal.classfile.Node;

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
public final class TypeExprArrayNode extends ITypeExprNode {

    public final ITypeExprNode child;
    public final Optional<ExprNode> size;

    public TypeExprArrayNode(Location location,
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
    public OrdinaryType typeCheck(Context c) throws SemanticException {
        OrdinaryType type = this.child.typeCheck(c);
        return new ArrayType(type);
    }

}
