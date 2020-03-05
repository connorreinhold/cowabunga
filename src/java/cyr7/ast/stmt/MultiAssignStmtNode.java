package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A statement of the form
 * v1:t1, ..., vn:tn = f(e)
 *
 * <p>
 * It is guaranteed that the number of variable declarations is at least 2.
 */
public final class MultiAssignStmtNode extends AbstractNode implements StmtNode {

    /**
     * An empty element corresponds to a wildcard declaration.
     */
    public final List<Optional<VarDeclNode>> varDecls;

    public final FunctionCallExprNode initializer;

    public MultiAssignStmtNode(Location location,
                               List<Optional<VarDeclNode>> varDecls,
                               FunctionCallExprNode initializer) {
        super(location);

        assert varDecls != null;
        assert initializer != null;
        assert varDecls.size() >= 2;

        this.varDecls = Util.immutableCopy(varDecls);
        this.initializer = initializer;
    }

    @Override
    public List<Node> getChildren() {
        return Stream.concat(
            varDecls.stream().filter(Optional::isPresent).map(Optional::get),
            List.of(initializer).stream())
            .collect(Collectors.toList());
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiAssignStmtNode that = (MultiAssignStmtNode) o;
        return varDecls.equals(that.varDecls) &&
            initializer.equals(that.initializer);
    }

}
