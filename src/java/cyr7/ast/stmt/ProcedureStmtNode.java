package cyr7.ast.stmt;

import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.ResultType;
import cyr7.semantics.TypeCheckUtil;
import cyr7.semantics.UnitType;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a call to a procedure [procedureCall], which is a 
 * FunctionCallExprNode with return type unit.
 */
public final class ProcedureStmtNode extends StmtNode {
	public final FunctionCallExprNode procedureCall;

	public ProcedureStmtNode(ComplexSymbolFactory.Location location,
							 FunctionCallExprNode procedureCall) {
		super(location);

		assert procedureCall != null;

		this.procedureCall = procedureCall;
	}

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof ProcedureStmtNode) {
            ProcedureStmtNode oNode = (ProcedureStmtNode)o;
            return this.procedureCall.equals(oNode.procedureCall);
        }
        return false;
    }

    @Override
    public ResultType typeCheck(Context c) throws SemanticException,
            UnbalancedPushPopException {
        ExpandedType type = procedureCall.typeCheck(c);
        if (TypeCheckUtil.checkTypeEquality(type, UnitType.UNIT)) {
            return ResultType.UNIT;
        } else {
            throw new SemanticException("Expected a procedure but found a "
                    + "function instead.");
        }
    }



}
