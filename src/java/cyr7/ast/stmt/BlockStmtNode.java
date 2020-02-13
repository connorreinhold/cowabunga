package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.LinkedList;

public final class BlockStmtNode extends AbstractNode implements StmtNode {
    final LinkedList<StmtNode> statements;

    public BlockStmtNode(ComplexSymbolFactory.Location location, LinkedList<StmtNode> statements) {
        super(location);
        assert statements != null;

        this.statements = statements;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        for (StmtNode statement: statements) {
            statement.prettyPrint(printer);
        }

        printer.endList();
    }
}
