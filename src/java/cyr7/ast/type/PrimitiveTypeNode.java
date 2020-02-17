package cyr7.ast.type;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.semantics.Context;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.Type;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public class PrimitiveTypeNode extends AbstractNode implements ITypeExprNode {

    public final PrimitiveEnum type;

    public PrimitiveTypeNode(ComplexSymbolFactory.Location location, PrimitiveEnum type) {
        super(location);

        this.type = type;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(type.toString().toLowerCase());
    }
    
    public boolean equals(Object o) {
        if (o instanceof PrimitiveTypeNode) {
            PrimitiveTypeNode oNode = (PrimitiveTypeNode)o;
            return this.type.equals(oNode.type);
        }
        return false;
    }
}
