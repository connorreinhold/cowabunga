package cyr7.ast.toplevel;

import java.util.List;
import java.util.Optional;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.semantics.types.ResultType;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a Function object in XI files with a [header] and function body
 * [block]
 */
public final class FunctionDeclNode extends AbstractNode {

    public final FunctionHeaderDeclNode header;
    public final BlockStmtNode block;
    private Optional<ResultType> resultType;

    public FunctionDeclNode(ComplexSymbolFactory.Location location,
                            FunctionHeaderDeclNode header,
                            BlockStmtNode block) {
        super(location);

        assert header != null;
        assert block != null;

        this.header = header;
        this.block = block;
        this.resultType = Optional.empty();
    }

    public ResultType getResultType() {
        if (this.resultType.isPresent()) {
            return this.resultType.get();
        } else {
            throw new RuntimeException("Attempted to access result type with"
                    + "out decorating the node.");
        }
    }

    public void setType(ResultType type) {
        if (this.resultType.isEmpty()) {
            this.resultType = Optional.of(type);
        } else {
            throw new RuntimeException("Attempted to add another result type "
                    + "when a type has already been added to the node.");
        }
    }

    @Override
    public List<Node> getChildren() {
        return List.of(header, block);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FunctionDeclNode) {
            FunctionDeclNode oNode = (FunctionDeclNode) o;
            return this.header.equals(oNode.header)
                && this.block.equals(oNode.block);
        }
        return false;
    }
}
