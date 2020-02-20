package cyr7.ast.stmt;

import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ResultType;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * A statement of the form
 * _ = f(e)
 */
public final class ExprStmtNode extends StmtNode {

    public final FunctionCallExprNode expr;

    public ExprStmtNode(Location location, FunctionCallExprNode expr) {
        super(location);

        this.expr = expr;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public ResultType typeCheck(Context c) throws SemanticException, UnbalancedPushPopException {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

}
