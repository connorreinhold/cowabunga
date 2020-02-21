package cyr7.semantics;

import cyr7.ast.ArrayAccessNode;
import cyr7.ast.VarDeclNode;
import cyr7.ast.VariableAccessNode;
import cyr7.ast.expr.ArrayExprNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.binexpr.*;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.stmt.*;
import cyr7.ast.toplevel.*;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.ExpandedType.Type;
import cyr7.util.OneOfTwo;
import cyr7.visitor.AbstractVisitor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TypeCheckVisitor extends
    AbstractVisitor<OneOfTwo<ExpandedType, ResultType>> {

    /**
     * Returns true iff left <= right where (<=) is the subtype relation.
     * <p>
     * Note that the subtype relation (and therefore this function) is not
     * antisymmetric, i.e. it may be the case that lhs <= rhs is false AND
     * rhs <= lhs is false.
     * <p>
     * However, the subtype relation is reflexive, meaning
     * lhs <= rhs and rhs <= lhs implies lhs == rhs.
     *
     * @param left  the lhs type
     * @param right the rhs type
     * @return true iff lhs <= rhs
     */
    private static boolean isSubtype(ExpandedType left, ExpandedType right) {
        // Warning: the order of comparisons in this method is delicate and
        // a lot of thought was put into this. Please be careful editing this
        // method.

        if (left.getType() == ExpandedType.Type.TUPLE) {
            // We can only compare tuple types to other tuple types
            if (right.getType() != ExpandedType.Type.TUPLE) {
                return false;
            }

            TupleType tup1 = (TupleType) left;
            TupleType tup2 = (TupleType) right;
            if (tup1.elements.size() != tup2.elements.size()) {
                return false;
            }

            // Ensure all types of the tuple are compatible
            for (int i = 0; i < tup1.elements.size(); i++) {
                if (!isSubtype(tup1.elements.get(i), tup2.elements.get(i))) {
                    return false;
                }
            }

            return true;
        }

        assert left.getType() != Type.TUPLE && right.getType() != Type.TUPLE;

        if (right.getType() == Type.UNIT) {
            return true;
        }
        if (left.getType() == Type.UNIT) {
            return false;
        }

        assert left.getType() != Type.UNIT && right.getType() != Type.UNIT;

        if (left.getType() == Type.VOID) {
            return true;
        }
        if (right.getType() == Type.VOID) {
            return false;
        }

        assert left.getType() != Type.VOID && right.getType() != Type.VOID;

        assert left.getType() == Type.PRIMITIVE || left.getType() == Type.ARRAY;
        assert right.getType() == Type.PRIMITIVE || right.getType() == Type.ARRAY;

        if (left.getType() == Type.PRIMITIVE && right.getType() == Type.PRIMITIVE) {
            return left.equals(right);
        } else if (left.getType() == Type.ARRAY && right.getType() == Type.ARRAY) {
            ArrayType l = (ArrayType) left;
            ArrayType r = (ArrayType) right;
            return isSubtype(l.child, r.child);
        }

        return false;
    }

    private static boolean isEqual(ExpandedType left, ExpandedType right) {
        return isSubtype(left, right) && isSubtype(right, left);
    }

    private static Optional<ExpandedType> supertypeOf(ExpandedType left,
                                                      ExpandedType right) {
        if (isSubtype(left, right)) {
            return Optional.of(right);
        } else if (isSubtype(right, left)) {
            return Optional.of(left);
        } else {
            return Optional.empty();
        }
    }

    private final Context context;

    public TypeCheckVisitor(Context initialContext) {
        this.context = initialContext;
    }

    // Top Level

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(FunctionDeclNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(FunctionHeaderDeclNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(IxiProgramNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(UseNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VarDeclNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(XiProgramNode n) {
        return null;
    }

    // Type

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(PrimitiveTypeNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(TypeExprArrayNode n) {
        return null;
    }

    // Statement and Expr

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ArrayAccessNode n) {
        ExpandedType arrayType = n.index.accept(this).assertFirst();
        ExpandedType indexType = n.index.accept(this).assertFirst();

        if (arrayType.getType() == Type.ARRAY
            && isSubtype(indexType, PrimitiveType.INT)) {
            ArrayType actualArrayType = (ArrayType) arrayType;
            return OneOfTwo.ofFirst(actualArrayType.child);
        }

        throw new SemanticException();
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VariableAccessNode n) {
        Optional<OrdinaryType> optionalVar = context.getVar(n.identifier);
        if (optionalVar.isPresent()) {
            return OneOfTwo.ofFirst(optionalVar.get());
        }

        throw new SemanticException();
    }

    // Statement

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ArrayDeclStmtNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(AssignmentStmtNode n) {
        ExpandedType lhsType = n.access.accept(this).assertFirst();
        ExpandedType rhsType = n.value.accept(this).assertFirst();
        if (isSubtype(lhsType, rhsType)) {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException("Inequivalent type assignment");
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(BlockStmtNode n) {
        context.push();
        Iterator<StmtNode> stmtIterator = n.statements.iterator();
        while (stmtIterator.hasNext()) {
            StmtNode stmt = stmtIterator.next();
            ResultType type = stmt.accept(this).assertSecond();
            if (stmtIterator.hasNext() && type == ResultType.VOID) {
                throw new SemanticException("Early void statement");
            } else {
                context.pop();
                return OneOfTwo.ofSecond(type);
            }
        }
        context.pop();
        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ExprStmtNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(IfElseStmtNode n) {
        context.push();
        ExpandedType guardType = n.guard.accept(this).assertFirst();
        context.pop();

        if (!isSubtype(guardType, PrimitiveType.BOOL)) {
            throw new SemanticException(
                    "Guard expression does not evaluate to bool");
        }

        context.push();
        ResultType ifType = n.ifBlock.accept(this).assertSecond();
        context.pop();

        if (n.elseBlock.isPresent()) {
            context.push();
            ResultType elseType = n.elseBlock.get().accept(this).assertSecond();
            context.pop();
            return OneOfTwo.ofSecond(ResultType.leastUpperBound(ifType, elseType));
        } else {
            return OneOfTwo.ofSecond(ifType);
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(MultiAssignStmtNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ProcedureStmtNode n) {
        ExpandedType type = n.procedureCall.accept(this).assertFirst();
        if (isEqual(type, UnitType.UNIT)) {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException("Expected a procedure but found a "
                    + "function instead.");
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ReturnStmtNode n) {
        int numOfValues = n.exprs.size();
        Optional<ExpandedType> maybeTypes = context.getRet();
        assert(maybeTypes.isPresent());
        ExpandedType expected = maybeTypes.get();
        ExpandedType exprType;

        switch (numOfValues) {
        case 0:
            if (expected.equals(UnitType.UNIT)) {
                return OneOfTwo.ofSecond(ResultType.VOID);
            }
            break;
        case 1:
            exprType = n.exprs.get(0).accept(this).assertFirst();
            if (expected.equals(exprType)) {
                return OneOfTwo.ofSecond(ResultType.VOID);
            }
            break;
        default:
            exprType = new TupleType(
                n.exprs.stream()
                    .map(e -> e.accept(this).assertFirst())
                    .collect(Collectors.toList()));
            if (isSubtype(exprType, expected)) {
                return OneOfTwo.ofSecond(ResultType.VOID);
            }
            break;
        }
        throw new SemanticException("Return types do not match the function's "
                + "expected return types");
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VarDeclStmtNode n) {
        if (context.contains(n.varDecl.identifier)) {
            throw new SemanticException("Duplicate variable " + n.varDecl.identifier);
        }
        ExpandedType type = n.varDecl.typeExpr.accept(this).assertFirst();
        if (type.isOrdinary()) {
            context.addVar(n.varDecl.identifier, (OrdinaryType) type);
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException();
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VarInitStmtNode n) {
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(WhileStmtNode n) {
        ExpandedType guardType = n.guard.accept(this).assertFirst();
        if (!isSubtype(guardType, PrimitiveType.BOOL)){
            throw new SemanticException("Guard expression does "
                    + "not evaluate to bool");
        }

        context.push();
        n.block.accept(this);
        context.pop();

        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    // Expression

    private PrimitiveType typecheckIntegerBinExpr(BinExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (isSubtype(left, PrimitiveType.INT)
            && isSubtype(right, PrimitiveType.INT)) {
            return PrimitiveType.INT;
        }

        throw new SemanticException();
    }

    private PrimitiveType typecheckBooleanBinExpr(BinExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (isSubtype(left, PrimitiveType.BOOL)
            && isSubtype(right, PrimitiveType.BOOL)) {
            return PrimitiveType.BOOL;
        }

        throw new SemanticException();
    }

    private PrimitiveType typecheckComparisonBinExpr(BinExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (isSubtype(left, PrimitiveType.INT)
            && isSubtype(right, PrimitiveType.INT)) {
            return PrimitiveType.BOOL;
        }

        throw new SemanticException();
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ArrayExprNode n) {
        if (n.arrayVals.size() == 0) {
            return OneOfTwo.ofFirst(new ArrayType(VoidType.VOID));
        }

        // arrayType is the supertype of the first 0...i array values
        ExpandedType arrayType = n.arrayVals.get(0).accept(this).assertFirst();

        for (int i = 1; i < n.arrayVals.size(); i++) {
            ExpandedType valueType = n.arrayVals.get(i).accept(this).assertFirst();
            Optional<ExpandedType> possibleSupertype = supertypeOf(arrayType, valueType);

            if (possibleSupertype.isEmpty()) {
                // type mismatch within array
                throw new SemanticException();
            }

            arrayType = possibleSupertype.get();
        }

        return OneOfTwo.ofFirst(arrayType);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(FunctionCallExprNode n) {
        Optional<FunctionType> optionalFn = context.getFn(n.identifier);
        if (optionalFn.isEmpty()) {
            throw new SemanticException();
        }

        FunctionType function = optionalFn.get();

        if (function.input.getType() == Type.UNIT) {
            // Function requires no arguments
            if (n.parameters.isEmpty()) {
                return OneOfTwo.ofFirst(function.output);
            } else {
                throw new SemanticException();
            }

        } else if (function.input.isOrdinary()) {
            // Function requires one argument
            if (n.parameters.size() != 1) {
                throw new SemanticException();
            }

            ExpandedType parameterType = n.parameters.get(0).accept(this).assertFirst();
            if (isSubtype(parameterType, function.input)) {
                return OneOfTwo.ofFirst(function.output);
            } else {
                throw new SemanticException();
            }

        } else if (function.input.getType() == Type.TUPLE) {
            // Function requires multiple arguments
            TupleType expected = (TupleType) function.input;
            TupleType actual = new TupleType(
                n.parameters.stream()
                    .map(e -> e.accept(this).assertFirst())
                    .collect(Collectors.toList()));

            if (isSubtype(actual, expected)) {
                return OneOfTwo.ofFirst(function.output);
            } else {
                throw new SemanticException();
            }
        }

        throw new SemanticException();
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(AddExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (isSubtype(left, PrimitiveType.INT)
            && isSubtype(right, PrimitiveType.INT)) {
            return OneOfTwo.ofFirst(PrimitiveType.INT);
        } else if (left.getType() == Type.ARRAY
            && right.getType() == Type.ARRAY) {
            Optional<ExpandedType> supertype = supertypeOf(left, right);
            if (supertype.isPresent()) {
                return OneOfTwo.ofFirst(supertype.get());
            }
        }

        throw new SemanticException();
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(AndExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (isSubtype(left, PrimitiveType.BOOL)
            && isSubtype(right, PrimitiveType.BOOL)) {
            return OneOfTwo.ofFirst(PrimitiveType.BOOL);
        }

        throw new SemanticException();
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(DivExprNode n) {
        return OneOfTwo.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(EqualsExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (left.isOrdinary()
            && right.isOrdinary()
            && supertypeOf(left, right).isPresent()) {
            return OneOfTwo.ofFirst(PrimitiveType.BOOL);
        }

        throw new SemanticException();
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(GTEExprNode n) {
        return OneOfTwo.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(GTExprNode n) {
        return OneOfTwo.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(HighMultExprNode n) {
        return OneOfTwo.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LTEExprNode n) {
        return OneOfTwo.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LTExprNode n) {
        return OneOfTwo.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(MultExprNode n) {
        return OneOfTwo.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(NotEqualsExprNode n) {
        return OneOfTwo.ofFirst(typecheckBooleanBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(OrExprNode n) {
        return OneOfTwo.ofFirst(typecheckBooleanBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(RemExprNode n) {
        return OneOfTwo.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(SubExprNode n) {
        return OneOfTwo.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LiteralBoolExprNode n) {
        return OneOfTwo.ofFirst(PrimitiveType.BOOL);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LiteralCharExprNode n) {
        return OneOfTwo.ofFirst(PrimitiveType.INT);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LiteralIntExprNode n) {
        return OneOfTwo.ofFirst(PrimitiveType.INT);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LiteralStringExprNode n) {
        return OneOfTwo.ofFirst(new ArrayType(PrimitiveType.INT));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(BoolNegExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (isSubtype(type, PrimitiveType.BOOL)) {
            return OneOfTwo.ofFirst(PrimitiveType.BOOL);
        }

        throw new SemanticException();
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(IntNegExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (isSubtype(type, PrimitiveType.INT)) {
            return OneOfTwo.ofFirst(PrimitiveType.INT);
        }

        throw new SemanticException();
    }
}
