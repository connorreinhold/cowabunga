package cyr7.typecheck;

import static cyr7.semantics.types.ExpandedType.supertypeOf;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.BinExprNode;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.expr.unaryexpr.LengthExprNode;
import cyr7.ast.stmt.ArrayDeclStmtNode;
import cyr7.ast.stmt.AssignmentStmtNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.ExprStmtNode;
import cyr7.ast.stmt.IfElseStmtNode;
import cyr7.ast.stmt.MultiAssignStmtNode;
import cyr7.ast.stmt.ProcedureStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.VarDeclStmtNode;
import cyr7.ast.stmt.VarInitStmtNode;
import cyr7.ast.stmt.WhileStmtNode;
import cyr7.ast.toplevel.FunctionDeclNode;
import cyr7.ast.toplevel.FunctionHeaderDeclNode;
import cyr7.ast.toplevel.IxiProgramNode;
import cyr7.ast.toplevel.UseNode;
import cyr7.ast.toplevel.XiProgramNode;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.lexer.LexerException;
import cyr7.exceptions.parser.ParserException;
import cyr7.exceptions.semantics.ArrayTypeExpectedException;
import cyr7.exceptions.semantics.DuplicateIdentifierException;
import cyr7.exceptions.semantics.EarlyReturnException;
import cyr7.exceptions.semantics.ExpectedFunctionException;
import cyr7.exceptions.semantics.InterfaceFileNotFoundException;
import cyr7.exceptions.semantics.InvalidArgumentException;
import cyr7.exceptions.semantics.InvalidInterfaceException;
import cyr7.exceptions.semantics.InvalidReturnValueException;
import cyr7.exceptions.semantics.MissingReturnException;
import cyr7.exceptions.semantics.OrdinaryTypeExpectedException;
import cyr7.exceptions.semantics.ReturnValueInUnitFunctionException;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.exceptions.semantics.TypeMismatchException;
import cyr7.exceptions.semantics.UnboundIdentifierException;
import cyr7.exceptions.semantics.UncomparableValuesException;
import cyr7.exceptions.semantics.UnsummableValuesException;
import cyr7.parser.ParserUtil;
import cyr7.semantics.context.Context;
import cyr7.semantics.context.HashMapStackContext;
import cyr7.semantics.types.ArrayType;
import cyr7.semantics.types.ExpandedType;
import cyr7.semantics.types.FunctionType;
import cyr7.semantics.types.OrdinaryType;
import cyr7.semantics.types.PrimitiveType;
import cyr7.semantics.types.ResultType;
import cyr7.semantics.types.UnitType;
import cyr7.util.OneOfThree;
import cyr7.visitor.AbstractVisitor;

final class TypeCheckVisitor extends AbstractVisitor<TypeCheckVisitor.Result> {

    static final class Result
            extends OneOfThree<ExpandedType, ResultType, Void> {

        static Result ofExpanded(ExpandedType e) {
            return new Result(e, null);
        }

        static Result ofResult(ResultType r) {
            return new Result(null, r);
        }

        static Result ofVoid() {
            return new Result(null, null);
        }

        protected Result(ExpandedType first, ResultType second) {
            super(first, second, null);
        }

    }

    /**
     * Symbol table that maps identifiers to types.
     */
    protected final Context context;

    /**
     * An interface that communicates with the CLI when the contents of an
     * interface file (*.ixi) is requested.
     */
    private final IxiFileOpener fileOpener;

    private final Map<String, FunctionType> interfaceFuncDecls;

    /**
     * Initialize typecheck visitor with given Context {@code initialContext}.
     */
    TypeCheckVisitor(IxiFileOpener fileOpener) {
        this.context = new HashMapStackContext();
        this.fileOpener = fileOpener;
        this.interfaceFuncDecls = new HashMap<>();
    }

    // Top Level

    /**
     * Type-checks the body of a function declaration.
     * <p>
     * Side effects: this method has no side effects.
     *
     * @param n
     * @return An empty optional
     */
    @Override
    public Result visit(FunctionDeclNode n) {
        ExpandedType outputTypes = context.getFn(n.header.identifier)
                .get().output;
        context.push();
        n.header.args.forEach(v -> {
            ExpandedType t = v.accept(this).assertFirst();
            assert t.isOrdinary();
            context.addVar(v.identifier, t.getOrdinaryType());
        });
        context.addRet(outputTypes);
        ResultType type = n.block.accept(this).assertSecond();
        n.setType(type);
        if (!outputTypes.isUnit() && type.equals(ResultType.UNIT)) {
            throw new MissingReturnException(n.getLocation());
        }
        context.pop();
        return Result.ofVoid();
    }

