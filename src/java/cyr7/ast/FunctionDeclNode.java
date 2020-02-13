package cyr7.ast;

import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.VarDeclNode;
import cyr7.ast.type.ITypeExprNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

/** 
 * Represents a Function object in XI files with a [header] and function body [block]
 */
public class FunctionDeclNode extends AbstractNode {
    final FunctionHeaderDeclNode header;
    final BlockStmtNode block;

    public FunctionDeclNode(ComplexSymbolFactory.Location location, FunctionHeaderDeclNode header, BlockStmtNode block) {
        super(location);

        assert header != null;
        assert block != null;

        this.header = header;
        this.block = block;
    }

    /**
     * 
     * @return true if the function does not return a value, false otherwise
     */
    public boolean isProcedure() {
        return header.returnTypes.isEmpty();
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom(header.identifier);

        printer.startList();
        for (VarDeclNode argDecl : header.args) {
            argDecl.prettyPrint(printer);
        }
        printer.endList();

        printer.startList();
        for (ITypeExprNode typeNode : header.returnTypes) {
            typeNode.prettyPrint(printer);
        }
        printer.endList();

        block.prettyPrint(printer);

        printer.endList();
    }
}
