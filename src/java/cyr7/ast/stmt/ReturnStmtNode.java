package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.List;

/**
 * Represents a return statement in a block, containing a potentially empty list [exprs] of ExprNodes
 */
public final class ReturnStmtNode extends AbstractNode implements StmtNode {

    public final List<ExprNode> exprs;

    public ReturnStmtNode(Location location, List<ExprNode> exprs) {
        super(location);
        assert exprs != null;

        this.exprs = Util.immutableCopy(exprs);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof ReturnStmtNode) {
            ReturnStmtNode oNode = (ReturnStmtNode)o;
            return this.exprs.equals(oNode.exprs);
        }
        return false;
    }

}
