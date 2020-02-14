package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.LinkedList;

/**
 * Represents a block of statements (denoted by { ... } in code)
 */
public class BlockStmtNode extends AbstractNode implements StmtNode {
    final LinkedList<StmtNode> statements;

    public BlockStmtNode(ComplexSymbolFactory.Location location,
            LinkedList<StmtNode> statements) {
        super(location);
        assert statements != null;

        this.statements = statements;
    }

    public boolean equals(Object o) {
        if (o instanceof BlockStmtNode) {
            BlockStmtNode oNode = (BlockStmtNode) o;
            return this.statements.equals(oNode.statements);
        }
        return false;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        for (StmtNode statement : statements) {
            statement.prettyPrint(printer);
        }

        printer.endList();
    }
}
