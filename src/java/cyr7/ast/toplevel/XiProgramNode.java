package cyr7.ast.toplevel;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents an XI program, containing a list of use statements and a list
 * of functions
 */
public final class XiProgramNode extends AbstractNode {

    public final List<UseNode> uses;
    public final List<FunctionDeclNode> functions;

    public XiProgramNode(ComplexSymbolFactory.Location location,
                         List<UseNode> uses, List<FunctionDeclNode> functions) {
        super(location);

        assert uses != null;
        assert functions != null;

        this.uses = Util.immutableCopy(uses);
        this.functions = Util.immutableCopy(functions);
    }

    @Override
    public List<Node> getChildren() {
        return Stream.concat(uses.stream(), functions.stream()).collect(Collectors.toList());
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof XiProgramNode) {
            XiProgramNode oNode = (XiProgramNode) o;
            return this.functions.equals(oNode.functions)
                && this.uses.equals(oNode.uses);
        }
        return false;
    }


}
