package cyr7.ast.stmt;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.Collections;
import java.util.List;

public class SingleVarDeclStmtNode extends VarDeclStmtNode {

    final String identifier;

    final SingleVarInitNode typeInitializer;

    /// A possibly null initializer
    final ExprNode initializer;

    public SingleVarDeclStmtNode(String identifier,
                                 PrimitiveTypeNode primitive,
                                 List<ExprNode> dimensionList,
                                 ExprNode initializer) {
        SingleVarInitNode node = new SingleVarPrimitiveNode(primitive);
        Collections.reverse(dimensionList);
        for (ExprNode e : dimensionList) {
            if (e == null) {
                node = new SingleVarArrayDimensionlessNode(node);
            } else {
                node = new SingleVarArrayDimensionNode(node, e);
            }
        }

        this.identifier = identifier;
        this.typeInitializer = node;
        this.initializer = initializer;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        if (initializer == null) {
            printer.startList();
            printer.printAtom(identifier);
            typeInitializer.prettyPrint(printer);
            printer.endList();
        } else {
            printer.startList();

            printer.printAtom("=");

            printer.startList();
            printer.printAtom(identifier);
            typeInitializer.prettyPrint(printer);
            printer.endList();

            initializer.prettyPrint(printer);

            printer.endList();
        }
    }
}
