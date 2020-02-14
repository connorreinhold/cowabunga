package cyr7.ast.expr;

import java.util.Optional;

import cyr7.ast.AbstractNode;
import cyr7.ast.INode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a generic expression: Ex: 1+1, true, arr[2][3]
 */
public abstract class ExprNode extends AbstractNode {

    /**
     * The value stored in this expression is safe if empty or true. 
     * Not safe if false.
     */
    protected Optional<Boolean> isASafeValue;
    
    public ExprNode(ComplexSymbolFactory.Location location) {
        super(location);
        this.isASafeValue = Optional.empty();
    }
    
    public ExprNode(ComplexSymbolFactory.Location location, 
        Optional<Boolean> isSafe) {
        super(location);
        this.isASafeValue = isSafe;
    }
    
    public boolean isASafeValue() {
        return this.isASafeValue.isEmpty() || this.isASafeValue.get();
    }
    
    public Optional<Boolean> negateSafety() {
        if (this.isASafeValue.isPresent()) {
            return Optional.of(!this.isASafeValue.get());
        }
        return this.isASafeValue;
    }
    
}
