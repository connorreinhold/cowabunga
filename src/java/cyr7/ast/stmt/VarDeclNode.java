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

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom(identifier);
        typeExpr.prettyPrint(printer);
        printer.endList();
    }
}
