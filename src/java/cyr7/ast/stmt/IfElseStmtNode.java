package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Optional;

/**
 * Represents an if/else statement. Contains [ExprNode guard], a body [ifBlock], and an optional [elseBlock]
 */
public class IfElseStmtNode extends AbstractNode implements StmtNode {
	final ExprNode guard;
    final StmtNode ifBlock;
    final Optional<StmtNode> elseBlock;
	
    public IfElseStmtNode(ComplexSymbolFactory.Location location, ExprNode guard, StmtNode ifBlock, Optional<StmtNode> elseBlock) {
    	super(location);

    	assert guard != null;
    	assert ifBlock != null;
    	assert elseBlock != null;

    	this.guard = guard;
    	this.ifBlock = ifBlock;
    	this.elseBlock = elseBlock;
    }
    
    public boolean equals(Object o) {
        if (o instanceof IfElseStmtNode) {
            IfElseStmtNode oNode = (IfElseStmtNode)o;
            return this.guard.equals(oNode.guard)
                    && this.ifBlock.equals(oNode.ifBlock)
                    && this.elseBlock.equals(oNode.elseBlock);
        }
        return false;
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
