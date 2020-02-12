package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.type.PrimitiveTypeNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.Collections;
import java.util.List;

public class ArrayVarDeclStmtNode extends VarDeclStmtNode {

    final String identifier;
    final ArrayInitNode arrayInit;

    /// A possibly null value expression
    final ExprNode valueInit;

    public ArrayVarDeclStmtNode(String identifier, ArrayInitNode arrayInit, ExprNode valueInit) {
        this.identifier = identifier;
        this.arrayInit = arrayInit;
        this.valueInit = valueInit;
    }

    public ArrayVarDeclStmtNode(String identifier,
                                PrimitiveTypeNode primitive,
                                List<ExprNode> dimensionList,
                                ExprNode valueInit) {
        ArrayInitNode node = new ArrayInitPrimitiveNode(primitive);
        Collections.reverse(dimensionList);
        for (ExprNode e : dimensionList) {
            if (e == null) {
                node = new ArrayInitDimensionlessNode(node);
            } else {
                node = new ArrayInitDimensionNode(node, e);
            }
        }

        this.identifier = identifier;
        this.arrayInit = node;
        this.valueInit = valueInit;
    }

    public ArrayVarDeclStmtNode(String identifier,
                                PrimitiveTypeNode primitive,
                                List<ExprNode> dimensionList) {
        this(identifier, primitive, dimensionList, null);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom(identifier);

        arrayInit.prettyPrint(printer);

        if (valueInit != null) {
            valueInit.prettyPrint(printer);
        }

        printer.endList();
    }
}
