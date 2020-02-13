package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.List;
import java.util.Optional;

public class VarDeclStmtNode extends StmtNode {

    final List<Optional<VarDeclNode>> varDecls;

    final Optional<ExprNode> initializer;

    public VarDeclStmtNode(List<Optional<VarDeclNode>> varDecls, Optional<ExprNode> initializer) {
        this.varDecls = Util.immutableCopy(varDecls);
        this.initializer = initializer;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        if (initializer.isPresent()) {
            printer.startList();

            printer.printAtom("=");

            for (Optional<VarDeclNode> n : varDecls) {
                if (n.isPresent()) {
                    n.get().prettyPrint(printer);
                } else {
                    printer.printAtom("_");
                }
            }

            initializer.get().prettyPrint(printer);

            printer.endList();
        } else {
            for (Optional<VarDeclNode> n : varDecls) {
                if (n.isPresent()) {
                    n.get().prettyPrint(printer);
                } else {
                    printer.printAtom("_");
                }
            }
        }
    }

}
