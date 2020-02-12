package cyr7.ast.stmt;

import cyr7.ast.type.PrimitiveTypeNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class SingleVarPrimitiveNode extends SingleVarInitNode {

    final PrimitiveTypeNode primitive;

    public SingleVarPrimitiveNode(PrimitiveTypeNode primitive) {
        this.primitive = primitive;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        primitive.prettyPrint(printer);
    }

}
