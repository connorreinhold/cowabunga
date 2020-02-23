package cyr7.semantics;

import cyr7.ast.ArrayAccessNode;
import cyr7.ast.VarDeclNode;
import cyr7.ast.VariableAccessNode;
import cyr7.ast.expr.ArrayExprNode;
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
import cyr7.ast.type.TypeExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.semantics.ExpandedType.Type;
import cyr7.util.OneOfTwo;
import cyr7.visitor.AbstractVisitor;

import java.util.Iterator;
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
//    private static boolean isSubtype(ExpandedType left, ExpandedType right) {
//        // Warning: the order of comparisons in this method is delicate and
//        // a lot of thought was put into this. Please be careful editing this
//        // method.
//        
//        return left.isSubtypeOf(right);
//    }

    private static Optional<ExpandedType> supertypeOf(ExpandedType left,
                                                      ExpandedType right) {
        if (left.isSubtypeOf(right)) {
            return Optional.of(right);
        } else if (right.isSubtypeOf(left)) {
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
        ResultType headerResult = n.header.accept(this).assertSecond();
        ResultType resultType = n.block.accept(this).assertSecond();
        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(FunctionHeaderDeclNode n) {
        String functionName = n.identifier;
        if (context.contains(functionName)) {
            throw new SemanticException("Duplicate name: " + functionName);
        }
        
        ExpandedType input = new ExpandedType(n.args.stream().map(v -> {
            ExpandedType t = v.accept(this).assertFirst();
            assert(t.isOrdinary());
            return t.getOrdinaryType();
        }).collect(Collectors.toList()));
        
        ExpandedType output = new ExpandedType(n.returnTypes.stream().map(v -> {
            ExpandedType t = v.accept(this).assertFirst();
            assert(t.isOrdinary());
            return t.getOrdinaryType();
        }).collect(Collectors.toList()));
        FunctionType functionType = new FunctionType(input, output);
        context.addFn(functionName, functionType);
        return OneOfTwo.ofSecond(ResultType.UNIT);
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
        if (context.contains(n.identifier)) {
            throw new SemanticException("Duplicate variable declaration");
        }
        ExpandedType type = n.typeExpr.accept(this).assertFirst();
        return OneOfTwo.ofFirst(type);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(XiProgramNode n) {
        return null;
    }

    // Type

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(PrimitiveTypeNode n) {
        ExpandedType t;
        switch (n.type) {
        case BOOL:
            t = new ExpandedType(new PrimitiveType(OrdinaryType.Type.BOOL));
            break;
        case INT:
            t = new ExpandedType(new PrimitiveType(OrdinaryType.Type.INT));
            break;
         default:
            throw new SemanticException("Found unknown primitive type");
        }
        return OneOfTwo.ofFirst(t);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(TypeExprArrayNode n) {
        ExpandedType type = n.child.accept(this).assertFirst();
        if (n.size.isPresent()) {
            ExpandedType sizeType = n.size.get().accept(this).assertFirst();
            if (!sizeType.isSubtypeOfInt()) {
                throw new SemanticException("Size of array is not an integer");
            }
        }
        
        if (!type.isOrdinary()) {
            throw new SemanticException("Expected an array of primitives or"
                    + " of arrays, but found an array of tuples");            
        }
        return OneOfTwo.ofFirst(
                new ExpandedType(new ArrayType(type.getOrdinaryType())));
    }

    
    // Statement and Expr

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ArrayAccessNode n) {
        ExpandedType arrayType = n.child.accept(this).assertFirst();
        ExpandedType indexType = n.index.accept(this).assertFirst();

        if (arrayType.isArray() && indexType.isSubtypeOfInt()) {
            ArrayType actualArrayType = arrayType.getArrayType();
            return OneOfTwo.ofFirst(new ExpandedType(actualArrayType.child));
        }
        throw new SemanticException();
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VariableAccessNode n) {
        Optional<OrdinaryType> optionalVar = context.getVar(n.identifier);
        if (optionalVar.isPresent()) {
            return OneOfTwo.ofFirst(new ExpandedType(optionalVar.get()));
        }
        throw new SemanticException();
    }

    // Statement

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ArrayDeclStmtNode n) {
        if (context.contains(n.identifier)) {
            throw new SemanticException("Duplicate variablee declaration");
        }
        
        ExpandedType expectedArray = n.type.accept(this).assertFirst();
        if (!expectedArray.isArray()) {
            throw new SemanticException("Expected an array, but found a "
                    + "non-array type instead");
        }
        if (expectedArray.isOrdinary()) {
            context.addVar(n.identifier, (OrdinaryType)expectedArray);
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException("Expected an array or primitive, but "
                    + "found a tuple");
        }
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
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isOrdinary()) {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else if (type.isTuple()) {
            throw new SemanticException("Expected a single return value, but "
                    + "found a tuple");
        } else {
            throw new SemanticException("Expected a function, but found "
                    + "a procedure");
        }
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
        ExpandedType types = n.initializer.accept(this).assertFirst();
        if (types.isUnit()) {
            throw new SemanticException("Expected function call, but found a "
                    + "procedure instead.");
        }
        TupleType tupleType = (TupleType)types;
        List<ExpandedType> typeList = tupleType.elements;
        List<Optional<VarDeclNode>> varDecls = n.varDecls;
        if (typeList.size() != varDecls.size()) {
            throw new SemanticException("Number of variable declarations and "
                    + "function return values do not match");
        }
        
        Iterator<ExpandedType> typeIterator = typeList.iterator();
        Iterator<Optional<VarDeclNode>> declIterator = varDecls.iterator();
        while(typeIterator.hasNext() && declIterator.hasNext() ) {
            ExpandedType returnedType = typeIterator.next();
            Optional<VarDeclNode> maybeVarDecl = declIterator.next();
            
            if (maybeVarDecl.isPresent()) {
                VarDeclNode varDecl = maybeVarDecl.get();
                ExpandedType varDeclType = varDecl.accept(this).assertFirst();
                assert(returnedType.isOrdinary() && varDeclType.isOrdinary());
                if (isSubtype(returnedType, varDeclType)) {
                    context.addVar(varDecl.identifier, 
                                (OrdinaryType)varDeclType);
                } else {
                    throw new SemanticException("Mismatched types");
                }
            }
        }
        return OneOfTwo.ofSecond(ResultType.UNIT);
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
        ExpandedType type = n.varDecl.accept(this).assertFirst();
        if (type.isOrdinary()) {
            context.addVar(n.varDecl.identifier, (OrdinaryType) type);
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException();
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VarInitStmtNode n) {
        VarDeclNode varDecl = n.varDecl;
        ExpandedType varDeclType = varDecl.accept(this).assertFirst();
        ExpandedType initializedType = n.initializer.accept(this).assertFirst();
        
        if (isSubtype(initializedType, varDeclType)) {
            assert(varDeclType.isOrdinary() && initializedType.isOrdinary());
            context.addVar(varDecl.identifier, (OrdinaryType)varDeclType);
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException("Mismatched types");
        }
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
