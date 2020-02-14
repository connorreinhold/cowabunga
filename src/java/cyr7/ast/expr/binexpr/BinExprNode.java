package cyr7.ast.expr.binexpr;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.ParserIntegerOverflowException;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Node that represents executing some binary operator on [ExprNode left] and
 * [ExprNode right]
 */
public abstract class BinExprNode extends ExprNode {

    final ExprNode left, right;

    public BinExprNode(ComplexSymbolFactory.Location location, ExprNode left,
            ExprNode right) throws ParserIntegerOverflowException  {
        super(location);
        if (!left.isASafeValue()) {        
            Location loc = left.getLocation();
            throw new ParserIntegerOverflowException( 
                    loc.getLine(), loc.getColumn());
        } else if (!right.isASafeValue()) {
            Location loc = right.getLocation();
            throw new ParserIntegerOverflowException( 
                    loc.getLine(), loc.getColumn());
        } else {
            this.left = left;
            this.right = right;
        }
    }
}
