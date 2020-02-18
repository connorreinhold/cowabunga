package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ResultType;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Optional;

/**
 * Represents an if/else statement. Contains [ExprNode guard], a body [ifBlock],
 * and an optional [elseBlock]
 */
public class IfElseStmtNode extends StmtNode {

    final ExprNode guard;
    final StmtNode ifBlock;
    final Optional<StmtNode> elseBlock;

    public IfElseStmtNode(ComplexSymbolFactory.Location location,
            ExprNode guard, StmtNode ifBlock, Optional<StmtNode> elseBlock) {
        super(location);

        assert guard != null;
        assert ifBlock != null;
        assert elseBlock != null;

        this.guard = guard;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    public boolean equals(Object o) {
        if (o instanceof IfElseStmtNode) {
            IfElseStmtNode oNode = (IfElseStmtNode) o;
            return this.guard.equals(oNode.guard) && this.ifBlock.equals(
                    oNode.ifBlock) && this.elseBlock.equals(oNode.elseBlock);
        }
        return false;
    }

    @Override
    public void prettyPrint(SExpPrinter printer) {
        printer.startList();

        printer.printAtom("if");

        guard.prettyPrint(printer);

        ifBlock.prettyPrint(printer);

        elseBlock.ifPresent(stmtNode -> stmtNode.prettyPrint(printer));

        printer.endList();
    }

    @Override
    public ResultType typeCheck(Context c) throws SemanticException,
            UnbalancedPushPopException {
        c.push();
        ExpandedType guardType = guard.typeCheck(c);
        c.pop();
        if (!guardType.equals(PrimitiveType.BOOL)) {
            throw new SemanticException(
                    "Guard statement does not evaluate to bool.");
        } else {
            c.push();
            ResultType ifType = ifBlock.typeCheck(c);
            c.pop();

            if (elseBlock.isPresent()) {
                c.push();
                ResultType elseType = elseBlock.get()
                                               .typeCheck(c);
                c.pop();
                return ResultType.leastUpperBound(ifType, elseType);
            } else {
                return ifType;
            }
        }
    }

}
