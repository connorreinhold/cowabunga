package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Represents a single [use] statement at the top of XI files. Contains the name of the interface 
 * that the XI file references
 */
public class UseNode implements INode {
    final String interfaceName;

    public UseNode(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("use");
        printer.printAtom(interfaceName);
        printer.endList();
    }
}
