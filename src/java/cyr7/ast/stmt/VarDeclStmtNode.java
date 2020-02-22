package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.VarDeclNode;
import cyr7.ast.type.TypeExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.OrdinaryType;
import cyr7.semantics.ResultType;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Objects;

/**
 * A statement of the form
 * x:t
 */
public final class VarDeclStmtNode extends AbstractNode implements StmtNode {

    public final VarDeclNode varDecl;

    public VarDeclStmtNode(Location location, VarDeclNode varDecl) {
        super(location);

        this.varDecl = varDecl;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VarDeclStmtNode that = (VarDeclStmtNode) o;
        return Objects.equals(varDecl, that.varDecl);
    }

}
