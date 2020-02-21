package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ResultType;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a block of statements (denoted by { ... } in code)
 */
public final class BlockStmtNode extends AbstractNode implements StmtNode {

    public final List<StmtNode> statements;
    
    public BlockStmtNode(ComplexSymbolFactory.Location location,
            List<StmtNode> statements) {
        super(location);

        assert statements != null;

        this.statements = Util.immutableCopy(statements);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof BlockStmtNode) {
            BlockStmtNode oNode = (BlockStmtNode) o;
            return this.statements.equals(oNode.statements);
        }
        return false;
    }

}
