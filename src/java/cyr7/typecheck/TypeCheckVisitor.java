package cyr7.typecheck;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.*;
import cyr7.ast.expr.literalexpr.*;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.stmt.*;
import cyr7.ast.stmt.assign.ArrayAssignNode;
import cyr7.ast.stmt.assign.VariableAssignNode;
import cyr7.ast.toplevel.*;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.ParserException;
import cyr7.exceptions.semantics.*;
import cyr7.parser.ParserUtil;
import cyr7.semantics.*;
import cyr7.util.OneOfThree;
import cyr7.visitor.AbstractVisitor;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class TypeCheckVisitor extends
    AbstractVisitor<OneOfThree<ExpandedType, ResultType, Optional<Void>>> {

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
    protected final Context context;

    private final IxiFileOpener fileOpener;

    private final Map<String, FunctionType> interfaceFuncDecls;

    /**
     * Initialize typecheck visitor with given Context {@code initialContext}.
     */
    public TypeCheckVisitor(IxiFileOpener fileOpener) {
        this.context = new HashMapStackContext();
        this.fileOpener = fileOpener;
        this.interfaceFuncDecls = new HashMap<>();
    }

    // Top Level
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(FunctionDeclNode n) {
        ExpandedType outputTypes = context.getFn(n.header.identifier).get().output;
        context.push();
        n.header.args.forEach(v -> {
            ExpandedType t = v.accept(this).assertFirst();
            assert t.isOrdinary();
            context.addVar(v.identifier, t.getOrdinaryType());
        });
        context.addRet(outputTypes);
        ResultType type = n.block.accept(this).assertSecond();
        if (!outputTypes.isUnit() && type.equals(ResultType.UNIT)) {
            throw new MissingReturnException(n.getLocation().get());
        }
        context.pop();
        return OneOfThree.ofThird(Optional.empty());
    }

    private FunctionType functionTypeOf(FunctionHeaderDeclNode n) {
        ExpandedType input = new ExpandedType(n.args.stream().map(v -> {
            ExpandedType t = v.typeExpr.accept(this).assertFirst();
            assert t.isOrdinary();
            return t.getOrdinaryType();
        }).collect(Collectors.toList()));

        ExpandedType output = new ExpandedType(n.returnTypes.stream()
            .map(v -> {
                ExpandedType t = v.accept(this).assertFirst();
                assert t.isOrdinary();
                return t.getOrdinaryType();
            }).collect(Collectors.toList()));
        return new FunctionType(input, output);
    }

    /**
     * Only call this from XiProgramNode
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(FunctionHeaderDeclNode n) {
        String functionName = n.identifier;
        FunctionType functionType = functionTypeOf(n);
        if (context.contains(functionName)) {
            throw new DuplicateIdentifierException(functionName,
                    n.getLocation().get());
        } else if (interfaceFuncDecls.containsKey(n.identifier)
                && !interfaceFuncDecls.get(n.identifier).equals(functionType)) {
            throw new DuplicateIdentifierException(n.identifier, n.getLocation().get());
        }
        context.addFn(functionName, functionType);
        return OneOfThree.ofFirst(functionType.output);
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(IxiProgramNode n) {
        n.functionDeclarations.forEach(header -> {
            FunctionType functionType = functionTypeOf(header);

            if (interfaceFuncDecls.containsKey(header.identifier)
                && !interfaceFuncDecls.get(header.identifier).equals(functionType)) {
                throw new DuplicateIdentifierException(header.identifier, header.getLocation().get());
            } else {
                interfaceFuncDecls.put(header.identifier, functionType);
            }
        });
        return OneOfThree.ofThird(Optional.empty());
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(UseNode n) {
        try {
            Reader reader = fileOpener.openIxiLibraryFile(n.interfaceName);
            IxiProgramNode interfaceNode = (IxiProgramNode) ParserUtil.parseNode(reader, true);
            interfaceNode.accept(this);
            return OneOfThree.ofThird(Optional.empty());
        } catch(ParserException e) {
            throw new SemanticParserException(e);
        } catch(IOException e) {
            throw new InterfaceFileNotFoundException(n.interfaceName, n.getLocation().get());
        } catch(Exception e) {
            throw new SemanticParserException(e, n.getLocation().get());
        }
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(VarDeclNode n) {
        if (context.contains(n.identifier)) {
            throw new DuplicateIdentifierException(n.identifier,
                                                   n.getLocation().get());
        }
        ExpandedType type = n.typeExpr.accept(this).assertFirst();
        context.addVar(n.identifier, type.getOrdinaryType());
        return OneOfThree.ofFirst(type);
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(XiProgramNode n) {
        context.addFn("length", new FunctionType(ExpandedType.voidArrayType, ExpandedType.intType));

        n.uses.forEach(use -> use.accept(this));
        n.functions.forEach(decl -> decl.header.accept(this));
        n.functions.forEach(decl -> decl.accept(this));

        return OneOfThree.ofThird(Optional.empty());
    }

    // Type

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(PrimitiveTypeNode n) {
        switch (n.type) {
        case BOOL:
            return OneOfThree.ofFirst(ExpandedType.boolType);
        case INT:
            return OneOfThree.ofFirst(ExpandedType.intType);
        }
        return null;
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(TypeExprArrayNode n) {
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
        
        return OneOfThree.ofFirst(
                new ExpandedType(new ArrayType(type.getOrdinaryType())));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(ArrayAssignNode n) {
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
        return OneOfThree.ofFirst(new ExpandedType(innerArrayType));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(VariableAssignNode n) {
        Optional<OrdinaryType> optionalVar = context.getVar(n.identifier);
        if (optionalVar.isPresent()) {
            return OneOfThree.ofFirst(new ExpandedType(optionalVar.get()));
        }
        throw new UnboundIdentifierException(n.identifier, 
                                             n.getLocation().get());
    }

    // Statement

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(ArrayDeclStmtNode n) {
        if (context.contains(n.identifier)) {
            throw new DuplicateIdentifierException(
                    n.identifier, n.getLocation().get());
        }

        ExpandedType expectedArray = n.type.accept(this).assertFirst();
        
        assert expectedArray.isArray();
        assert expectedArray.isOrdinary();
        
        context.addVar(n.identifier, expectedArray.getOrdinaryType());
        return OneOfThree.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(AssignmentStmtNode n) {
        ExpandedType lhsType = n.assign.accept(this).assertFirst();
        ExpandedType rhsType = n.value.accept(this).assertFirst();

        if (rhsType.isASubtypeOf(lhsType)) {
            return OneOfThree.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(lhsType, rhsType, 
                    n.value.getLocation().get());
        }
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(BlockStmtNode n) {
        context.push();
        Iterator<StmtNode> stmtIterator = n.statements.iterator();
        while (stmtIterator.hasNext()) {
            StmtNode stmt = stmtIterator.next();
            ResultType type = stmt.accept(this).assertSecond();
            if (stmtIterator.hasNext() && type == ResultType.VOID) {
                throw new EarlyReturnException(n.getLocation().get());
            } else if (!stmtIterator.hasNext()) {
                context.pop();
                return OneOfThree.ofSecond(type);
            }
        }
        context.pop();
        return OneOfThree.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(ExprStmtNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isOrdinary()) {
            return OneOfThree.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(type, 
                    ExpandedType.unitOrdinaryType, n.expr.getLocation().get());
        }
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(IfElseStmtNode n) {
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
            return OneOfThree.ofSecond(
                        ResultType.leastUpperBound(ifType, elseType));
        } else {
            return OneOfThree.ofSecond(ResultType.UNIT);
        }
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(MultiAssignStmtNode n) {
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
            return OneOfThree.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(types, declTypes, 
                    n.initializer.getLocation().get());
        }
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(ProcedureStmtNode n) {
        ExpandedType type = n.procedureCall.accept(this).assertFirst();
        if (type.isUnit()) {
            return OneOfThree.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(type, ExpandedType.unitExpandedType,
                    n.procedureCall.getLocation().get());
        }
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(ReturnStmtNode n) {
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
            return OneOfThree.ofSecond(ResultType.VOID);
        } else {
            throw new TypeMismatchException(exprType, expected,
                    n.getLocation().get());
        }
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(VarDeclStmtNode n) {
        n.varDecl.accept(this);
        return OneOfThree.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(VarInitStmtNode n) {
        VarDeclNode varDecl = n.varDecl;
        ExpandedType varDeclType = varDecl.accept(this).assertFirst();
        ExpandedType initializedType = n.initializer.accept(this).assertFirst();
        
        if (initializedType.isASubtypeOf(varDeclType)) {
            assert varDeclType.isOrdinary() && initializedType.isOrdinary();
            return OneOfThree.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(initializedType, varDeclType,
                    n.getLocation().get());
        }
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(WhileStmtNode n) {
        ExpandedType guardType = n.guard.accept(this).assertFirst();

        if (!guardType.isSubtypeOfBool()) {
            throw new TypeMismatchException(guardType, 
                    ExpandedType.boolType, n.guard.getLocation().get());
        }

        context.push();
        n.block.accept(this);
        context.pop();

        return OneOfThree.ofSecond(ResultType.UNIT);
    }

    // Expression

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(LiteralArrayExprNode n) {
        if (n.arrayVals.size() == 0) {
            return OneOfThree.ofFirst(new ExpandedType(
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
        return OneOfThree.ofFirst(new ExpandedType(new ArrayType(elementType)));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(FunctionCallExprNode n) {
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
            return OneOfThree.ofFirst(function.output);
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
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(AddExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (left.isSubtypeOfInt() && right.isSubtypeOfInt()) {
            Optional<ExpandedType> supertype = supertypeOf(left, right);
            if (supertype.isPresent()) {
                return OneOfThree.ofFirst(supertype.get());
            }
        }
        if (left.isSubtypeOfArray() && right.isSubtypeOfArray()) {
            Optional<ExpandedType> supertype = supertypeOf(left, right);
            if (supertype.isPresent()) {
                return OneOfThree.ofFirst(supertype.get());
            }
        }
        throw new UnsummableValuesException(left, right, n.getLocation().get());
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(EqualsExprNode n) {
        return OneOfThree.ofFirst(this.typecheckEqualityBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(NotEqualsExprNode n) {
        return OneOfThree.ofFirst(this.typecheckEqualityBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(HighMultExprNode n) {
        return OneOfThree.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(MultExprNode n) {
        return OneOfThree.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(RemExprNode n) {
        return OneOfThree.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(SubExprNode n) {
        return OneOfThree.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(DivExprNode n) {
        return OneOfThree.ofFirst(typecheckIntegerBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(LTEExprNode n) {
        return OneOfThree.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(LTExprNode n) {
        return OneOfThree.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(GTEExprNode n) {
        return OneOfThree.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(GTExprNode n) {
        return OneOfThree.ofFirst(typecheckComparisonBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(OrExprNode n) {
        return OneOfThree.ofFirst(typecheckBooleanBinExpr(n));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(AndExprNode n) {
        return OneOfThree.ofFirst(typecheckBooleanBinExpr(n));
    }


    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(LiteralBoolExprNode n) {
        return OneOfThree.ofFirst(ExpandedType.boolType);
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(LiteralCharExprNode n) {
        return OneOfThree.ofFirst(ExpandedType.intType);
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(LiteralIntExprNode n) {
        return OneOfThree.ofFirst(ExpandedType.intType);
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(LiteralStringExprNode n) {
        return OneOfThree.ofFirst(
                new ExpandedType(new ArrayType(PrimitiveType.intType)));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(BoolNegExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isSubtypeOfBool()) {
            return OneOfThree.ofFirst(ExpandedType.boolType);
        }
        throw new TypeMismatchException(type, ExpandedType.boolType, 
                n.expr.getLocation().get());
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(IntNegExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isSubtypeOfInt()) {
            return OneOfThree.ofFirst(ExpandedType.intType);
        }
        throw new TypeMismatchException(type, ExpandedType.intType, 
                n.expr.getLocation().get());
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(
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
        return OneOfThree.ofFirst(new ExpandedType(innerArrayType));
    }

    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(VariableAccessExprNode n) {
        Optional<OrdinaryType> optionalVar = context.getVar(n.identifier);
        if (optionalVar.isPresent()) {
            return OneOfThree.ofFirst(new ExpandedType(optionalVar.get()));
        }
        throw new UnboundIdentifierException(n.identifier, 
                n.getLocation().get());
    }

}
