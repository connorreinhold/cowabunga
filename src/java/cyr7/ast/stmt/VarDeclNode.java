package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.INode;
import cyr7.ast.type.ITypeExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a Variable Declaration, with a String [identifier] and a type [typeExpr] of the initialized
 * variable
 */
public class VarDeclNode extends AbstractNode {

    public final String identifier;

    public final ITypeExprNode typeExpr;

    public VarDeclNode(ComplexSymbolFactory.Location location, String identifier, ITypeExprNode typeExpr) {
        super(location);

        this.identifier = identifier;
        this.typeExpr = typeExpr;
    }
    
    public boolean equals(Object o) {
        if (o instanceof VarDeclNode) {
            VarDeclNode oNode = (VarDeclNode)o;
            return this.identifier.equals(oNode.identifier)
                    && this.typeExpr.equals(oNode.typeExpr);
        }
        return false;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom(identifier);
        typeExpr.prettyPrint(printer);
        printer.endList();
    }

}
