package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ResultType;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

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

    public final List<Optional<VarDeclNode>> varDecls;

    public final Optional<ExprNode> initializer;

    public VarDeclStmtNode(Location location,
                           List<Optional<VarDeclNode>> varDecls,
                           Optional<ExprNode> initializer) {
        super(location);

        assert varDecls != null;
        assert initializer != null;

        this.varDecls = Util.immutableCopy(varDecls);
        this.initializer = initializer;
    }
    
    public boolean equals(Object o) {
        if (o instanceof VarDeclStmtNode) {
            VarDeclStmtNode oNode = (VarDeclStmtNode)o;
            return this.varDecls.equals(oNode.varDecls)
                    && this.initializer.equals(oNode.initializer);
        }
        return false;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        if (initializer.isPresent()) {
            printer.startList();

            printer.printAtom("=");

            printVarDecls(printer);

            initializer.get().prettyPrint(printer);

            printer.endList();
        } else {
            printVarDecls(printer);
        }
    }

    private void printVarDecls(SExpPrinter printer) {
        if (varDecls.size() != 1) {
            printer.startList();
        }

        for (Optional<VarDeclNode> n : varDecls) {
            if (n.isPresent()) {
                n.get().prettyPrint(printer);
            } else {
                printer.printAtom("_");
            }
        }

        if (varDecls.size() != 1) {
            printer.endList();
        }
    }

    @Override
    public ResultType typeCheck(Context c) throws SemanticException,
            UnbalancedPushPopException {
        if (initializer.isEmpty()) {
            // VarDecl or ArrayDecl
            assert(varDecls.size() == 1);
            VarDeclNode declNode = varDecls.get(0).get();
//            ResultType lhsType = declNode.typeCheck(c);
        } else {
            // VarInit or ExprStmt or MultiAssign
        }
        return null;
    }

}
