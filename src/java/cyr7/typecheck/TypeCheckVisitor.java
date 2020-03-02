package cyr7.typecheck;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.AndExprNode;
import cyr7.ast.expr.binexpr.BinExprNode;
import cyr7.ast.expr.binexpr.DivExprNode;
import cyr7.ast.expr.binexpr.EqualsExprNode;
import cyr7.ast.expr.binexpr.GTEExprNode;
import cyr7.ast.expr.binexpr.GTExprNode;
import cyr7.ast.expr.binexpr.HighMultExprNode;
import cyr7.ast.expr.binexpr.LTEExprNode;
import cyr7.ast.expr.binexpr.LTExprNode;
import cyr7.ast.expr.binexpr.MultExprNode;
import cyr7.ast.expr.binexpr.NotEqualsExprNode;
import cyr7.ast.expr.binexpr.OrExprNode;
import cyr7.ast.expr.binexpr.RemExprNode;
import cyr7.ast.expr.binexpr.SubExprNode;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
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
import cyr7.exceptions.LexerException;
import cyr7.exceptions.ParserException;
import cyr7.exceptions.semantics.ArrayTypeExpectedException;
import cyr7.exceptions.semantics.DuplicateIdentifierException;
import cyr7.exceptions.semantics.EarlyReturnException;
import cyr7.exceptions.semantics.ExpectedFunctionException;
import cyr7.exceptions.semantics.InterfaceFileNotFoundException;
import cyr7.exceptions.semantics.InvalidArgumentException;
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
import cyr7.semantics.ArrayType;
import cyr7.semantics.Context;
import cyr7.semantics.ExpandedType;
import cyr7.semantics.FunctionType;
import cyr7.semantics.HashMapStackContext;
import cyr7.semantics.OrdinaryType;
import cyr7.semantics.PrimitiveType;
import cyr7.semantics.ResultType;
import cyr7.semantics.UnitType;
import cyr7.util.OneOfThree;
import cyr7.visitor.AbstractVisitor;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class TypeCheckVisitor extends
    AbstractVisitor<OneOfThree<ExpandedType, ResultType, Optional<Void>>> {

    /**
     * Returns the supertype of two types, if such a relation exists.
     *
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
     *
     * Side effects: this method has no side effects.
     *
     * @param n
     * @return An empty optional
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(FunctionDeclNode n) {
        ExpandedType outputTypes =
            context.getFn(n.header.identifier).get().output;
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

    /**
     * Returns the function type of a function header.
     *
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

        ExpandedType output = new ExpandedType(n.returnTypes.stream()
            .map(v -> {
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
     * @throws DuplicateIdentifierException if n.identifier exists within the
     * context, or if an interface declares a function with the same name and
     * conflicting type.
     * @return
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
            throw new DuplicateIdentifierException(n.identifier,
                n.getLocation().get());
        }
        context.addFn(functionName, functionType);
        return OneOfThree.ofFirst(functionType.output);
    }

    /**
     * Type check an Ixi program by visiting each function declaration.
     *
     * Side effects: this method adds to {@code interfaceFuncDecls} a mapping
     * [header.identifier -> functionType]
     *
     * @param n
     * @return Nothing
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(IxiProgramNode n) {
        n.functionDeclarations.forEach(header -> {
            FunctionType functionType = functionTypeOf(header);

            if (interfaceFuncDecls.containsKey(header.identifier)
                && !interfaceFuncDecls.get(header.identifier).equals(functionType)) {
                throw new DuplicateIdentifierException(header.identifier,
                    header.getLocation().get());
            } else {
                interfaceFuncDecls.put(header.identifier, functionType);
            }
        });
        return OneOfThree.ofThird(Optional.empty());
    }

    /**
     * Type check a use node by type checking the ixi file associated with the
     * use node.
     *
     * Side effects: this method adds function mappings to
     * {@code interfaceFuncDecls}
     *
     * @param n
     * @return Nothing
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(UseNode n) {
        try {
            Reader reader = fileOpener.openIxiLibraryFile(n.interfaceName);
            IxiProgramNode interfaceNode =
                (IxiProgramNode) ParserUtil.parseNode(reader,
                    n.interfaceName + ".ixi", true);
            interfaceNode.accept(this);
            return OneOfThree.ofThird(Optional.empty());
        } catch (ParserException | LexerException | SemanticException e) {
            throw e;
        } catch (Exception e) {
            throw new InterfaceFileNotFoundException(n.interfaceName,
                n.getLocation().get());
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
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(VarDeclNode n) {
        if (context.contains(n.identifier)) {
            throw new DuplicateIdentifierException(n.identifier,
                n.getLocation().get());
        }
        ExpandedType type = n.typeExpr.accept(this).assertFirst();
        context.addVar(n.identifier, type.getOrdinaryType());
        return OneOfThree.ofFirst(type);
    }

    /**
     * Type check an Xi Program Node.
     *
     * 1. Type-check use declarations
     * 2. Perform a first pass over function headers
     * 3. Type-check
     *
     * Side effects: this method adds the length function to the context.
     *
     * @param n
     * @return Nothing
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(XiProgramNode n) {
        context.addFn("length", new FunctionType(
            new ExpandedType(
                new ArrayType(UnitType.unitValue)),
            ExpandedType.intType));

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

        return OneOfThree.ofThird(Optional.empty());
    }

    // Type

    /**
     * Returns the type of n
     *
     * Side effects: this method is pure
     *
     * @param n
     * @return
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(PrimitiveTypeNode n) {
        switch (n.type) {
            case BOOL:
                return OneOfThree.ofFirst(ExpandedType.boolType);
            case INT:
                return OneOfThree.ofFirst(ExpandedType.intType);
            default:
                // fallback
                return OneOfThree.ofFirst(ExpandedType.intType);
        }
    }

    /**
     * Return the type of n
     *
     * Side effects: this method is pure
     *
     * @param n
     * @return
     */
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

        if (!type.isOrdinary()) {
            throw new OrdinaryTypeExpectedException(n.getLocation().get());
        }

        return OneOfThree.ofFirst(
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
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(ArrayDeclStmtNode n) {
        if (context.contains(n.identifier)) {
            throw new DuplicateIdentifierException(
                n.identifier, n.getLocation().get());
        }

        ExpandedType expectedArray = n.type.accept(this).assertFirst();

        if (!expectedArray.isArray()) {
            throw new ArrayTypeExpectedException(n.getLocation().get());
        }

        context.addVar(n.identifier, expectedArray.getOrdinaryType());
        return OneOfThree.ofSecond(ResultType.UNIT);
    }

    /**
     * Side effects: This method has no side effects
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(AssignmentStmtNode n) {
        ExpandedType lhsType = n.lhs.accept(this).assertFirst();
        ExpandedType rhsType = n.rhs.accept(this).assertFirst();

        if (rhsType.isASubtypeOf(lhsType)) {
            return OneOfThree.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(lhsType, rhsType,
                n.rhs.getLocation().get());
        }
    }

    /**
     * Side effects: This method has no side effects
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(BlockStmtNode n) {
        context.push();
        Iterator<StmtNode> stmtIterator = n.statements.iterator();
        while (stmtIterator.hasNext()) {
            StmtNode stmt = stmtIterator.next();
            ResultType type = stmt.accept(this).assertSecond();
            if (stmtIterator.hasNext() && type == ResultType.VOID) {
                throw new EarlyReturnException(stmt.getLocation().get());
            } else if (!stmtIterator.hasNext()) {
                context.pop();
                return OneOfThree.ofSecond(type);
            }
        }
        context.pop();
        return OneOfThree.ofSecond(ResultType.UNIT);
    }

    /**
     * Typecheck a statement of the form:
     *
     * `_ = 4;`
     *
     * Side effects: This method has no side effects
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(ExprStmtNode n) {
        ExpandedType type = n.expr.accept(this).assertFirst();

        if (!(n.expr instanceof FunctionCallExprNode)) {
            throw new ExpectedFunctionException(n.expr.getLocation().get());
        }

        if (type.isOrdinary()) {
            // The type of a function call expr node is the return type of the
            // function. (view page 3 of types.pdf)
            return OneOfThree.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(type,
                ExpandedType.unitOrdinaryType, n.expr.getLocation().get());
        }
    }

    /**
     * Side effects: This method has no side effects
     *
     * @param n
     * @return The least upper bound of the two sub block.
     */
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

    /**
     * Side effects: This method adds [n.identifier -> typeOf(n)] to the context
     * for each variable declared.
     *
     * @param n
     * @return Unit Statement Type
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(MultiAssignStmtNode n) {
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
            return OneOfThree.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(types, declTypes,
                n.initializer.getLocation().get());
        }
    }

    /**
     * Side effects: This method has no side effects.
     *
     * @param n
     * @return Unit Statement Type
     */
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

    /**
     * Side effects: This method has no side effects.
     *
     * @param n
     * @return Void Statement Type
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(ReturnStmtNode n) {
        Optional<ExpandedType> maybeTypes = context.getRet();
        assert maybeTypes.isPresent();

        if (maybeTypes.get().isUnit() && !n.exprs.isEmpty()) {
            throw new ReturnValueInUnitFunctionException(n.getLocation().get());
        }

        ExpandedType expected = maybeTypes.get();
        ExpandedType exprType = new ExpandedType(n.exprs.stream()
            .map(e -> {
                ExpandedType t = e.accept(this).assertFirst();
                if (!t.isOrdinary()) {
                    throw new InvalidReturnValueException(e.getLocation().get());
                }
                return t.getOrdinaryType();
            }).collect(Collectors.toList()));

        if (exprType.isASubtypeOf(expected)) {
            return OneOfThree.ofSecond(ResultType.VOID);
        } else {
            throw new TypeMismatchException(exprType, expected,
                n.getLocation().get());
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
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(VarDeclStmtNode n) {
        n.varDecl.accept(this);
        return OneOfThree.ofSecond(ResultType.UNIT);
    }

    /**
     * Side effects: this method adds a mapping [n.identifier -> typeOf(n)] to
     * the context.
     *
     * @param n
     * @return
     */
    @Override
    public OneOfThree<ExpandedType, ResultType, Optional<Void>> visit(VarInitStmtNode n) {
        // The initializer must be type-checked before the variable declaration
        // since otherwise it would be possible to do recursive variable
        // declarations
        ExpandedType initializedType = n.initializer.accept(this).assertFirst();

        ExpandedType varDeclType = n.varDecl.accept(this).assertFirst();

        if (!initializedType.isOrdinary()) {
            throw new OrdinaryTypeExpectedException(
                n.initializer.getLocation().get());
        }

        if (initializedType.isASubtypeOf(varDeclType)) {
            assert varDeclType.isOrdinary();
            return OneOfThree.ofSecond(ResultType.UNIT);
        } else {
            throw new TypeMismatchException(initializedType, varDeclType,
                n.getLocation().get());
        }
    }

    /**
     * Type checks a while statement node
     *
     * @param n
     * @return A statement node
     */
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
            return OneOfThree.ofFirst(
                new ExpandedType(ArrayType.voidArrayDefault));
        }

        List<ExpandedType> arrayTypes = n.arrayVals.stream().map(e -> {
            ExpandedType type = e.accept(this).assertFirst();
            if (!type.isOrdinary()) {
                throw new OrdinaryTypeExpectedException(
                    e.getLocation().get());
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

        if (!arrayType.get().isOrdinary()) {
            throw new OrdinaryTypeExpectedException(n.getLocation().get());
        }

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
            .map(e -> {
                ExpandedType type = e.accept(this).assertFirst();
                if (!type.isOrdinary()) {
                    throw new OrdinaryTypeExpectedException(
                        e.getLocation().get());
                } else {
                    return type.getOrdinaryType();
                }
            }).collect(Collectors.toList()));

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

        if (!left.isOrdinary()) {
            throw new OrdinaryTypeExpectedException(
                n.left.getLocation().get());
        }
        if (!right.isOrdinary()) {
            throw new OrdinaryTypeExpectedException(
                n.right.getLocation().get());
        }

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

        if (left.isVoid() && right.isVoid()) {
            return OneOfThree.ofFirst(ExpandedType.genericAddType);
        }

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
            new ExpandedType(new ArrayType(PrimitiveType.intDefault)));
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
