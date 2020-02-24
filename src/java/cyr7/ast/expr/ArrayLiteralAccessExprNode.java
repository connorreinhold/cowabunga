package cyr7.ast.expr;

import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class ArrayLiteralAccessExprNode extends ArrayExprNode {

    public final ArrayExprNode child;
    public final ExprNode index;
    
    public ArrayLiteralAccessExprNode(Location location, ArrayExprNode child, 
            ExprNode index) {
        super(location);
        this.child = child;
        this.index = index;
    }
    
    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayLiteralAccessExprNode) {
            ArrayLiteralAccessExprNode oNode = (ArrayLiteralAccessExprNode)o;
            return this.child.equals(oNode.child) 
                    && this.index.equals(oNode.index);
        }
        return false;
    }

}
