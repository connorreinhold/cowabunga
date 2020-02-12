package cyr7.ast.stmt;

import java.util.LinkedList;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class IfStmtNode extends StmtNode{
	final ExprNode guard;
    final BlockStmtNode block;
	
    public IfStmtNode(ExprNode guard, BlockStmtNode block) {
    	this.guard = guard;
    	this.block = block;
    }

	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.startList();
		printer.printAtom("if");
		guard.prettyPrint(printer);
		block.prettyPrint(printer);
		printer.endList();
		
	}
}
