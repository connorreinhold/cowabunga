package cyr7.ast.expr;

import cyr7.ast.AbstractNode;
import cyr7.semantics.types.ExpandedType;
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class AbstractExprNode extends AbstractNode implements ExprNode {

    private ExpandedType type;

    public AbstractExprNode(Location location) {
        super(location);
    }

    @Override
    public ExpandedType getType() {
        return type;
    }

    @Override
    public void setType(ExpandedType t) {
        this.type = t;
    }

}
