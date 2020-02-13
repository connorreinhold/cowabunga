package cyr7.ast.stmt;

import cyr7.ast.AbstractNode;
import cyr7.ast.INode;
import cyr7.ast.type.ITypeExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

public final class VarDeclNode extends AbstractNode {

    final String identifier;

    final ITypeExprNode typeExpr;

    public VarDeclNode(ComplexSymbolFactory.Location location, String identifier, ITypeExprNode typeExpr) {
        super(location);

        this.identifier = identifier;
        this.typeExpr = typeExpr;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();
        printer.printAtom(identifier);
        typeExpr.prettyPrint(printer);
        printer.endList();
    }

}
