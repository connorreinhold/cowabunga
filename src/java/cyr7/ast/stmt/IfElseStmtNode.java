package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class IfElseStmtNode extends StmtNode{
	final ExprNode guard;
    final StmtNode ifBlock;
    final StmtNode elseBlock;
	
    public IfElseStmtNode(ExprNode guard, StmtNode ifBlock, StmtNode elseBlock) {
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
		
		if (elseBlock != null) {
			elseBlock.prettyPrint(printer);
		}
		printer.endList();
		
	}
}
