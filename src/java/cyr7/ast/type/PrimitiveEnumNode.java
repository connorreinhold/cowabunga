package cyr7.ast.type;

import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Represents an XI primitive type
 */
public enum PrimitiveEnumNode implements ITypeExprNode {
    INT, BOOL;

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(toString().toLowerCase());
    }
}
