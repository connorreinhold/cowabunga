package cyr7.ast.stmt;

import cyr7.ast.INode;
import cyr7.ast.type.ITypeExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class VarDeclNode implements INode {

    final String identifier;

    final ITypeExprNode typeExpr;

    public VarDeclNode(String identifier, ITypeExprNode typeExpr) {
        this.identifier = identifier;
        this.typeExpr = typeExpr;
    }

//    public VarDecl(String identifier,
//                   PrimitiveTypeNode primitive,
//                   List<ExprNode> dimensionList,
//                   ExprNode initializer) {
//        TypeExprNode node = new TypeExprPrimitiveNode(primitive);
//        Collections.reverse(dimensionList);
//        for (ExprNode e : dimensionList) {
//            if (e == null) {
//                node = new SingleVarArrayDimensionlessNode(node);
//            } else {
//                node = new TypeExprArrayNode(node, e);
//            }
//        }
//
//        this.identifier = identifier;
//        this.typeInitializer = node;
//        this.initializer = initializer;
//    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom(identifier);
        typeExpr.prettyPrint(printer);
        printer.endList();
    }
}
