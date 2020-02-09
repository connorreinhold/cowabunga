package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class ArrayTypeNode implements TypeNode {
    final TypeNode t;
    
    public ArrayTypeNode(TypeNode t) {
        this.t = t;
    }

    @Override
    public int getDimensions() {
        return 1 + t.getDimensions();
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("[]");
        t.prettyPrint(printer);
        printer.endList();
    }
    
}