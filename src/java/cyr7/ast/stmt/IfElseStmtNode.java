package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.Optional;

public class IfElseStmtNode extends StmtNode{
	final ExprNode guard;
    final StmtNode ifBlock;
    final Optional<StmtNode> elseBlock;
	
    public IfElseStmtNode(ExprNode guard, StmtNode ifBlock, Optional<StmtNode> elseBlock) {
    	this.guard = guard;
    	this.ifBlock = ifBlock;
    	this.elseBlock = elseBlock;
    }

	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.startList();
		
		printer.printAtom("if");
		
		guard.prettyPrint(printer);
		
		ifBlock.prettyPrint(printer);

		elseBlock.ifPresent(stmtNode -> stmtNode.prettyPrint(printer));

		printer.endList();
	}
}
