package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.LinkedList;

/**
 * Represents a return statement in a block, containing a potentially empty list [exprs] of ExprNodes
 */
public final class ReturnStmtNode extends AbstractNode implements StmtNode {

    final LinkedList<ExprNode> exprs;

    public ReturnStmtNode(ComplexSymbolFactory.Location location, LinkedList<ExprNode> exprs) {
        super(location);
        assert exprs != null;

        this.exprs = exprs;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("return");
        for (ExprNode expr : exprs) {
            expr.prettyPrint(printer);
        }
        printer.endList();
    }

}
