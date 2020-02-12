package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class VariableAccessExprNode extends ExprAccessNode {

    final String identifier;

    public VariableAccessExprNode(String id) {
        assert id != null;
        this.identifier = id;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(identifier);
    }

}
