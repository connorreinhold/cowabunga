package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ResultType;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.Optional;

/**
 * A statement of the form
 * v1:t1, ..., vn:tn = f(e)
 */
public final class MultiAssignStmtNode extends AbstractNode implements StmtNode {

    public final List<Optional<VarDeclNode>> varDecls;

    public final FunctionCallExprNode initializer;

    public MultiAssignStmtNode(ComplexSymbolFactory.Location location,
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
