package cyr7.ast.stmt;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.type.TypeNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class SingleVarDeclStmtNode extends VarDeclStmtNode {

    final VarDeclNode varDecl;

    /// A possibly null initializer
    final ExprNode initializer;

    public SingleVarDeclStmtNode(VarDeclNode varDecl, ExprNode initializer) {
        this.varDecl = varDecl;
        this.initializer = initializer;
    }

    public SingleVarDeclStmtNode(VarDeclNode varDecl) {
        this.varDecl = varDecl;
        this.initializer = null;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        if (initializer == null) {
            varDecl.prettyPrint(printer);
        } else {
            printer.startList();

            printer.printAtom("=");

            varDecl.prettyPrint(printer);

            initializer.prettyPrint(printer);

            printer.endList();
        }
    }
}
