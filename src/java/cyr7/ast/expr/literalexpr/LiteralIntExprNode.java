package cyr7.ast.expr.literalexpr;

import java.util.Optional;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents an integer literal, Ex: [contents] = "20"
 */
public class LiteralIntExprNode extends ExprNode {

    final String contents;
    final static String MAX = "9223372036854775808";

    public LiteralIntExprNode(ComplexSymbolFactory.Location location, 
            String contents) {
        // By design of the lexer, the magnitude of the number cannot be 
        // greater than 2^63
        super(location, contents.equals(MAX) ? 
                 Optional.of(false)
                : Optional.empty());
        this.contents = contents;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(contents);
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof LiteralIntExprNode) {
            LiteralIntExprNode oNode = (LiteralIntExprNode) o;
            return this.contents.equals(oNode.contents);
        }
        return false;    
    }
    
}
