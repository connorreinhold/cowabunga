package cyr7.ast.stmt;

import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.LinkedList;

public class BlockStmtNode extends StmtNode {
    final LinkedList<StmtNode> statements;

    public BlockStmtNode(LinkedList<StmtNode> statements) {
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
