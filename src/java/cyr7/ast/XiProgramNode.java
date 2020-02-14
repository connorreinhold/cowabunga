package cyr7.ast;

import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents an XI program, containing a list of use statements and a list
 * of functions
 */
public class XiProgramNode extends AbstractNode implements IProgramNode {
    public final List<UseNode> uses;
    public final List<FunctionDeclNode> functions;

    public XiProgramNode(ComplexSymbolFactory.Location location,
                         List<UseNode> uses, List<FunctionDeclNode> functions) {
        super(location);

        assert uses != null;
        assert functions != null;

        this.uses = Util.immutableCopy(uses);
        this.functions = Util.immutableCopy(functions);
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof XiProgramNode) {
            XiProgramNode oNode = (XiProgramNode) o;
            return this.functions.equals(oNode.functions)
                    && this.uses.equals(oNode.uses);
        }
        return false;
    }


}
