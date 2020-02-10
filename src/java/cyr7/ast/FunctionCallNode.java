package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.LinkedList;

public class FunctionCallNode extends ExprNode {

    final String identifier;
    final LinkedList<ExprNode> parameters;

    public FunctionCallNode(String id, LinkedList<ExprNode> parameters) {
        this.identifier = id;
        this.parameters = parameters;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom(identifier);

        for (ExprNode node : parameters) {
            node.prettyPrint(printer);
        }

        printer.endList();
    }
}
