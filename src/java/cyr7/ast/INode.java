package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public interface INode {

    ComplexSymbolFactory.Location getLocation();

    void prettyPrint(SExpPrinter printer);

}