    /**
     * Returns the function type of a function header.
     * <p>
     * Side effects: this method has no side effects
     *
     * @param n
     * @return The type of n
     */
    private FunctionType functionTypeOf(FunctionHeaderDeclNode n) {
        ExpandedType input = new ExpandedType(n.args.stream().map(v -> {
            ExpandedType t = v.typeExpr.accept(this).assertFirst();
            assert t.isOrdinary();
            return t.getOrdinaryType();
        }).collect(Collectors.toList()));

        ExpandedType output = new ExpandedType(n.returnTypes.stream().map(v -> {
            ExpandedType t = v.accept(this).assertFirst();
            assert t.isOrdinary();
            return t.getOrdinaryType();
        }).collect(Collectors.toList()));
        return new FunctionType(input, output);
    }

    /**
     * Side effects: this method adds the mapping [n.identifier -> typeOf(n)] to
     * the context at the current stack level.
     *
     * @param n
     * @return
     * @throws DuplicateIdentifierException if n.identifier exists within the
     *                                      context, or if an interface declares
     *                                      a function with the same name and
     *                                      conflicting type.
     */
    @Override
    public Result visit(FunctionHeaderDeclNode n) {
        String functionName = n.identifier;
        FunctionType functionType = functionTypeOf(n);
        if (context.contains(functionName)) {
            throw new DuplicateIdentifierException(functionName,
                    n.getLocation());
        } else if (interfaceFuncDecls.containsKey(n.identifier)
                && !interfaceFuncDecls.get(n.identifier).equals(functionType)) {
            throw new DuplicateIdentifierException(n.identifier,
                    n.getLocation());
        }
        context.addFn(functionName, functionType);
        n.setType(functionType);
        return Result.ofExpanded(functionType.output);
    }

    /**
     * Type check an Ixi program by visiting each function declaration.
     * <p>
     * Side effects: this method adds to {@code interfaceFuncDecls} a mapping
     * [header.identifier -> functionType]
     *
     * @param n
     * @return Nothing
     */
    @Override
    public Result visit(IxiProgramNode n) {
        n.functionDeclarations.forEach(header -> {
            FunctionType functionType = functionTypeOf(header);

            if (interfaceFuncDecls.containsKey(header.identifier)
                    && !interfaceFuncDecls.get(header.identifier)
                            .equals(functionType)) {
                throw new DuplicateIdentifierException(header.identifier,
                        header.getLocation());
            } else {
                interfaceFuncDecls.put(header.identifier, functionType);
            }
        });
        return Result.ofVoid();
    }

    /**
     * Type check a use node by type checking the ixi file associated with the
     * use node.
     * <p>
     * Side effects: this method adds function mappings to
     * {@code interfaceFuncDecls}
     *
     * @param n
     * @return Nothing
     */
    @Override
    public Result visit(UseNode n) {
        try {
            Reader reader = fileOpener.openIxiLibraryFile(n.interfaceName);
            IxiProgramNode interfaceNode = (IxiProgramNode) ParserUtil
                    .parseNode(reader, n.interfaceName + ".ixi", true);
            interfaceNode.accept(this);
            return Result.ofVoid();
        } catch (ParserException e) {
            throw new InvalidInterfaceException(e, n.getLocation());
        } catch (LexerException e) {
            throw new InvalidInterfaceException(e, n.getLocation());
        } catch (SemanticException e) {
            throw new InvalidInterfaceException(e, n.getLocation());
        } catch (Exception e) {
            throw new InterfaceFileNotFoundException(n.interfaceName,
                    n.getLocation());
        }
    }

    /**
     * Side effect: this adds a variable declaration [n -> typeOf(n)] to the
     * context
     *
     * @param n
     * @return The type of the variable declaration
     */
    @Override
    public Result visit(VarDeclNode n) {
        if (context.contains(n.identifier)) {
            throw new DuplicateIdentifierException(n.identifier,
                    n.getLocation());
        }
        ExpandedType type = n.typeExpr.accept(this).assertFirst();
        context.addVar(n.identifier, type.getOrdinaryType());
        n.setType(type);
        return Result.ofExpanded(type);
    }

