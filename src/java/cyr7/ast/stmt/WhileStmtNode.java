package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ResultType;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a while statement, with [ExprNode guard] and a [block] representing the body of the while 
 * loop
 */
public final class WhileStmtNode extends StmtNode {

	public final ExprNode guard;
    public final StmtNode block;
	
    public WhileStmtNode(ComplexSymbolFactory.Location location, ExprNode guard, StmtNode block) {
        super(location);
        
        assert guard != null;
        assert block != null;
        this.guard = guard;
        this.block = block;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof WhileStmtNode) {
            WhileStmtNode oNode = (WhileStmtNode)o;
            return this.guard.equals(oNode.guard)
                    && this.block.equals(oNode.block);
        }
        return false;
    }

    @Override
    public ResultType typeCheck(Context c) throws SemanticException,
            UnbalancedPushPopException {
        var guardType = guard.typeCheck(c);
        if (!TypeCheckUtil.checkTypeEquality(guardType, PrimitiveType.BOOL)) {
            throw new SemanticException("Guard expression does "
                    + "not evaluate to bool");
        }
        
        c.push();
        block.typeCheck(c);
        c.pop();
        
        return ResultType.UNIT;
    }

}
