package cyr7.ast.stmt;

import cyr7.ast.expr.FunctionCallExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
/**
 * Represents a call to a procedure [procedureCall], which is a FunctionCallExprNode with
 * no return types
 */
public class ProcedureStmtNode extends StmtNode{
	final FunctionCallExprNode procedureCall;
	public ProcedureStmtNode(FunctionCallExprNode procedureCall) {
		assert procedureCall != null;
		this.procedureCall = procedureCall;
	}
	@Override
	public void prettyPrint(SExpPrinter printer) {
		this.procedureCall.prettyPrint(printer);
	}

}
