package cyr7.ast;

import java.util.LinkedList;
import java.util.List;

import cyr7.ast.stmt.VarDeclNode;
import cyr7.ast.type.ITypeExprNode;
import cyr7.util.Util;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Represents an IXI function declaration with an identifier, a list of
 * function arguments,
 * and a list representing the type(s) the function returns
 */
public class FunctionHeaderDeclNode extends AbstractNode {

    final String identifier;
    final List<VarDeclNode> args;
    final List<ITypeExprNode> returnTypes;

    public FunctionHeaderDeclNode(ComplexSymbolFactory.Location location,
                                  String id,
                                  List<VarDeclNode> args,
                                  List<ITypeExprNode> returnTypes) {
        super(location);

        assert id != null;
        assert args != null;
        assert returnTypes != null;

        this.identifier = id;
        this.args = Util.immutableCopy(args);
        this.returnTypes = Util.immutableCopy(returnTypes);
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startUnifiedList();

        printer.printAtom(this.identifier);

        printer.startList();
        args.forEach(a -> {
            a.prettyPrint(printer);
        });
        printer.endList();

        printer.startList();
        returnTypes.forEach(r -> {
            r.prettyPrint(printer);
        });
        printer.endList();
        printer.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FunctionHeaderDeclNode) {
            FunctionHeaderDeclNode oNode = (FunctionHeaderDeclNode) o;
            return this.identifier.equals(oNode.identifier)
                    && this.args.equals(oNode.args)
                    && this.returnTypes.equals(oNode.returnTypes);
        }
        return false;
    }
}
