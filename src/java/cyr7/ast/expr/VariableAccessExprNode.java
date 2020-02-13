package cyr7.ast.expr;

import edu.cornell.cs.cs4120.util.SExpPrinter;

/**
 * Represents accessing a specific variable as an expresion. Ex: a:int = b + 2
 * b in this case would be a VariableAccessExprNode
 */
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
