package cyr7.ast;

import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.type.TypeNode;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class FunctionDeclNode implements NodeInterface {
    final FunctionHeaderDeclNode header;
    final BlockStmtNode block;

    public FunctionDeclNode(FunctionHeaderDeclNode header, BlockStmtNode block) {
        assert header != null;
        assert block != null;

        this.header = header;
        this.block = block;
    }

    public boolean isProcedure() {
        return header.returnTypes.isEmpty();
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom(header.identifier);

        printer.startList();
        for (FunctionArgDeclNode argDecl : header.args) {
            argDecl.prettyPrint(printer);
        }
        printer.endList();

        printer.startList();
        for (TypeNode typeNode : header.returnTypes) {
            typeNode.prettyPrint(printer);
        }
        printer.endList();

        block.prettyPrint(printer);

        printer.endList();
    }
}
