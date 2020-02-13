package cyr7.ast.type;

import cyr7.ast.AbstractNode;
import cyr7.ast.expr.ExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class TypeExprArrayNode extends AbstractNode implements ITypeExprNode {

    final ITypeExprNode child;

    final Optional<ExprNode> size;

    public TypeExprArrayNode(ComplexSymbolFactory.Location location, ITypeExprNode child, Optional<ExprNode> size) {
        super(location);

        this.child = child;
        this.size = size;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("[]");

        child.prettyPrint(printer);

        size.ifPresent(exprNode -> exprNode.prettyPrint(printer));

        printer.endList();
    }

}
