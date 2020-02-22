package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.ExprNode;
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
 * x:t = e
 */
public final class VarInitStmtNode extends AbstractNode implements StmtNode {

    public final VarDeclNode varDecl;
    public final ExprNode initializer;

    public VarInitStmtNode(Location location, VarDeclNode varDecl, ExprNode expr) {
        super(location);

        this.varDecl = varDecl;
        this.initializer = expr;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VarInitStmtNode that = (VarInitStmtNode) o;
        return Objects.equals(varDecl, that.varDecl) &&
            Objects.equals(initializer, that.initializer);
    }

}
