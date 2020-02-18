package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ResultType;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a return statement in a block, containing a potentially empty list [exprs] of ExprNodes
 */
public final class ReturnStmtNode extends StmtNode {

    public final List<ExprNode> exprs;

    public ReturnStmtNode(ComplexSymbolFactory.Location location, List<ExprNode> exprs) {
        super(location);
        assert exprs != null;

        this.exprs = Util.immutableCopy(exprs);
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
    
    public boolean equals(Object o) {
        if (o instanceof ReturnStmtNode) {
            ReturnStmtNode oNode = (ReturnStmtNode)o;
            return this.exprs.equals(oNode.exprs);
        }
        return false;
    }

    @Override
    public ResultType typeCheck(Context c) throws SemanticException,
            UnbalancedPushPopException {
        // TODO Auto-generated method stub
        return null;
    }

}
