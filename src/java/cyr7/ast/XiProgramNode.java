package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.LinkedList;

public class XiProgramNode extends ProgramNode {
    final LinkedList<UseNode> uses;
    final LinkedList<FunctionDeclNode> functions;

    public XiProgramNode(LinkedList<UseNode> uses, LinkedList<FunctionDeclNode> functions) {
        this.uses = uses;
        this.functions = functions;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        for (UseNode use : uses) {
            use.prettyPrint(printer);
        }

        for (FunctionDeclNode functionDeclNode : functions) {
            functionDeclNode.prettyPrint(printer);
        }

        printer.endList();
    }

}
