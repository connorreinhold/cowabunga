package cyr7.ast;

import java.util.List;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class IxiProgramNode extends ProgramNode implements NodeInterface {
    final List<FunctionDeclNode> functionDeclarations;

    public IxiProgramNode(List<FunctionDeclNode> lst) {
        this.functionDeclarations = lst;
    }

    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom("");
        printer.startUnifiedList();
        this.functionDeclarations.forEach(f -> {
            f.prettyPrint(printer);
        });
        printer.endList();
        printer.printAtom("");
        printer.endList();
    }

}
