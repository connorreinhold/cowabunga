package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Optional;

/**
 * An interface representing every node in our AST
 */
public interface INode {

    Optional<ComplexSymbolFactory.Location> getLocation();

	/**
	 * @param printer - the printer object to output s-expressions to
	 */
    void prettyPrint(SExpPrinter printer);
    
}
