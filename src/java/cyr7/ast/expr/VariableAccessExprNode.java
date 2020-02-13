package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class VariableAccessExprNode extends ExprAccessNode {

    final String identifier;

    public VariableAccessExprNode(ComplexSymbolFactory.Location location, String id) {
        super(location);
        assert id != null;
        this.identifier = id;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(identifier);
    }

}
