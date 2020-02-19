package cyr7.ast.stmt;

import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ResultType;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Objects;

/**
 * A statement of the form
 * a: int[e1]...[en][]...[]
 */
public final class ArrayDeclStmtNode extends StmtNode {

    public final String identifier;

    public final TypeExprArrayNode type;

    public ArrayDeclStmtNode(Location location, String identifier, TypeExprArrayNode type) {
        super(location);
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public ResultType typeCheck(Context c) throws SemanticException, UnbalancedPushPopException {
        // TODO:
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayDeclStmtNode that = (ArrayDeclStmtNode) o;
        return identifier.equals(that.identifier) &&
            type.equals(that.type);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom(identifier);
        type.prettyPrint(printer);
        printer.endList();
    }

}