    /**
     * Type check an Xi Program Node.
     * <p>
     * 1. Type-check use declarations 2. Perform a first pass over function
     * headers 3. Type-check
     * <p>
     * Side effects: this method adds the length function to the context.
     *
     * @param n
     * @return Nothing
     */
    @Override
    public Result visit(XiProgramNode n) {
        // Type check use statements
        n.uses.forEach(use -> use.accept(this));

        // Perform a first pass over function headers. If a function declaration
        // in the xi file conflicts with a function declaration in the ixi file,
        // the exception will be thrown here.
        n.functions.forEach(decl -> decl.header.accept(this));

        // There are no conflicting function headers. We add the interface
        // functions to the context so that we can use them when type-checking
        // the body of functions
        for (var funcDecl : interfaceFuncDecls.entrySet()) {
            context.addFn(funcDecl.getKey(), funcDecl.getValue());
        }

        // Type check the body of each function
        n.functions.forEach(decl -> decl.accept(this));

        return Result.ofVoid();
    }

    // Type

    /**
     * Returns the type of n
     * <p>
     * Side effects: this method is pure
     *
     * @param n
     * @return
     */
    @Override
    public Result visit(PrimitiveTypeNode n) {
        switch (n.type) {
        case BOOL:
            return Result.ofExpanded(ExpandedType.boolType);
        case INT:
            return Result.ofExpanded(ExpandedType.intType);
        default:
            // fallback
            return Result.ofExpanded(ExpandedType.intType);
        }
    }

    /**
     * Return the type of n
     * <p>
     * Side effects: this method is pure
     *
     * @param n
     * @return
     */
    @Override
    public Result visit(TypeExprArrayNode n) {
        ExpandedType type = n.child.accept(this).assertFirst();
        if (n.size.isPresent()) {
            ExpandedType sizeType = n.size.get().accept(this).assertFirst();
            if (!sizeType.isSubtypeOfInt()) {
                throw new TypeMismatchException(sizeType, ExpandedType.intType,
                        n.size.get().getLocation());
            }
        }

        if (!type.isOrdinary()) {
            throw new OrdinaryTypeExpectedException(n.getLocation());
        }

        return Result.ofExpanded(
                new ExpandedType(new ArrayType(type.getOrdinaryType())));
    }

    // Statement

    /**
     * Side effects: this method adds [n.identifier -> typeOf(n)] to the context
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public Result visit(ArrayDeclStmtNode n) {
        if (context.contains(n.identifier)) {
            throw new DuplicateIdentifierException(n.identifier,
                    n.getLocation());
        }

        ExpandedType expectedArray = n.type.accept(this).assertFirst();

        if (!expectedArray.isArray()) {
            throw new ArrayTypeExpectedException(n.getLocation());
        }

        context.addVar(n.identifier, expectedArray.getOrdinaryType());
        return Result.ofResult(ResultType.UNIT);
    }

    /**
     * Side effects: This method has no side effects
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public Result visit(AssignmentStmtNode n) {
        ExpandedType lhsType = n.lhs.accept(this).assertFirst();
        ExpandedType rhsType = n.rhs.accept(this).assertFirst();

        if (rhsType.isASubtypeOf(lhsType)) {
            return Result.ofResult(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(lhsType, rhsType,
                    n.rhs.getLocation());
        }
    }

    /**
     * Side effects: This method has no side effects
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public Result visit(BlockStmtNode n) {
        context.push();
        Iterator<StmtNode> stmtIterator = n.statements.iterator();
        while (stmtIterator.hasNext()) {
            StmtNode stmt = stmtIterator.next();
            ResultType type = stmt.accept(this).assertSecond();
            if (stmtIterator.hasNext() && type == ResultType.VOID) {
                throw new EarlyReturnException(stmt.getLocation());
            } else if (!stmtIterator.hasNext()) {
                context.pop();
                return Result.ofResult(type);
            }
        }
        context.pop();
        return Result.ofResult(ResultType.UNIT);
    }

    /**
     * Typecheck a statement of the form:
     * <p>
     * `_ = 4;`
     * <p>
     * Side effects: This method has no side effects
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public Result visit(ExprStmtNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();

        if (!(n.expr instanceof FunctionCallExprNode
                || n.expr instanceof LengthExprNode)) {
            throw new ExpectedFunctionException(n.expr.getLocation());
        }

        if (type.isOrdinary()) {
            // The type of a function call expr node is the return type of the
            // function. (view page 3 of types.pdf)
            return Result.ofResult(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(type, ExpandedType.unitOrdinaryType,
                    n.expr.getLocation());
        }
    }

    /**
     * Side effects: This method has no side effects
     *
     * @param n
     * @return The least upper bound of the two sub block.
     */
    @Override
    public Result visit(IfElseStmtNode n) {
        context.push();
        ExpandedType guardType = n.guard.accept(this).assertFirst();
        context.pop();

        if (!guardType.isSubtypeOfBool()) {
            throw new TypeMismatchException(guardType, ExpandedType.boolType,
                    n.guard.getLocation());
        }

        context.push();
        ResultType ifType = n.ifBlock.accept(this).assertSecond();
        context.pop();

        if (n.elseBlock.isPresent()) {
            context.push();
            ResultType elseType = n.elseBlock.get().accept(this).assertSecond();
            context.pop();
            return Result
                    .ofResult(ResultType.leastUpperBound(ifType, elseType));
        } else {
            return Result.ofResult(ResultType.UNIT);
        }
    }

