package cyr7.ast.expr;

import cyr7.exceptions.SemanticException;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.TypeCheckUtil;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a single access to an Array at Expr value [index], and a child
 * object [accessNode] potentially representing 'nearer' array accesses. Ex:
 * ArrayAccessExprNode(ArrayAccessExprNode(VarAccessExprNode("arr"), 3), 4) =
 * arr[3][4]
 */
public class ArrayAccessExprNode extends ExprAccessNode {

    final ExprAccessNode accessNode;
    final ExprNode index;

    public ArrayAccessExprNode(ComplexSymbolFactory.Location location,
            ExprAccessNode accessNode, ExprNode index) {
        super(location);

        assert accessNode != null;
        assert index != null;

        this.accessNode = accessNode;
        this.index = index;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("[]");
        accessNode.prettyPrint(printer);
        index.prettyPrint(printer);

        printer.endList();
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

    @Override
    public ExpandedType typeCheck(Context c) throws SemanticException {
        if (TypeCheckUtil.checkTypeEquality(index.typeCheck(c),
                PrimitiveType.INT)) {
            ExpandedType accessNodeType = accessNode.typeCheck(c);
            if (accessNodeType instanceof ArrayType) {
                return ((ArrayType) accessNodeType).child;
            }
            throw new SemanticException("invalid access");
        }
        throw new SemanticException("index not an int");
    }
}
