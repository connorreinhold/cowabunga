package cyr7.ast.type;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.OrdinaryType;
import cyr7.semantics.PrimitiveType;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class PrimitiveTypeNode extends ITypeExprNode {

    public final PrimitiveEnum type;

    public PrimitiveTypeNode(Location location, PrimitiveEnum type) {
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

    @Override
    public OrdinaryType typeCheck(Context c) throws SemanticException {
        switch (type) {
        case BOOL:
            return PrimitiveType.BOOL;
        case INT:
            return PrimitiveType.INT;
        }
        throw new SemanticException("Unexpected type check");
    }
}
