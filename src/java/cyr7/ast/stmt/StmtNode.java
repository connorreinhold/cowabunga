package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.INode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.Type;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a Stmt in XI files; an executable piece of code
 */
public abstract class StmtNode extends AbstractNode {

    public StmtNode(ComplexSymbolFactory.Location location) {
        super(location);
    }

    public abstract Type typeCheck(Context c) throws SemanticException;

}
