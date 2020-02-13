package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Optional;

public class IfElseStmtNode extends AbstractNode implements StmtNode {

	final ExprNode guard;
    final StmtNode ifBlock;
    final Optional<StmtNode> elseBlock;
	
    public IfElseStmtNode(ComplexSymbolFactory.Location location, ExprNode guard, StmtNode ifBlock, Optional<StmtNode> elseBlock) {
    	super(location);

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
