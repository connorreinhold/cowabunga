package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.FunctionCallExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
/**
 * Represents a call to a procedure [procedureCall], which is a FunctionCallExprNode with
 * no return types
 */
public class ProcedureStmtNode extends AbstractNode implements StmntNode {
	final FunctionCallExprNode procedureCall;

	public ProcedureStmtNode(ComplexSymbolFactory.Location location,
							 FunctionCallExprNode procedureCall) {
		super(location);
		assert procedureCall != null;
		this.procedureCall = procedureCall;
	}

	@Override
	public void prettyPrint(SExpPrinter printer) {
		this.procedureCall.prettyPrint(printer);
	}

}
