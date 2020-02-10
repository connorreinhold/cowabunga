package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class ArrayAccessNode extends ExprNode{

    final String identifier;
    final ExprNode index;

    public ArrayAccessNode(String id, ExprNode index){
        this.identifier = id;
        this.index = index;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("[]");
        printer.printAtom(identifier);
        index.prettyPrint(printer);

        printer.endList();
    }
}
