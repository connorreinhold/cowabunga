package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ResultType;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Objects;

/**
 * A statement of the form
 * a: int[e1]...[en][]...[]
 */
public final class ArrayDeclStmtNode extends AbstractNode implements StmtNode {

    public final String identifier;

    public final TypeExprArrayNode type;

    public ArrayDeclStmtNode(Location location, String identifier, TypeExprArrayNode type) {
        super(location);
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayDeclStmtNode that = (ArrayDeclStmtNode) o;
        return identifier.equals(that.identifier) &&
            type.equals(that.type);
    }

}