    /**
     * Side effects: This method adds [n.identifier -> typeOf(n)] to the context
     * for each variable declared.
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public Result visit(MultiAssignStmtNode n) {
        // The initializer must be type-checked before the variable declaration
        // since otherwise it would be possible to do recursive variable
        // declarations
        ExpandedType types = n.initializer.accept(this).assertFirst();

        List<Optional<VarDeclNode>> varDecls = n.varDecls;
        ExpandedType declTypes = new ExpandedType(varDecls.stream().map(v -> {
            if (v.isEmpty()) {
                return UnitType.unitValue;
            } else {
                return v.get().accept(this).assertFirst().getOrdinaryType();
            }
        }).collect(Collectors.toList()));

        if (types.isASubtypeOf(declTypes)) {
            return Result.ofResult(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(types, declTypes,
                    n.initializer.getLocation());
        }
    }

    /**
     * Side effects: This method has no side effects.
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public Result visit(ProcedureStmtNode n) {
        ExpandedType type = n.procedureCall.accept(this).assertFirst();
        if (type.isUnit()) {
            return Result.ofResult(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(type, ExpandedType.unitExpandedType,
                    n.procedureCall.getLocation());
        }
    }

    /**
     * Side effects: This method has no side effects.
     *
     * @param n
     * @return Void Statement Type
     */
    @Override
    public Result visit(ReturnStmtNode n) {
        Optional<ExpandedType> maybeTypes = context.getRet();
        assert maybeTypes.isPresent();

        if (maybeTypes.get().isUnit() && !n.exprs.isEmpty()) {
            throw new ReturnValueInUnitFunctionException(n.getLocation());
        }

        ExpandedType expected = maybeTypes.get();
        ExpandedType exprType = new ExpandedType(n.exprs.stream().map(e -> {
            ExpandedType t = e.accept(this).assertFirst();
            if (!t.isOrdinary()) {
                throw new InvalidReturnValueException(e.getLocation());
            }
            return t.getOrdinaryType();
        }).collect(Collectors.toList()));

        if (exprType.isASubtypeOf(expected)) {
            return Result.ofResult(ResultType.VOID);
        } else {
            throw new TypeMismatchException(exprType, expected,
                    n.getLocation());
        }
    }

    /**
     * Side effects: this method adds a mapping [n.identifier -> typeOf(n)] to
     * the context.
     *
     * @param n
     * @return
     */
    @Override
    public Result visit(VarDeclStmtNode n) {
        n.varDecl.accept(this);
        return Result.ofResult(ResultType.UNIT);
    }

