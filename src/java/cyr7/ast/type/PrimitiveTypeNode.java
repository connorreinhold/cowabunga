package cyr7.ast.type;

import cyr7.ast.AbstractNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public class PrimitiveTypeNode extends AbstractNode implements ITypeExprNode {

    public enum Type {
        INT, BOOL;
    }

    final Type type;

    public PrimitiveTypeNode(ComplexSymbolFactory.Location location, Type type) {
        super(location);

        this.type = type;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(type.toString().toLowerCase());
    }
}
