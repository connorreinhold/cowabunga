package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.LinkedList;

public class XiProgramNode extends AbstractNode {
    final LinkedList<UseNode> uses;
    final LinkedList<FunctionDeclNode> functions;

    public XiProgramNode(ComplexSymbolFactory.Location location, LinkedList<UseNode> uses, LinkedList<FunctionDeclNode> functions) {
        super(location);

        this.uses = uses;
        this.functions = functions;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.startUnifiedList();
        for (UseNode use : uses) {
            use.prettyPrint(printer);
        }
        printer.endList();

        printer.startUnifiedList();
        for (FunctionDeclNode functionDeclNode : functions) {
            functionDeclNode.prettyPrint(printer);
        }
        printer.endList();

        printer.endList();
    }

}
