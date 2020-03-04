package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.List;

/**
 * A statement of the form
 * _ = e
 */
public final class ExprStmtNode extends AbstractNode implements StmtNode {

    public final ExprNode expr;

    public ExprStmtNode(Location location, ExprNode expr) {
        super(location);

        this.expr = expr;
    }

    @Override
    public List<Node> getChildren() {
        return List.of(expr);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

}
