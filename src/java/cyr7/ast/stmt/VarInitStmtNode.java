package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.List;
import java.util.Objects;

/**
 * A statement of the form
 * x:t = e
 */
public final class VarInitStmtNode extends AbstractNode implements StmtNode {

    public final VarDeclNode varDecl;
    public final ExprNode initializer;

    public VarInitStmtNode(Location location, VarDeclNode varDecl,
                           ExprNode expr) {
        super(location);

        this.varDecl = varDecl;
        this.initializer = expr;
    }

    @Override
    public List<Node> getChildren() {
        return List.of(varDecl, initializer);
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
