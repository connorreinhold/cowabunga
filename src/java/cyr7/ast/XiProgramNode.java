package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.util.LinkedList;

/**
 * Represents an XI program, contianing a list of use statements and a list of functions
 */
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
