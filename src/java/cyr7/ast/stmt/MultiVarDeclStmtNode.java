package cyr7.ast.stmt;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.List;

public class MultiVarDeclStmtNode extends VarDeclStmtNode {

    /// A list with possibly null elements which signify discard assignments
    final List<VarDeclNode> varDecls;

    /// A possibly null initializer
    final FunctionCallExprNode initializer;

    public MultiVarDeclStmtNode(List<VarDeclNode> varDecls,
                                FunctionCallExprNode initializer) {
        assert varDecls != null;
        assert initializer != null;

        this.varDecls = Util.immutableCopy(varDecls);
        this.initializer = initializer;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("=");

        printer.startList();
        for (VarDeclNode decl : varDecls) {
            if (decl == null) {
                printer.printAtom("_");
            } else {
                decl.prettyPrint(printer);
            }
        }
        printer.endList();

        initializer.prettyPrint(printer);

        printer.endList();
    }

}
