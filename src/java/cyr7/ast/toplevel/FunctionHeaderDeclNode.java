package cyr7.ast.toplevel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.ast.VarDeclNode;
import cyr7.ast.type.TypeExprNode;
import cyr7.semantics.types.FunctionType;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Represents an IXI function declaration with an identifier, a list of
 * function arguments,
 * and a list representing the type(s) the function returns
 */
public final class FunctionHeaderDeclNode extends AbstractNode {

    public final String identifier;
    public final List<VarDeclNode> args;
    public final List<TypeExprNode> returnTypes;

    private FunctionType type;

    public FunctionHeaderDeclNode(Location location,
                                  String id,
                                  List<VarDeclNode> args,
                                  List<TypeExprNode> returnTypes) {
        super(location);


        assert id != null;
        assert args != null;
        assert returnTypes != null;

        this.identifier = id;
        this.args = Util.immutableCopy(args);
        this.returnTypes = Util.immutableCopy(returnTypes);
    }

    @Override
    public List<Node> getChildren() {
        return Util.immutableCopy(
            Stream.concat(args.stream(), returnTypes.stream())
                .collect(Collectors.toList()));
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FunctionHeaderDeclNode) {
            FunctionHeaderDeclNode oNode = (FunctionHeaderDeclNode) o;
            return this.identifier.equals(oNode.identifier)
                && this.args.equals(oNode.args)
                && this.returnTypes.equals(oNode.returnTypes);
        }
        return false;
    }

    public FunctionType getType() {
        return type;
    }

    public void setType(FunctionType t) {
        this.type = t;
    }

}
