package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * An interface representing every node in our AST
 */
public interface INode {
	/**
	 * @param [printer] - the printer object to output s-expressions to
	 */
    void prettyPrint(SExpPrinter printer);
}
