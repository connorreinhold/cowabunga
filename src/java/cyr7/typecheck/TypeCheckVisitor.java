package cyr7.typecheck;

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
import cyr7.exceptions.SemanticException;
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.FunctionType;
import cyr7.semantics.OrdinaryType;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ResultType;
import cyr7.semantics.VoidType;
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
        context.addRet(outputTypes);
        n.block.accept(this);
        context.pop();
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
            t = ExpandedType.boolType;
            break;
        case INT:
            t = ExpandedType.intType;
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
            context.addVar(n.identifier, expectedArray.getOrdinaryType());
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
        
        if (rhsType.isASubtypeOf(lhsType)) {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        }
        throw new SemanticException("Inequivalent type assignment");
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

        if (!guardType.isSubtypeOfBool()) {
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
            return OneOfTwo.ofSecond(
                        ResultType.leastUpperBound(ifType, elseType));
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
        List<OrdinaryType> typeList = types.getTypes();
        List<Optional<VarDeclNode>> varDecls = n.varDecls;
        if (typeList.size() != varDecls.size()) {
            throw new SemanticException("Number of variable declarations and "
                    + "function return values do not match");
        }
        Iterator<OrdinaryType> typeIterator = typeList.iterator();
        Iterator<Optional<VarDeclNode>> declIterator = varDecls.iterator();
        while(typeIterator.hasNext() && declIterator.hasNext()) {
            OrdinaryType returnedType = typeIterator.next();
            Optional<VarDeclNode> maybeVarDecl = declIterator.next();
            if (maybeVarDecl.isPresent()) {
                VarDeclNode varDecl = maybeVarDecl.get();
                ExpandedType varDeclType = varDecl.accept(this).assertFirst();
                assert(varDeclType.isOrdinary());
                OrdinaryType varDeclTrueType = varDeclType.getOrdinaryType();
                if (returnedType.isSubtypeOf(varDeclTrueType)) {
                    context.addVar(varDecl.identifier, varDeclTrueType);
                } else {
                    throw new SemanticException("Mismatched types");
                }
            } // else is a wildcard and returned value is thrown away.
        }
        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ProcedureStmtNode n) {
        ExpandedType type = n.procedureCall.accept(this).assertFirst();
        if (type.isUnit()) {
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
            if (expected.isUnit()) {
                return OneOfTwo.ofSecond(ResultType.VOID);
            }
            break;
        case 1:
            if (expected.isOrdinary()) {
                exprType = n.exprs.get(0).accept(this).assertFirst();
                if (exprType.isASubtypeOf(expected)) {
                    return OneOfTwo.ofSecond(ResultType.VOID);
                }
            }
            break;
        default:
            exprType = new ExpandedType(n.exprs.stream()
                    .map(e -> {
                        ExpandedType t = e.accept(this).assertFirst();
                        assert(t.isOrdinary());
                        return t.getOrdinaryType();
                    }).collect(Collectors.toList()));
            if (exprType.isASubtypeOf(expected)) {
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
            context.addVar(n.varDecl.identifier, type.getOrdinaryType());
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
        
        if (initializedType.isASubtypeOf(varDeclType)) {
            assert(varDeclType.isOrdinary() && initializedType.isOrdinary());
            context.addVar(varDecl.identifier, varDeclType.getOrdinaryType());
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException("Mismatched types");
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(WhileStmtNode n) {
        ExpandedType guardType = n.guard.accept(this).assertFirst();
        if (!guardType.isSubtypeOfBool()) {
            throw new SemanticException("Guard expression does "
                    + "not evaluate to bool");
        }

        context.push();
        n.block.accept(this);
        context.pop();

        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    // Expression

    private ExpandedType typecheckIntegerBinExpr(BinExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (left.isSubtypeOfInt() && right.isSubtypeOfInt()) {
            return ExpandedType.intType;
        }
        throw new SemanticException();
    }

    private ExpandedType typecheckBooleanBinExpr(BinExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (left.isSubtypeOfBool() && right.isSubtypeOfBool()) {
            return ExpandedType.boolType;
        }

        throw new SemanticException();
    }

    private ExpandedType typecheckComparisonBinExpr(BinExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (left.isSubtypeOfInt() && right.isSubtypeOfInt()) {
            return ExpandedType.intType;
        }

        throw new SemanticException();
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ArrayExprNode n) {
        if (n.arrayVals.size() == 0) {
            return OneOfTwo.ofFirst(new ExpandedType(
                    new ArrayType(OrdinaryType.voidType)));
        }
        // arrayType is the supertype of the first 0...i array values
        Optional<ExpandedType> arrayType = Optional.empty();
        List<ExpandedType> arrayTypes = n.arrayVals.stream().map(e -> {
            return e.accept(this).assertFirst();
        }).collect(Collectors.toList());
        for (ExpandedType t: arrayTypes) {
            if (arrayType.isEmpty()) {
                arrayType = Optional.of(t);
            } else {
                Optional<ExpandedType> possibleSupertype = 
                        supertypeOf(arrayType.get(), t);
                if (possibleSupertype.isEmpty()) {
                    throw new SemanticException("Mismatch types in array");
                } else {
                    arrayType = possibleSupertype;
                }
            }
        }
        assert(arrayType.isPresent());
        return OneOfTwo.ofFirst(arrayType.get());
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(FunctionCallExprNode n) {
        Optional<FunctionType> optionalFn = context.getFn(n.identifier);
        if (optionalFn.isEmpty()) {
            throw new SemanticException("Function does not exist");
        }
        FunctionType function = optionalFn.get();
        ExpandedType inputTypes = function.input;
        ExpandedType params = new ExpandedType(n.parameters.stream()
                .map(e -> e.accept(this).assertFirst().getOrdinaryType())
                .collect(Collectors.toList()));
        
        if (params.isASubtypeOf(inputTypes)) {
            return OneOfTwo.ofFirst(function.output);
        } else {
            throw new SemanticException("Parameter types do not match types "
                    + "specified by function");
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(AddExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (left.isSubtypeOfInt() && right.isSubtypeOfInt()) {
            return OneOfTwo.ofFirst(ExpandedType.intType);
        }
        if (left.isArray() && right.isArray()) {
            Optional<ExpandedType> supertype = supertypeOf(left, right);
            if (supertype.isPresent()) {
                return OneOfTwo.ofFirst(supertype.get());
            }
        }
        throw new SemanticException("Cannot add incompatible types");
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(AndExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (left.isSubtypeOfBool() && right.isSubtypeOfBool()) {
            return OneOfTwo.ofFirst(ExpandedType.boolType);
        }
        throw new SemanticException("Cannot perform AND on non-bool values");
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(DivExprNode n) {
        return OneOfTwo.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(EqualsExprNode n) {
        ExpandedType left = n.accept(this).assertFirst();
        ExpandedType right = n.accept(this).assertFirst();

        if (left.isOrdinary() && right.isOrdinary()
            && supertypeOf(left, right).isPresent()) {
            return OneOfTwo.ofFirst(ExpandedType.boolType);
        }
        throw new SemanticException("Types of values being compared are "
                + "incompatible");
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
        return OneOfTwo.ofFirst(ExpandedType.intType);
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
        throw new SemanticException("Cannot negate a non-bool value");
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(IntNegExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isSubtypeOfInt()) {
            return OneOfTwo.ofFirst(ExpandedType.intType);
        }

        throw new SemanticException();
    }
}
