package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents a single [use] statement at the top of XI files. Contains the name of the interface 
 * that the XI file references
 */
public class UseNode extends AbstractNode {
    final String interfaceName;

    public UseNode(ComplexSymbolFactory.Location location, String interfaceName) {
        super(location);
        this.interfaceName = interfaceName;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("use");
        printer.printAtom(interfaceName);
        printer.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UseNode) {
            UseNode oNode = (UseNode) o;
            return this.interfaceName.equals(oNode.interfaceName);
        }
        return false;
    }
    
}
