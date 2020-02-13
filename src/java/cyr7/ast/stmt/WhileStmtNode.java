package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Represents a while statement, with [ExprNode guard] and a [block] representing the body of the while 
 * loop
 */
public class WhileStmtNode extends StmtNode{
	final ExprNode guard;
    final StmtNode block;
	
    public WhileStmtNode(ExprNode guard, StmtNode block) {
    	this.guard = guard;
    	this.block = block;
    }

	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.startList();
		printer.printAtom("while");
		guard.prettyPrint(printer);
		block.prettyPrint(printer);
		printer.endList();
	}
}
