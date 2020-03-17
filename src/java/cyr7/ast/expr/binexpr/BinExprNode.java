package cyr7.ast.expr.binexpr;

import java.util.List;
import java.util.Objects;

import cyr7.ast.Node;
import cyr7.ast.expr.AbstractExprNode;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Node that represents executing some binary operator on [ExprNode left] and
 * [ExprNode right]
 */
public class BinExprNode extends AbstractExprNode {

    public enum ASTOpType {
        ADD, SUB, MUL, HIGH_MUL, DIV, MOD, EQ, NEQ, LT, GT, LEQ, GEQ, AND, OR
    }

    public final ExprNode left, right;
    public final ASTOpType opType;

    public BinExprNode(Location location, ASTOpType opType, ExprNode left,
            ExprNode right) {
        super(location);

        assert opType != null;
        assert left != null;
        assert right != null;

        this.opType = opType;
        this.left = left;
        this.right = right;
    }

    @Override
    public List<Node> getChildren() {
        return List.of(left, right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, opType, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BinExprNode)) {
            return false;
        }
        BinExprNode other = (BinExprNode) obj;
        return Objects.equals(left, other.left) && opType == other.opType
                && Objects.equals(right, other.right);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
