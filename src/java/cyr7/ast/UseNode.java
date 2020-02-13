package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

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
}
