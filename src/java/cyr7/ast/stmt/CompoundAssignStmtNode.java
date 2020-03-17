package cyr7.ast.stmt;

import java.util.List;
import java.util.Objects;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.ast.expr.ExprNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CompoundAssignStmtNode extends AbstractNode implements StmtNode {

    public enum CompoundOpType {
        PLUS, MINUS, MULT, HIGH_MULT, DIV, REM, OR, AND
    }

    public final CompoundOpType opType;
    public final ExprNode lhs;
    public final ExprNode rhs;

    public CompoundAssignStmtNode(Location location, CompoundOpType type,
            ExprNode lhs, ExprNode rhs) {
        super(location);
        this.opType = type;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return this.accept(visitor);
    }

    @Override
    public List<Node> getChildren() {
        return List.of(lhs, rhs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lhs, opType, rhs);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompoundAssignStmtNode)) {
            return false;
        }
        CompoundAssignStmtNode other = (CompoundAssignStmtNode) obj;
        return Objects.equals(lhs, other.lhs) && opType == other.opType
                && Objects.equals(rhs, other.rhs);
    }

}
