package cyr7.ir;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
import cyr7.ast.expr.unaryexpr.LengthExprNode;
import cyr7.ast.stmt.ArrayDeclStmtNode;
import cyr7.ast.stmt.AssignmentStmtNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.ExprStmtNode;
import cyr7.ast.stmt.IfElseStmtNode;
import cyr7.ast.stmt.MultiAssignStmtNode;
import cyr7.ast.stmt.ProcedureStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
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
import cyr7.ir.interpret.Configuration;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.parser.ParserUtil;
import cyr7.semantics.types.ExpandedType;
import cyr7.semantics.types.ResultType;
import cyr7.util.OneOfTwo;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AstToIrVisitor extends AbstractVisitor<OneOfTwo<IRExpr, IRStmt>> {

    private final IdGenerator generator;

    public AstToIrVisitor(IdGenerator generator) {
        this.generator = generator;
    }

    private String functionName(String n, List<ExpandedType> paramTypes,
                                ExpandedType returnType) {
        String name = "_I" + n.replace("_", "__") + "_";
        StringBuffer params = new StringBuffer();
        paramTypes.forEach(t -> params.append(typeIdentifier(t)));
        return name + typeIdentifier(returnType) + params.toString();
    }

    private String typeIdentifier(ExpandedType t) {
        if (t.isSubtypeOfInt()) {
            return "i";
        } else if (t.isSubtypeOfBool()) {
            return "b";
        } else if (t.isUnit()) {
            return "p";
        } else if (t.isSubtypeOfArray()) {
            return "a"
                + typeIdentifier(new ExpandedType(t.getInnerArrayType()));
        } else if (t.isTuple()) {
            StringBuffer types = new StringBuffer();
            t.getTypes().forEach(
                type -> types
                    .append(typeIdentifier(new ExpandedType(type))));
            return "t" + t.getTypes().size() + types.toString();
        } else {
            throw new IllegalArgumentException("invalid type for function");
        }
    }

    // Top Level

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(FunctionDeclNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        List<IRStmt> seq = new ArrayList<>();
        for (int i = 0; i < n.header.args.size(); i++) {
            VarDeclNode node = n.header.args.get(i);
            seq.add(
                make.IRMove(
                    make.IRTemp(node.identifier),
                    make.IRTemp(generator.argTemp(i))));
        }
        seq.add(n.block.accept(this).assertSecond());

        if (n.getResultType() == ResultType.UNIT) {
            seq.add(make.IRReturn());
        }
        return OneOfTwo.ofSecond(make.IRSeq(seq));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(FunctionHeaderDeclNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IxiProgramNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(UseNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VarDeclNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        return OneOfTwo.ofSecond(
            make.IRMove(make.IRTemp(n.identifier), make.IRConst(0)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(XiProgramNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String file = n.getLocation().getUnit();
        int dotIndex = file.lastIndexOf('.');
        if (dotIndex != -1) {
            file = file.substring(0, file.lastIndexOf('.'));
        }

        IRCompUnit program = make.IRCompUnit(file);
        for (FunctionDeclNode fun : n.functions) {
            IRStmt funStmts = fun.accept(this).assertSecond();
            List<ExpandedType> paramTypes = fun.header.args.stream()
                .map(vdn -> vdn.getType()).collect(Collectors.toList());
            String funcName = this.functionName(fun.header.identifier,
                paramTypes, fun.header.getType().output);
            program.appendFunc(make.IRFuncDecl(funcName, funStmts));
        }
        return OneOfTwo.ofSecond(program);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(PrimitiveTypeNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        return OneOfTwo.ofFirst(make.IRConst(0));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(TypeExprArrayNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        List<IRStmt> commands = new ArrayList<IRStmt>();

        if (n.size.isEmpty()) {
            return OneOfTwo.ofFirst(make.IRConst(0));
        }
        String memBlockStart = generator.newTemp();
        String arrSize = generator.newTemp();
        String pointerStart = generator.newTemp();
        String lh = generator.newLabel();
        String lt = generator.newLabel();
        String lf = generator.newLabel();

        IRExpr size = n.size.get().accept(this).assertFirst();
        commands.add(make.IRMove(make.IRTemp(arrSize), size));

        IRExpr spaceNeeded = make.IRBinOp(OpType.MUL,
            make.IRConst(Configuration.WORD_SIZE),
            make.IRBinOp(OpType.ADD, make.IRTemp(arrSize), make.IRConst(1)));

        IRExpr memLoc = make.IRCall(make.IRName("_xi_alloc"), spaceNeeded);
        commands.add(make.IRMove(make.IRTemp(memBlockStart), memLoc));

        commands.add(make.IRMove(make.IRMem(make.IRTemp(memBlockStart)),
            make.IRTemp(arrSize)));

        commands.add(make.IRMove(make.IRTemp(pointerStart),
            make.IRBinOp(OpType.ADD, make.IRTemp(memBlockStart),
                make.IRConst(Configuration.WORD_SIZE))));

        // Iterate through all elements and recursively create child arrays
        IRExpr guard = make.IRBinOp(OpType.GT, make.IRTemp(arrSize),
            make.IRConst(0));

        // Memory location: 334312
        IRExpr createArray = n.child.accept(this).assertFirst();

        IRExpr valueLoc = make
            .IRMem(make.IRBinOp(OpType.ADD, make.IRTemp(pointerStart),
                make.IRBinOp(OpType.MUL,
                    make.IRConst(Configuration.WORD_SIZE),
                    make.IRTemp(arrSize))));

        IRStmt block = make.IRSeq(
            make.IRMove(make.IRTemp(arrSize),
                make.IRBinOp(OpType.SUB, make.IRTemp(arrSize),
                    make.IRConst(1))),
            make.IRMove(valueLoc, createArray));

        return OneOfTwo.ofFirst(
            make.IRESeq(
                make.IRSeq(make.IRLabel(lh),
                    make.IRCJump(guard, lt, lf),
                    make.IRLabel(lt),
                    make.IRSeq(block,
                        make.IRJump(make.IRName(lh))),
                    make.IRLabel(lf)),
                make.IRTemp(pointerStart)));
    }

    // Statements

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ArrayDeclStmtNode n) {
        IRExpr val = n.type.accept(this).assertFirst();
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        return OneOfTwo.ofSecond(
            make.IRSeq(make.IRMove(make.IRTemp(n.identifier), val)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(AssignmentStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        IRExpr lhs = n.lhs.accept(this).assertFirst();
        IRExpr rhs = n.rhs.accept(this).assertFirst();
        return OneOfTwo.ofSecond(make.IRMove(lhs, rhs));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(BlockStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        List<IRStmt> stmts = n.statements.stream()
            .map(stmt -> stmt.accept(this).assertSecond())
            .collect(Collectors.toList());
        return OneOfTwo.ofSecond(make.IRSeq(stmts));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ExprStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        IRExpr e = n.expr.accept(this).assertFirst();
        return OneOfTwo.ofSecond(make.IRExp(e));
    }

    /**
     * Translates if (e) s1 else s2
     */
    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IfElseStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String lt = generator.newLabel();
        String lf = generator.newLabel();
        String end = generator.newLabel();
        List<IRStmt> commands = new ArrayList<>();

        commands.add(
            n.guard.accept(new CTranslationVisitor(generator, lt, lf)));
        commands.add(make.IRLabel(lf));
        if (n.elseBlock.isPresent()) {
            commands.add(n.elseBlock.get().accept(this).assertSecond());
        }
        commands.add(make.IRJump(make.IRName(end)));
        commands.add(make.IRLabel(lt));
        commands.add(n.ifBlock.accept(this).assertSecond());
        commands.add(make.IRLabel(end));
        return OneOfTwo.ofSecond(make.IRSeq(commands));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(MultiAssignStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        List<IRStmt> commands = new ArrayList<>();
        IRExpr functionCall = n.initializer.accept(this).assertFirst();
        commands.add(make.IRExp(functionCall));

        AtomicInteger retNum = new AtomicInteger();
        for (Optional<VarDeclNode> var : n.varDecls) {
            if (var.isPresent()) {
                commands.add(make.IRMove(
                    make.IRTemp(var.get().identifier),
                    make.IRTemp(generator.retTemp(retNum.get()))));
            }
            retNum.incrementAndGet();
        }
        return OneOfTwo.ofSecond(make.IRSeq(commands));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ProcedureStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        return OneOfTwo.ofSecond(
            make.IRExp(n.procedureCall.accept(this).assertFirst()));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ReturnStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());


        AtomicInteger retNum = new AtomicInteger();
        List<IRStmt> rets = n.exprs.stream()
            .map(stmt -> make.IRMove(
                make.IRTemp(generator.retTemp(retNum.getAndIncrement())),
                stmt.accept(this).assertFirst()))
            .collect(Collectors.toList());
        rets.add(make.IRReturn());
        return OneOfTwo.ofSecond(make.IRSeq(rets));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VarDeclStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        // Initialize it to 0
        return OneOfTwo.ofSecond(make.IRMove(
            make.IRTemp(n.varDecl.identifier),
            make.IRConst(0)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VarInitStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String name = n.varDecl.identifier;
        IRExpr expr = n.initializer.accept(this).assertFirst();

        return OneOfTwo.ofSecond(make.IRMove(make.IRTemp(name), expr));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(WhileStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String lh = generator.newLabel();
        String lt = generator.newLabel();
        String lf = generator.newLabel();

        IRStmt guard = n.guard.accept(
            new CTranslationVisitor(generator, lt, lf));
        IRStmt block = n.block.accept(this).assertSecond();

        return OneOfTwo.ofSecond(make.IRSeq(
            make.IRLabel(lh), guard,
            make.IRLabel(lt), make.IRSeq(block,
                make.IRJump(make.IRName(lh))),
            make.IRLabel(lf)));
    }

    // Expressions

    private OneOfTwo<IRExpr, IRStmt> binOp(IRBinOp.OpType opType,
                                           BinExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        IRExpr left = n.left.accept(this).assertFirst();
        IRExpr right = n.right.accept(this).assertFirst();

        return OneOfTwo.ofFirst(make.IRBinOp(opType, left, right));
    }

    /**
     * Translates f(e1, e2, e3, ...)
     */
    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(FunctionCallExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        List<IRExpr> params = n.parameters.stream()
            .map(stmt -> stmt.accept(this).assertFirst())
            .collect(Collectors.toList());
        List<ExpandedType> paramTypes =
            n.parameters.stream().map(vdn -> vdn.getType()).collect(Collectors.toList());
        String encodedName = functionName(n.identifier, paramTypes,
            n.getType());
        return OneOfTwo.ofFirst(make.IRCall(make.IRName(encodedName), params));
    }

    /**
     * Translates arr[index].
     */
    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ArrayAccessExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String arrTemp = generator.newTemp();
        String indexTemp = generator.newTemp();
        String lengthTemp = generator.newTemp();
        String lt = generator.newLabel();
        String lf = generator.newLabel();
        List<IRStmt> commands = new ArrayList<IRStmt>();

        IRExpr index = n.index.accept(this).assertFirst();
        IRExpr arr = n.child.accept(this).assertFirst();

        commands.add(make.IRMove(make.IRTemp(indexTemp), index));
        commands.add(make.IRMove(make.IRTemp(arrTemp), arr));

        IRExpr length = make.IRMem(
            make.IRBinOp(OpType.SUB, make.IRTemp(arrTemp),
                make.IRConst(Configuration.WORD_SIZE)));
        commands.add(make.IRMove(make.IRTemp(lengthTemp), length));

        // Check for out of bounds
        commands.add(make.IRCJump(
            make.IRBinOp(OpType.AND,
                make.IRBinOp(OpType.LEQ,
                    make.IRConst(0), make.IRTemp(indexTemp)),
                make.IRBinOp(OpType.LT,
                    make.IRTemp(indexTemp), make.IRTemp(lengthTemp))),
            lt, lf));
        commands.add(make.IRLabel(lf));
        commands.add(make.IRExp(make.IRCall(make.IRName("_xi_out_of_bounds"))));
        commands.add(make.IRLabel(lt));

        IRExpr val = make.IRMem(
            make.IRBinOp(OpType.ADD, make.IRTemp(arrTemp),
                make.IRBinOp(OpType.MUL,
                    make.IRTemp(indexTemp), make.IRConst(8))));
        return OneOfTwo.ofFirst(make.IRESeq(make.IRSeq(commands), val));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VariableAccessExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        return OneOfTwo.ofFirst(make.IRTemp(n.identifier));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(AddExprNode n) {
        if (n.getType().isSubtypeOfInt()) {
            return binOp(IRBinOp.OpType.ADD, n);
        }
        assert n.getType().isSubtypeOfArray();

        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        List<IRStmt> seq = new ArrayList<>();

        String leftArrAddr = generator.newTemp();
        String leftArrSize = generator.newTemp();

        String rightArrAddr = generator.newTemp();
        String rightArrSize = generator.newTemp();

        String summedArrSize = generator.newTemp();
        String summedArrAddr = generator.newTemp();

        seq.add(make.IRMove(make.IRTemp(leftArrAddr),
            n.left.accept(this).assertFirst()));

        seq.add(make.IRMove(make.IRTemp(rightArrAddr),
            n.right.accept(this).assertFirst()));

        seq.add(make.IRMove(
            make.IRTemp(leftArrSize),
            make.IRMem(
                make.IRBinOp(OpType.SUB,
                    make.IRTemp(leftArrAddr),
                    make.IRConst(8))
            )));
        seq.add(make.IRMove(
            make.IRTemp(rightArrSize),
            make.IRMem(
                make.IRBinOp(OpType.SUB,
                    make.IRTemp(rightArrAddr),
                    make.IRConst(8))
            )));
        seq.add(make.IRMove(
            make.IRTemp(summedArrSize),
            make.IRBinOp(OpType.ADD,
                make.IRTemp(leftArrSize),
                make.IRTemp(rightArrSize))
        ));

        // Space for concatenated array
        seq.add(make.IRMove(
            make.IRTemp(summedArrAddr),
            make.IRCall(
                make.IRName("_xi_alloc"),
                make.IRBinOp(OpType.MUL,
                    make.IRConst(8),
                    make.IRBinOp(OpType.ADD,
                        make.IRTemp(summedArrSize), make.IRConst(1)))
            )));

        seq.add(make.IRMove(
            make.IRMem(make.IRTemp(summedArrAddr)),
            make.IRTemp(summedArrSize)));
        // Move array address pointer to actual start of array
        seq.add(make.IRMove(
            make.IRTemp(summedArrAddr),
            make.IRBinOp(OpType.ADD,
                make.IRTemp(summedArrAddr),
                make.IRConst(8))));

        /**
         * i = 0;
         * while (i < leftArr.size) {
         *      summed[i] = leftArr[i];
         *      i++;
         * }
         * j = 0;
         * while (j < rightArr.size) {
         *      summed[leftArr.size + j] = rightArr[j];
         *      j++;
         * }
         */

        String leftSumming = generator.newLabel();
        String leftBody = generator.newLabel();
        String i = generator.newTemp();
        String rightSumming = generator.newLabel();
        String rightBody = generator.newLabel();
        String j = generator.newTemp();
        String exit = generator.newLabel();

        seq.add(make.IRMove(make.IRTemp(i), make.IRConst(0)));
        seq.add(make.IRMove(make.IRTemp(j), make.IRConst(0)));
        
        seq.add(make.IRLabel(leftSumming));
        // While i < leftArrSize
        seq.add(make.IRCJump(
            make.IRBinOp(OpType.LT, make.IRTemp(i), make.IRTemp(leftArrSize)),
            leftBody,
            rightSumming));
        seq.add(make.IRLabel(leftBody));
        // Move value at [leftArrAddr + 8 * i] into [summedArrAddr + 8 * i]
        seq.add(make.IRMove(
            make.IRMem(
                make.IRBinOp(OpType.ADD,
                    make.IRTemp(summedArrAddr),
                    make.IRBinOp(OpType.MUL,
                        make.IRTemp(i),
                        make.IRConst(8)))),
            make.IRMem(
                make.IRBinOp(OpType.ADD,
                make.IRTemp(leftArrAddr),
                make.IRBinOp(OpType.MUL,
                    make.IRTemp(i),
                    make.IRConst(8))))));
        // i++
        seq.add(make.IRMove(
            make.IRTemp(i),
            make.IRBinOp(OpType.ADD,
                make.IRTemp(i),
                make.IRConst(1))));

        seq.add(make.IRJump(make.IRName(leftSumming)));

        seq.add(make.IRLabel(rightSumming));
        seq.add(make.IRCJump(
            make.IRBinOp(OpType.LT, make.IRTemp(j),
                make.IRTemp(rightArrSize)),
            rightBody,
            exit));
        seq.add(make.IRLabel(rightBody));
        seq.add(make.IRMove(
            make.IRMem(
                make.IRBinOp(OpType.ADD,
                    make.IRTemp(summedArrAddr),
                    make.IRBinOp(OpType.MUL,
                        make.IRBinOp(OpType.ADD,
                            make.IRTemp(j),
                            make.IRTemp(leftArrSize)),
                        make.IRConst(8)))),
            make.IRMem(
                make.IRBinOp(OpType.ADD,
                make.IRTemp(rightArrAddr),
                make.IRBinOp(OpType.MUL,
                    make.IRTemp(j),
                    make.IRConst(8))))));
        seq.add(make.IRMove(make.IRTemp(j),
            make.IRBinOp(OpType.ADD, make.IRTemp(j), make.IRConst(1))));
        seq.add(make.IRJump(make.IRName(leftSumming)));

        seq.add(make.IRLabel(exit));
        return OneOfTwo.ofFirst(make.IRESeq(make.IRSeq(seq),
            make.IRTemp(summedArrAddr)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(AndExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String t = generator.newTemp();
        String lt = generator.newLabel();
        String lf = generator.newLabel();

        IRStmt e1CTranslated = n.left
            .accept(new CTranslationVisitor(generator, lt, lf));
        IRExpr e2 = n.right.accept(this).assertFirst();

        return OneOfTwo.ofFirst(make.IRESeq(make.IRSeq(
            make.IRMove(make.IRTemp(t), make.IRConst(0)),
            e1CTranslated,
            make.IRLabel(lt),
            make.IRMove(make.IRTemp(t), e2),
            make.IRLabel(lf)),
            make.IRTemp(t)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(DivExprNode n) {
        return binOp(IRBinOp.OpType.DIV, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(EqualsExprNode n) {
        return binOp(IRBinOp.OpType.EQ, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(GTEExprNode n) {
        return binOp(IRBinOp.OpType.GEQ, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(GTExprNode n) {
        return binOp(IRBinOp.OpType.GT, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(HighMultExprNode n) {
        return binOp(IRBinOp.OpType.HMUL, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LTEExprNode n) {
        return binOp(IRBinOp.OpType.LEQ, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LTExprNode n) {
        return binOp(IRBinOp.OpType.LT, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(MultExprNode n) {
        return binOp(IRBinOp.OpType.MUL, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(NotEqualsExprNode n) {
        return binOp(IRBinOp.OpType.NEQ, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(OrExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String t = generator.newTemp();
        String lt = generator.newLabel();
        String lf = generator.newLabel();

        IRStmt e1CTranslated = n.left
            .accept(new CTranslationVisitor(generator, lt, lf));
        IRExpr e2 = n.right.accept(this).assertFirst();

        return OneOfTwo.ofFirst(make
            .IRESeq(make.IRSeq(
                make.IRMove(make.IRTemp(t), make.IRConst(1)),
                e1CTranslated,
                make.IRLabel(lf),
                make.IRMove(make.IRTemp(t), e2),
                make.IRLabel(lt)),
                make.IRTemp(t)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(RemExprNode n) {
        return binOp(IRBinOp.OpType.MOD, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(SubExprNode n) {
        return binOp(IRBinOp.OpType.SUB, n);
    }

    /**
     * Translates an array literal with known IRExpr values.
     *
     * @param vals
     * @param location
     * @return
     */
    private OneOfTwo<IRExpr, IRStmt> visitArr(List<IRExpr> vals,
                                              Location location) {
        IRNodeFactory make = new IRNodeFactory_c(location);

        String memBlockStart = generator.newTemp();
        String pointerStart = generator.newTemp();
        int size = vals.size();

        IRExpr spaceNeeded = make.IRBinOp(OpType.MUL,
            make.IRConst(Configuration.WORD_SIZE),
            make.IRBinOp(OpType.ADD, make.IRConst(size), make.IRConst(1)));

        List<IRStmt> commands = new ArrayList<IRStmt>();

        IRExpr memLoc = make.IRCall(make.IRName("_xi_alloc"), spaceNeeded);

        commands.add(make.IRMove(make.IRTemp(memBlockStart), memLoc));
        commands.add(make.IRMove(make.IRMem(make.IRTemp(memBlockStart)),
            make.IRConst(size)));
        // Move size into the start of requested memory block.

        commands.add(make.IRMove(make.IRTemp(pointerStart),
            make.IRBinOp(OpType.ADD, make.IRTemp(memBlockStart),
                make.IRConst(Configuration.WORD_SIZE))));

        // Setting the values of the indices in memory
        for (int i = 0; i < size; i++) {
            IRExpr valueLoc = make
                .IRMem(make.IRBinOp(OpType.ADD, make.IRTemp(pointerStart),
                    make.IRBinOp(OpType.MUL,
                        make.IRConst(Configuration.WORD_SIZE),
                        make.IRConst(i))));
            commands.add(make.IRMove(valueLoc, vals.get(i)));
        }
        return OneOfTwo.ofFirst(
            make.IRESeq(make.IRSeq(commands), make.IRTemp(pointerStart)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralArrayExprNode n) {
        List<IRExpr> values = n.arrayVals.stream()
            .map(stmt -> stmt.accept(this).assertFirst())
            .collect(Collectors.toList());
        return visitArr(values, n.getLocation());
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralBoolExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        return OneOfTwo.ofFirst(make.IRConst(n.contents ? 1 : 0));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralCharExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        assert n.contents.length() == 1;
        return OneOfTwo.ofFirst(make.IRConst(n.contents.charAt(0)));
    }


    /**
     * Turns the number into a contant literal.
     */
    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralIntExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        return OneOfTwo.ofFirst(make.IRConst(Long.parseLong(n.contents)));
    }

    /**
     * TODO: Check if it is better to transform to a direct array literal.
     */
    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralStringExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        List<IRExpr> vals = new ArrayList<>();
        for (char c : n.contents.toCharArray()) {
            vals.add(make.IRConst(c));
        }
        return visitArr(vals, n.getLocation());
    }

    /**
     * Converts to true ^ b, where b is any boolean.
     */
    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(BoolNegExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        IRExpr e = n.expr.accept(this).assertFirst();
        return OneOfTwo
            .ofFirst(make.IRBinOp(IRBinOp.OpType.XOR, make.IRConst(1), e));
    }

    /**
     * Transforms to 0-e.
     */
    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IntNegExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        IRExpr e = n.expr.accept(this).assertFirst();
        return OneOfTwo
            .ofFirst(make.IRBinOp(IRBinOp.OpType.SUB, make.IRConst(0), e));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LengthExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        IRExpr e = n.expr.accept(this).assertFirst();
        return OneOfTwo.ofFirst(
            make.IRMem(
                make.IRBinOp(OpType.SUB, e,
                    make.IRConst(Configuration.WORD_SIZE))
            )
        );
    }

}