    /**
     * Side effects: this method adds a mapping [n.identifier -> typeOf(n)] to
     * the context.
     *
     * @param n
     * @return
     */
    @Override
    public Result visit(VarInitStmtNode n) {
        // The initializer must be type-checked before the variable declaration
        // since otherwise it would be possible to do recursive variable
        // declarations
        ExpandedType initializedType = n.initializer.accept(this).assertFirst();

        ExpandedType varDeclType = n.varDecl.accept(this).assertFirst();

        if (!initializedType.isOrdinary()) {
            throw new OrdinaryTypeExpectedException(
                    n.initializer.getLocation());
        }

        if (initializedType.isASubtypeOf(varDeclType)) {
            assert varDeclType.isOrdinary();
            return Result.ofResult(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(initializedType, varDeclType,
                    n.getLocation());
        }
    }

    /**
     * Type checks a while statement node
     *
     * @param n
     * @return A statement node
     */
    @Override
    public Result visit(WhileStmtNode n) {
        ExpandedType guardType = n.guard.accept(this).assertFirst();

        if (!guardType.isSubtypeOfBool()) {
            throw new TypeMismatchException(guardType, ExpandedType.boolType,
                    n.guard.getLocation());
        }

        context.push();
        n.block.accept(this);
        context.pop();

        return Result.ofResult(ResultType.UNIT);
    }

    // Expression

    public Result assignType(ExprNode n, ExpandedType t) {
        n.setType(t);
        return Result.ofExpanded(t);
    }

    public Result assignType(ExprNode n, OrdinaryType t) {
        return assignType(n, new ExpandedType(t));
    }

    @Override
    public Result visit(LiteralArrayExprNode n) {
        if (n.arrayVals.size() == 0) {
            return assignType(n, new ExpandedType(ArrayType.voidArrayDefault));
        }

        List<ExpandedType> arrayTypes = n.arrayVals.stream().map(e -> {
            ExpandedType type = e.accept(this).assertFirst();
            if (!type.isOrdinary()) {
                throw new OrdinaryTypeExpectedException(e.getLocation());
            }
            return type;
        }).collect(Collectors.toList());

        // arrayType is the supertype of the first 0...i array values
        Optional<ExpandedType> arrayType = Optional.empty();

        int index = 0;
        for (ExpandedType t : arrayTypes) {
            if (arrayType.isEmpty()) {
                arrayType = Optional.of(t);
            } else {
                Optional<ExpandedType> possibleSupertype = supertypeOf(
                        arrayType.get(), t);
                if (possibleSupertype.isEmpty()) {
                    throw new TypeMismatchException(t, arrayType.get(),
                            n.arrayVals.get(index).getLocation());

                } else {
                    arrayType = possibleSupertype;
                }
            }
            index++;
        }

        assert arrayType.isPresent();

        if (!arrayType.get().isOrdinary()) {
            throw new OrdinaryTypeExpectedException(n.getLocation());
        }

        OrdinaryType elementType = arrayType.get().getOrdinaryType();
        return assignType(n, new ArrayType(elementType));
    }

    @Override
    public Result visit(FunctionCallExprNode n) {
        Optional<FunctionType> optionalFn = context.getFn(n.identifier);
        if (optionalFn.isEmpty()) {
            throw new UnboundIdentifierException(n.identifier, n.getLocation());
        }
        FunctionType functionType = optionalFn.get();
        ExpandedType inputTypes = functionType.input;
        ExpandedType params = new ExpandedType(n.parameters.stream().map(e -> {
            ExpandedType type = e.accept(this).assertFirst();
            if (!type.isOrdinary()) {
                throw new OrdinaryTypeExpectedException(e.getLocation());
            } else {
                return type.getOrdinaryType();
            }
        }).collect(Collectors.toList()));

        if (params.isASubtypeOf(inputTypes)) {
            n.setFunctionType(functionType);
            return assignType(n, functionType.output);
        } else {
            throw new InvalidArgumentException(params, inputTypes,
                    n.getLocation());
        }
    }

    /**
     * Typechecks an integer binary operation expression, e.g. 9 + 10.
     */
    private Result typecheckIntegerBinExpr(BinExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (!left.isSubtypeOfInt()) {
            throw new TypeMismatchException(left, ExpandedType.intType,
                    n.left.getLocation());
        }
        if (!right.isSubtypeOfInt()) {
            throw new TypeMismatchException(right, ExpandedType.intType,
                    n.right.getLocation());
        }

        return assignType(n, ExpandedType.intType);
    }

    /**
     * Typechecks a boolean binary operation expression, e.g. true || false.
     */
    private Result typecheckBooleanBinExpr(BinExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (!left.isSubtypeOfBool()) {
            throw new TypeMismatchException(left, ExpandedType.boolType,
                    n.left.getLocation());
        }

        if (!right.isSubtypeOfBool()) {
            throw new TypeMismatchException(right, ExpandedType.boolType,
                    n.right.getLocation());
        }
        return assignType(n, ExpandedType.boolType);
    }

    /**
     * Typechecks a comparison expression, e.g. 3 <= 31.
     */
    private Result typecheckComparisonBinExpr(BinExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (!left.isSubtypeOfInt()) {
            throw new TypeMismatchException(left, ExpandedType.intType,
                    n.left.getLocation());
        }
        if (!right.isSubtypeOfInt()) {
            throw new TypeMismatchException(right, ExpandedType.intType,
                    n.right.getLocation());
        }
        return assignType(n, ExpandedType.boolType);
    }

    /**
     * Typechecks an equality expression, e.g. 3 == 31.
     */
    private Result typecheckEqualityBinExpr(BinExprNode n) {
        ExpandedType left = n.left.accept(this).assertFirst();
        ExpandedType right = n.right.accept(this).assertFirst();

        if (!left.isOrdinary()) {
            throw new OrdinaryTypeExpectedException(n.left.getLocation());
        }
        if (!right.isOrdinary()) {
            throw new OrdinaryTypeExpectedException(n.right.getLocation());
        }

        if (supertypeOf(left, right).isEmpty()) {
            throw new UncomparableValuesException(left, right, n.getLocation());
        }
        return assignType(n, ExpandedType.boolType);
    }

    @Override
    public Result visit(BinExprNode n) {
        switch (n.opType) {
        case ADD: {
            ExpandedType left = n.left.accept(this).assertFirst();
            ExpandedType right = n.right.accept(this).assertFirst();

            if (left.isVoid() && right.isVoid()) {
                return assignType(n, ExpandedType.genericAddType);
            }

            if (left.isSubtypeOfInt() && right.isSubtypeOfInt()) {
                Optional<ExpandedType> supertype = supertypeOf(left, right);
                if (supertype.isPresent()) {
                    return assignType(n, supertype.get());
                }
            }
            if (left.isSubtypeOfArray() && right.isSubtypeOfArray()) {
                Optional<ExpandedType> supertype = supertypeOf(left, right);
                if (supertype.isPresent()) {
                    return assignType(n, supertype.get());
                }
            }
            throw new UnsummableValuesException(left, right, n.getLocation());
        }
        case AND:
        case OR:
            return typecheckBooleanBinExpr(n);
        case EQ:
        case NEQ:
            return typecheckEqualityBinExpr(n);
        case GEQ:
        case GT:
        case LEQ:
        case LT:
            return typecheckComparisonBinExpr(n);
        case MOD:
        case MUL:
        case SUB:
        case DIV:
        case HIGH_MUL:
            return typecheckIntegerBinExpr(n);
        default:
            break;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Result visit(LiteralBoolExprNode n) {
        return assignType(n, ExpandedType.boolType);
    }

    @Override
    public Result visit(LiteralCharExprNode n) {
        return assignType(n, ExpandedType.intType);
    }

    @Override
    public Result visit(LiteralIntExprNode n) {
        return assignType(n, ExpandedType.intType);
    }

    @Override
    public Result visit(LiteralStringExprNode n) {
        return assignType(n, new ArrayType(PrimitiveType.intDefault));
    }

    @Override
    public Result visit(BoolNegExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isSubtypeOfBool()) {
            return assignType(n, ExpandedType.boolType);
        }
        throw new TypeMismatchException(type, ExpandedType.boolType,
                n.expr.getLocation());
    }

    @Override
    public Result visit(IntNegExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isSubtypeOfInt()) {
            return assignType(n, ExpandedType.intType);
        }
        throw new TypeMismatchException(type, ExpandedType.intType,
                n.expr.getLocation());
    }

    @Override
    public Result visit(LengthExprNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();
        if (type.isSubtypeOfArray()) {
            return assignType(n, ExpandedType.intType);
        }
        throw new TypeMismatchException(type, ExpandedType.intType,
                n.expr.getLocation());
    }

    @Override
    public Result visit(ArrayAccessExprNode n) {
        ExpandedType arrayType = n.child.accept(this).assertFirst();
        ExpandedType indexType = n.index.accept(this).assertFirst();

        if (!indexType.isSubtypeOfInt()) {
            throw new TypeMismatchException(indexType, ExpandedType.intType,
                    n.index.getLocation());
        }

        if (!arrayType.isSubtypeOfArray()) {
            throw new TypeMismatchException(arrayType,
                    ExpandedType.voidArrayType, n.index.getLocation());
        }

        OrdinaryType innerArrayType = arrayType.getInnerArrayType();
        return assignType(n, innerArrayType);
    }

    @Override
    public Result visit(VariableAccessExprNode n) {
        Optional<OrdinaryType> optionalVar = context.getVar(n.identifier);
        if (optionalVar.isPresent()) {
            return assignType(n, optionalVar.get());
        }
        throw new UnboundIdentifierException(n.identifier, n.getLocation());
    }

}
