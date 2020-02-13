package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.Optional;

public class VarDeclStmtNode extends AbstractNode implements StmtNode {

    final List<Optional<VarDeclNode>> varDecls;

    final Optional<ExprNode> initializer;

    public VarDeclStmtNode(ComplexSymbolFactory.Location location,
                           List<Optional<VarDeclNode>> varDecls,
                           Optional<ExprNode> initializer) {
        super(location);
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
