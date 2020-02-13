package cyr7.ast.stmt;

import java.util.LinkedList;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class WhileStmtNode extends AbstractNode implements StmtNode {

	final ExprNode guard;
    final BlockStmtNode block;
	
    public WhileStmtNode(ComplexSymbolFactory.Location location, ExprNode guard, BlockStmtNode block) {
    	super(location);

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
