package cyr7.ast.expr;

import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class IdExprNode extends ExprNode {

    final String identifier;

    public IdExprNode(String id) {
        assert id != null;

        this.identifier = id;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.printAtom(identifier);
    }

}
