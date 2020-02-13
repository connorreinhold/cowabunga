package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.List;
import java.util.Optional;

/**
 * Represents a variable declaration statement with an optional assignment [initializer]. Contains a list 
 * [varDecls] of Optional<VarDeclNodes> to initialize in the statement. An Optional.empty() represents an _,
 * discarding the value of an initialization.
 * 
 * Invariant: Cannot have multiple VarDeclNodes in [varDecls] or an Optional.empty() in [varDecls] without
 * the initializer being a FunctionCallExprNode
 */
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
