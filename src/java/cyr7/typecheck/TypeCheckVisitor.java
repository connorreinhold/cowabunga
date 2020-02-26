package cyr7.typecheck;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.binexpr.*;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.stmt.*;
import cyr7.ast.stmt.assign.ArrayAssignNode;
import cyr7.ast.stmt.assign.VariableAssignNode;
import cyr7.ast.toplevel.*;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.semantics.DuplicateIdentifierException;
import cyr7.exceptions.semantics.EarlyReturnException;
import cyr7.exceptions.semantics.InvalidArgumentException;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.exceptions.semantics.TypeMismatchException;
import cyr7.exceptions.semantics.UnboundIdentifierException;
import cyr7.exceptions.semantics.UncomparableValuesException;
import cyr7.exceptions.semantics.UnsummableValuesException;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.FunctionType;
import cyr7.semantics.OrdinaryType;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ResultType;
import cyr7.util.OneOfTwo;
import cyr7.visitor.AbstractVisitor;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TypeCheckVisitor extends
    AbstractVisitor<OneOfTwo<ExpandedType, ResultType>> {

    /**
     * Returns the supertype of two types, if such a relation exists.
     * @return Empty if there is typing relation between {@code left}
     * and {@code right}.
     */
    private static Optional<ExpandedType> supertypeOf(ExpandedType left,
                                                      ExpandedType right) {
        if (left.isASubtypeOf(right)) {
            return Optional.of(right);
        } else if (right.isASubtypeOf(left)) {
            return Optional.of(left);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Symbol table that maps identifiers to types.
     */
    private final Context context;

    /**
     * Initialize typecheck visitor with given Context {@code initialContext}.
     * @param initialContext
     */
    public TypeCheckVisitor(Context initialContext) {
        this.context = initialContext;
    }

    // Top Level
    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(FunctionDeclNode n) {
        ExpandedType outputTypes = n.header.accept(this).assertFirst();
        context.push();
        n.header.args.forEach(v -> {
            ExpandedType t = v.accept(this).assertFirst();
            assert(t.isOrdinary());
            context.addVar(v.identifier, t.getOrdinaryType());
        });
        context.addRet(outputTypes);
        n.block.accept(this);
        context.pop();
        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(FunctionHeaderDeclNode n) {
        String functionName = n.identifier;
        if (context.contains(functionName)) {
            throw new DuplicateIdentifierException(functionName, 
                    n.getLocation().get());
        }
        ExpandedType input = new ExpandedType(n.args.stream().map(v -> {
            ExpandedType t = v.accept(this).assertFirst();
            assert(t.isOrdinary());
            return t.getOrdinaryType();
        }).collect(Collectors.toList()));

        ExpandedType output = new ExpandedType(n.returnTypes.stream()
               .map(v -> {
            ExpandedType t = v.accept(this).assertFirst();
            assert(t.isOrdinary());
            return t.getOrdinaryType();
        }).collect(Collectors.toList()));
        FunctionType functionType = new FunctionType(input, output);
        context.addFn(functionName, functionType);
        return OneOfTwo.ofFirst(output);
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
            throw new DuplicateIdentifierException(n.identifier,
                                                   n.getLocation().get());
        }
        ExpandedType type = n.typeExpr.accept(this).assertFirst();
        context.addVar(n.identifier, type.getOrdinaryType());
        return OneOfTwo.ofFirst(type);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(XiProgramNode n) {
        return null;
    }

    // Type

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(PrimitiveTypeNode n) {
        switch (n.type) {
        case BOOL:
            return OneOfTwo.ofFirst(ExpandedType.boolType);
        case INT:
            return OneOfTwo.ofFirst(ExpandedType.intType);
        }
        return null;
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(TypeExprArrayNode n) {
        ExpandedType type = n.child.accept(this).assertFirst();
        if (n.size.isPresent()) {
            ExpandedType sizeType = n.size.get().accept(this).assertFirst();
            if (!sizeType.isSubtypeOfInt()) {
                throw new TypeMismatchException(
                        sizeType, ExpandedType.intType,
                        n.size.get().getLocation().get());
            }
        }
        assert type.isOrdinary();
        
        return OneOfTwo.ofFirst(
                new ExpandedType(new ArrayType(type.getOrdinaryType())));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ArrayAssignNode n) {
        ExpandedType arrayType = n.child.accept(this).assertFirst();
        ExpandedType indexType = n.index.accept(this).assertFirst();

        if (!indexType.isSubtypeOfInt()) {
            throw new TypeMismatchException(
                    indexType, ExpandedType.intType,
                    n.index.getLocation().get());
        }
        
        if (!arrayType.isSubtypeOfArray()) {
            throw new TypeMismatchException(
                    indexType, ExpandedType.voidArrayType,
                    n.child.getLocation().get());
        }
        
        // TODO: deal with the case of arrayType being void, e.g. {}[0][0].
        OrdinaryType innerArrayType = arrayType.getInnerArrayType();
        return OneOfTwo.ofFirst(new ExpandedType(innerArrayType));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VariableAssignNode n) {
        Optional<OrdinaryType> optionalVar = context.getVar(n.identifier);
        if (optionalVar.isPresent()) {
            return OneOfTwo.ofFirst(new ExpandedType(optionalVar.get()));
        }
        throw new UnboundIdentifierException(n.identifier, 
                                             n.getLocation().get());
    }

    // Statement

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ArrayDeclStmtNode n) {
        if (context.contains(n.identifier)) {
            throw new DuplicateIdentifierException(
                    n.identifier, n.getLocation().get());
        }

        ExpandedType expectedArray = n.type.accept(this).assertFirst();
        
        assert expectedArray.isArray();
        assert expectedArray.isOrdinary();
        
        context.addVar(n.identifier, expectedArray.getOrdinaryType());
        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(AssignmentStmtNode n) {
        ExpandedType lhsType = n.assign.accept(this).assertFirst();
        ExpandedType rhsType = n.value.accept(this).assertFirst();

        if (rhsType.isASubtypeOf(lhsType)) {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(lhsType, rhsType, 
                    n.value.getLocation().get());
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
                throw new EarlyReturnException(n.getLocation().get());
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
        } else {
            throw new TypeMismatchException(type, 
                    ExpandedType.unitOrdinaryType, n.expr.getLocation().get());
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(IfElseStmtNode n) {
        context.push();
        ExpandedType guardType = n.guard.accept(this).assertFirst();
        context.pop();

        if (!guardType.isSubtypeOfBool()) {
            throw new TypeMismatchException(guardType, 
                    ExpandedType.boolType, n.guard.getLocation().get());
        }

        context.push();
        ResultType ifType = n.ifBlock.accept(this).assertSecond();
        context.pop();

        if (n.elseBlock.isPresent()) {
            context.push();
            ResultType elseType = n.elseBlock.get().accept(this).assertSecond();
            context.pop();
            return OneOfTwo.ofSecond(
                        ResultType.leastUpperBound(ifType, elseType));
        } else {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(MultiAssignStmtNode n) {
        ExpandedType types = n.initializer.accept(this).assertFirst();
        List<Optional<VarDeclNode>> varDecls = n.varDecls;
        ExpandedType declTypes = new ExpandedType(varDecls.stream().map(v -> {
            if (v.isEmpty()) {
                return OrdinaryType.unitType;
            } else {
                return v.get().accept(this).assertFirst().getOrdinaryType();
            }
        }).collect(Collectors.toList()));
        if (types.isASubtypeOf(declTypes)) {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(types, declTypes, 
                    n.initializer.getLocation().get());
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ProcedureStmtNode n) {
        ExpandedType type = n.procedureCall.accept(this).assertFirst();
        if (type.isUnit()) {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(type, ExpandedType.unitExpandedType,
                    n.procedureCall.getLocation().get());
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ReturnStmtNode n) {
        Optional<ExpandedType> maybeTypes = context.getRet();
        assert maybeTypes.isPresent();
        ExpandedType expected = maybeTypes.get();
        ExpandedType exprType = new ExpandedType(n.exprs.stream()
                .map(e -> {
                    ExpandedType t = e.accept(this).assertFirst();
                    assert t.isOrdinary();
                    return t.getOrdinaryType();
                }).collect(Collectors.toList()));;
                
        if (exprType.isASubtypeOf(expected)) {
            return OneOfTwo.ofSecond(ResultType.VOID);
        } else {
            throw new TypeMismatchException(exprType, expected,
                    n.getLocation().get());
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VarDeclStmtNode n) {
        ExpandedType type = n.varDecl.accept(this).assertFirst();
        assert type.isOrdinary();
        context.addVar(n.varDecl.identifier, type.getOrdinaryType());
        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VarInitStmtNode n) {
        VarDeclNode varDecl = n.varDecl;
        ExpandedType varDeclType = varDecl.accept(this).assertFirst();
        ExpandedType initializedType = n.initializer.accept(this).assertFirst();
        
        if (initializedType.isASubtypeOf(varDeclType)) {
            assert varDeclType.isOrdinary() && initializedType.isOrdinary();
            context.addVar(varDecl.identifier, varDeclType.getOrdinaryType());
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(initializedType, varDeclType,
                    n.getLocation().get());
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(WhileStmtNode n) {
        ExpandedType guardType = n.guard.accept(this).assertFirst();

        if (!guardType.isSubtypeOfBool()) {
            throw new TypeMismatchException(guardType, 
                    ExpandedType.boolType, n.guard.getLocation().get());
        }

        context.push();
        n.block.accept(this);
        context.pop();

        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    // Expression

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LiteralArrayExprNode n) {
        if (n.arrayVals.size() == 0) {
            return OneOfTwo.ofFirst(new ExpandedType(
                    new ArrayType(OrdinaryType.voidType)));
        }
        // arrayType is the supertype of the first 0...i array values
        Optional<ExpandedType> arrayType = Optional.empty();
        List<ExpandedType> arrayTypes = n.arrayVals.stream().map(e -> {
            return e.accept(this).assertFirst();
        }).collect(Collectors.toList());
        
        int index = 0;
        for (ExpandedType t: arrayTypes) {
            if (arrayType.isEmpty()) {
                arrayType = Optional.of(t);
            } else {
                Optional<ExpandedType> possibleSupertype =
                        supertypeOf(arrayType.get(), t);
                if (possibleSupertype.isEmpty()) {
                    throw new TypeMismatchException(t, 
                            arrayType.get(), 
                            n.arrayVals.get(index).getLocation().get());
                    
                } else {
                    arrayType = possibleSupertype;
                }
            }
            index++;
        }
        
        assert arrayType.isPresent();
        assert arrayType.get().isOrdinary();
        OrdinaryType elementType = arrayType.get().getOrdinaryType();
        return OneOfTwo.ofFirst(new ExpandedType(new ArrayType(elementType)));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(FunctionCallExprNode n) {
        Optional<FunctionType> optionalFn = context.getFn(n.identifier);
        if (optionalFn.isEmpty()) {
            throw new UnboundIdentifierException(n.identifier, 
                    n.getLocation().get());
        }
        FunctionType function = optionalFn.get();
        ExpandedType inputTypes = function.input;
        ExpandedType params = new ExpandedType(n.parameters.stream()
                .map(e -> e.accept(this).assertFirst().getOrdinaryType())
                .collect(Collectors.toList()));

        if (params.isASubtypeOf(inputTypes)) {
            return OneOfTwo.ofFirst(function.output);
        } else {
            throw new InvalidArgumentException(params, 
                    inputTypes, n.getLocation().get());
        }
    }


    /**
     * Typechecks an integer binary operation expression, e.g. 9 + 10.
     */
    private ExpandedType typecheckIntegerBinExpr(BinExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (!left.isSubtypeOfInt()) {
            throw new TypeMismatchException(left, 
                    ExpandedType.intType, n.left.getLocation().get());
        }
        if (!right.isSubtypeOfInt()) {
            throw new TypeMismatchException(right, 
                    ExpandedType.intType, n.right.getLocation().get());
        }
        
        return ExpandedType.intType;
    }

    /**
     * Typechecks a boolean binary operation expression, e.g. true || false.
     */
    private ExpandedType typecheckBooleanBinExpr(BinExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (!left.isSubtypeOfBool()) {
            throw new TypeMismatchException(left, 
                    ExpandedType.boolType, n.left.getLocation().get());
        }

        if (!right.isSubtypeOfBool()) {
            throw new TypeMismatchException(right, 
                    ExpandedType.boolType, n.right.getLocation().get());
        }
        return ExpandedType.boolType;
    }

    /**
     * Typechecks a comparison expression, e.g. 3 <= 31.
     */
    private ExpandedType typecheckComparisonBinExpr(BinExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (!left.isSubtypeOfInt()) {
            throw new TypeMismatchException(left, 
                    ExpandedType.intType, n.left.getLocation().get());
        }
        if (!right.isSubtypeOfInt()) {
            throw new TypeMismatchException(right, 
                    ExpandedType.intType, n.right.getLocation().get());
        }
        return ExpandedType.boolType;
    }

    /**
     * Typechecks an equality expression, e.g. 3 == 31.
     */
    private ExpandedType typecheckEqualityBinExpr(BinExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (supertypeOf(left, right).isEmpty()) {
            throw new UncomparableValuesException(left, right, 
                    n.getLocation().get());
        }
        return ExpandedType.boolType;
    }


    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(AddExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (left.isSubtypeOfInt() && right.isSubtypeOfInt()) {
            Optional<ExpandedType> supertype = supertypeOf(left, right);
            if (supertype.isPresent()) {
                return OneOfTwo.ofFirst(supertype.get());
            }
        }
        if (left.isSubtypeOfArray() && right.isSubtypeOfArray()) {
            Optional<ExpandedType> supertype = supertypeOf(left, right);
            if (supertype.isPresent()) {
                return OneOfTwo.ofFirst(supertype.get());
            }
        }
        throw new UnsummableValuesException(left, right, n.getLocation().get());
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(EqualsExprNode n) {
        return OneOfTwo.ofFirst(this.typecheckEqualityBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(NotEqualsExprNode n) {
        return OneOfTwo.ofFirst(this.typecheckEqualityBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(HighMultExprNode n) {
        return OneOfTwo.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(MultExprNode n) {
        return OneOfTwo.ofFirst(typecheckIntegerBinExpr(n));
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
    public OneOfTwo<ExpandedType, ResultType> visit(DivExprNode n) {
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
    public OneOfTwo<ExpandedType, ResultType> visit(GTEExprNode n) {
        return OneOfTwo.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(GTExprNode n) {
        return OneOfTwo.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(OrExprNode n) {
        return OneOfTwo.ofFirst(typecheckBooleanBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(AndExprNode n) {
        return OneOfTwo.ofFirst(typecheckBooleanBinExpr(n));
    }


    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LiteralBoolExprNode n) {
        return OneOfTwo.ofFirst(ExpandedType.boolType);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LiteralCharExprNode n) {
        return OneOfTwo.ofFirst(ExpandedType.intType);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LiteralIntExprNode n) {
        return OneOfTwo.ofFirst(ExpandedType.intType);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(LiteralStringExprNode n) {
        return OneOfTwo.ofFirst(
                new ExpandedType(new ArrayType(PrimitiveType.intType)));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(BoolNegExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isSubtypeOfBool()) {
            return OneOfTwo.ofFirst(ExpandedType.boolType);
        }
        throw new TypeMismatchException(type, ExpandedType.boolType, 
                n.expr.getLocation().get());
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(IntNegExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isSubtypeOfInt()) {
            return OneOfTwo.ofFirst(ExpandedType.intType);
        }
        throw new TypeMismatchException(type, ExpandedType.intType, 
                n.expr.getLocation().get());
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(
            ArrayAccessExprNode n) {
        ExpandedType arrayType = n.child.accept(this).assertFirst();
        ExpandedType indexType = n.index.accept(this).assertFirst();

        if (!indexType.isSubtypeOfInt()) {
            throw new TypeMismatchException(indexType, ExpandedType.intType, 
                    n.index.getLocation().get());
        }
        
        if (!arrayType.isSubtypeOfArray()) {
            throw new TypeMismatchException(arrayType, 
                    ExpandedType.voidArrayType, 
                    n.index.getLocation().get());
        }
        
        OrdinaryType innerArrayType = arrayType.getInnerArrayType();
        return OneOfTwo.ofFirst(new ExpandedType(innerArrayType));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VariableAccessExprNode n) {
        Optional<OrdinaryType> optionalVar = context.getVar(n.identifier);
        if (optionalVar.isPresent()) {
            return OneOfTwo.ofFirst(new ExpandedType(optionalVar.get()));
        }
        throw new UnboundIdentifierException(n.identifier, 
                n.getLocation().get());
    }

}
