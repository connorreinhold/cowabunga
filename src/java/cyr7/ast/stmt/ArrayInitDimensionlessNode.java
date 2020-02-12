package cyr7.ast.stmt;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class ArrayInitDimensionlessNode extends ArrayInitNode {

    final ArrayInitNode child;

    public ArrayInitDimensionlessNode(ArrayInitNode child) {
        this.child = child;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("[]");

        child.prettyPrint(printer);

        printer.endList();
    }
}
