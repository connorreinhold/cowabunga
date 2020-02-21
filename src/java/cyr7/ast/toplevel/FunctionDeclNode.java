package cyr7.ast.toplevel;

import cyr7.ast.AbstractNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

/** 
 * Represents a Function object in XI files with a [header] and function body [block]
 */
public final class FunctionDeclNode extends AbstractNode {

    public final FunctionHeaderDeclNode header;
    public final BlockStmtNode block;

    public FunctionDeclNode(ComplexSymbolFactory.Location location, 
            FunctionHeaderDeclNode header, BlockStmtNode block) {
        super(location);

        assert header != null;
        assert block != null;

        this.header = header;
        this.block = block;
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
