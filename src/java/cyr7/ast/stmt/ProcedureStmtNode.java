package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a call to a procedure [procedureCall], which is a 
 * FunctionCallExprNode with return type unit.
 * <p>
 * Example:
 */
public final class ProcedureStmtNode extends AbstractNode implements StmtNode {
    
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

}
