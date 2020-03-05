package cyr7.ir.nodes;

import java_cup.runtime.ComplexSymbolFactory;

/**
 * An intermediate representation for statements
 */
public abstract class IRStmt extends IRNode_c {
    public IRStmt(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
