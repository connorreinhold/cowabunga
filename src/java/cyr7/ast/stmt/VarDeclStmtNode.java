package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.List;

public class VarDeclStmtNode extends StmtNode {

    final List<VarDeclNode> varDecls;

    /// A possibly null initializer
    final ExprNode initializer;

    public VarDeclStmtNode(List<VarDeclNode> varDecls, ExprNode initializer) {
        this.varDecls = varDecls;
        this.initializer = initializer;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        if (initializer == null) {
            for (VarDeclNode n : varDecls) {
                n.prettyPrint(printer);
            }
        } else {
            printer.startList();

            for (VarDeclNode n : varDecls) {
                if (n == null) {
                    printer.printAtom("_");
                } else {
                    n.prettyPrint(printer);
                }
            }

            initializer.prettyPrint(printer);

            printer.endList();
        }
    }

}
