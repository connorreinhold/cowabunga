package cyr7.ast.stmt;

import cyr7.ast.expr.ExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.exceptions.UnbalancedPushPopException;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.ResultType;
import cyr7.semantics.TupleType;
import cyr7.semantics.UnitType;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a return statement in a block, containing a potentially empty list [exprs] of ExprNodes
 */
public final class ReturnStmtNode extends StmtNode {

    public final List<ExprNode> exprs;

    public ReturnStmtNode(Location location, List<ExprNode> exprs) {
        super(location);
        assert exprs != null;

        this.exprs = Util.immutableCopy(exprs);
    }

    @Override
    public <T> T accept(AbstractVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object o) {
        if (o instanceof ReturnStmtNode) {
            ReturnStmtNode oNode = (ReturnStmtNode)o;
            return this.exprs.equals(oNode.exprs);
        }
        return false;
    }

    @Override
    public ResultType typeCheck(Context c) throws SemanticException,
            UnbalancedPushPopException {
        int numOfValues = exprs.size();
        Optional<ExpandedType> maybeTypes = c.getRet();
        assert(maybeTypes.isPresent());
        ExpandedType expected = maybeTypes.get();
        ExpandedType exprType;
        
        switch (numOfValues) {
        case 0:
            if (expected.equals(UnitType.UNIT))
                return ResultType.VOID;
            break;
        case 1:
            exprType = exprs.get(0).typeCheck(c);
            if (expected.equals(exprType))
                return ResultType.VOID;
            break;
        default:
            List<ExpandedType> mappedType = new LinkedList<>();
            for (ExprNode e: exprs) {
                mappedType.add(e.typeCheck(c));
            }
            exprType = new TupleType(mappedType);
            if (TypeCheckUtil.checkTypeEquality(exprType, expected)) {
                return ResultType.VOID;
            }
            break;
        }
        throw new SemanticException("Return types do not match the function's "
                + "expected return types");
    }

}
