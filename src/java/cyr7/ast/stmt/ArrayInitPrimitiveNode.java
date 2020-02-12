package cyr7.ast.stmt;

import cyr7.ast.type.PrimitiveTypeNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class ArrayInitPrimitiveNode extends ArrayInitNode {

    final PrimitiveTypeNode primitive;

    public ArrayInitPrimitiveNode(PrimitiveTypeNode primitive) {
        this.primitive = primitive;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        primitive.prettyPrint(printer);
    }

}
