package cyr7.ir;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.ExprNode;
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
import cyr7.ir.nodes.*;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.semantics.types.ExpandedType;
import cyr7.semantics.types.PrimitiveType;
import cyr7.util.OneOfTwo;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class AstToIrVisitor extends AbstractVisitor<OneOfTwo<IRExpr, IRStmt>> {

    private final IdGenerator generator;

    public AstToIrVisitor() {
        this(new DefaultIdGenerator());
    }

    public AstToIrVisitor(IdGenerator generator) {
        this.generator = generator;
    }

    private String functionName(String n, List<ExpandedType> paramTypes,
            ExpandedType returnType) {
        String name = "_I" + n.replace("_", "__") + "_";
        List<String> params = new ArrayList<>();
        paramTypes.forEach(t -> params.add(typeIdentifier(t)));
        return name + typeIdentifier(returnType) + String.join("", params);
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
            List<String> types = new ArrayList<>();
            t.getTypes().forEach(
                    type -> types.add(typeIdentifier(new ExpandedType(type))));
            return "t" + t.getTypes().size() + String.join("", types);
        } else {
            throw new IllegalArgumentException("invalid type for function");
        }
    }

    // Top Level

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(FunctionDeclNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        List<IRStmt> seq = new ArrayList<>();
        List<ExpandedType> paramTypes = n.header.args.stream().map(vdn -> vdn.getType()).collect(Collectors.toList());
        seq.add(make.IRLabel(functionName(n.header.identifier, paramTypes, n.header.getType().output)));
        seq.add(n.block.accept(this).assertSecond());
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
        file = file.substring(0, file.lastIndexOf('.'));
        IRCompUnit program = make.IRCompUnit(file);

        for (FunctionDeclNode fun : n.functions) {
            program.appendFunc((IRFuncDecl) fun.accept(this).assertFirst());
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

        if (n.size.isEmpty()) {
            return OneOfTwo.ofFirst(make.IRConst(0));
        }
        String memBlock = generator.newTemp();
        String arrSize = generator.newTemp();
        String pointerStart = generator.newTemp();
        String lh = generator.newLabel();
        String lt = generator.newLabel();
        String lf = generator.newLabel();

        List<IRStmt> commands = new ArrayList<IRStmt>();

        IRExpr size = n.size.get().accept(this).assertFirst();
        commands.add(make.IRMove(make.IRTemp(arrSize), size));

        IRExpr spaceNeeded = make.IRBinOp(OpType.MUL,
                make.IRConst(Configuration.WORD_SIZE), make.IRBinOp(OpType.ADD,
                        make.IRTemp(arrSize), make.IRConst(1)));
        IRExpr memLoc = make.IRCall(make.IRName("_xi_malloc"), spaceNeeded);
        commands.add(make.IRMove(make.IRTemp(memBlock), memLoc));
        commands.add(make.IRMove(make.IRMem(make.IRTemp(memBlock)),
                make.IRTemp(arrSize)));
        commands.add(make.IRMove(make.IRTemp(pointerStart),
                make.IRBinOp(OpType.ADD, make.IRTemp(memBlock),
                        make.IRConst(Configuration.WORD_SIZE))));

        // Iterate through all elements and recursively create child arrays
        IRExpr grd = make.IRBinOp(OpType.GT, make.IRTemp(arrSize),
                make.IRConst(0));
        IRExpr createArray = n.child.accept(this).assertFirst();
        IRExpr valueLoc = make
                .IRMem(make.IRBinOp(OpType.ADD, make.IRTemp(pointerStart),
                        make.IRBinOp(OpType.MUL,
                                make.IRConst(Configuration.WORD_SIZE),
                                make.IRTemp(arrSize))));
        IRStmt blk = make.IRSeq(
                make.IRMove(make.IRTemp(arrSize),
                        make.IRBinOp(OpType.SUB, make.IRTemp(arrSize),
                                make.IRConst(1))),
                make.IRMove(valueLoc, createArray));

        return OneOfTwo
                .ofFirst(
                        make.IRESeq(
                                make.IRSeq(make.IRLabel(lh),
                                        make.IRCJump(grd, lt, lf),
                                        make.IRLabel(lt),
                                        make.IRSeq(blk,
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

        IRExpr rhs = n.rhs.accept(this).assertFirst();
        if (ExpandedType.voidArrayType.isASubtypeOf(n.lhs.getType())) {
            IRExpr lhs = n.lhs.accept(this).assertFirst();
            return OneOfTwo.ofSecond(make.IRMove(lhs, rhs));
        } else {
            VariableAccessExprNode var = (VariableAccessExprNode) n.lhs;
            return OneOfTwo
                    .ofSecond(make.IRMove(make.IRTemp(var.identifier), rhs));
        }
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

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IfElseStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String lt = generator.newLabel();
        String lf = generator.newLabel();
        List<IRStmt> commands = new ArrayList<>();
        commands.add(
                n.guard.accept(new CTranslationVisitor(generator, lt, lf)));
        commands.add(make.IRLabel(lt));
        commands.add(n.ifBlock.accept(this).assertSecond());
        commands.add(make.IRLabel(lf));
        if (n.elseBlock.isPresent()) {
            commands.add(n.elseBlock.get().accept(this).assertSecond());
        }
        return OneOfTwo.ofSecond(make.IRSeq(commands));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(MultiAssignStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        List<IRStmt> commands = new ArrayList<>();
        IRExpr functionCall = n.initializer.accept(this).assertFirst();
        commands.add(make.IRExp(functionCall));
        int retNum = 0;
        for (Optional<VarDeclNode> var : n.varDecls) {
            if (var.isPresent()) {
                commands.add(make.IRMove(make.IRTemp(var.get().identifier),
                        make.IRTemp(generator.retTemp(retNum))));
            }
            retNum++;
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
                        make.IRTemp("_RET" + retNum.getAndIncrement()),
                        stmt.accept(this).assertFirst()))
                .collect(Collectors.toList());
        rets.add(make.IRReturn());
        return OneOfTwo.ofSecond(make.IRSeq(rets));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VarDeclStmtNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        // Initialize it to 0
        return OneOfTwo.ofSecond(make.IRMove(make.IRTemp(n.varDecl.identifier),
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

        IRStmt grd = n.guard.accept(new CTranslationVisitor(generator, lt, lf));
        IRStmt blk = n.block.accept(this).assertSecond();

        return OneOfTwo.ofSecond(make.IRSeq(make.IRLabel(lh), grd,
                make.IRLabel(lt), make.IRSeq(blk, make.IRJump(make.IRName(lh))),
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

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(FunctionCallExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        List<IRExpr> params = n.parameters.stream()
                .map(stmt -> stmt.accept(this).assertFirst())
                .collect(Collectors.toList());
        List<ExpandedType> paramTypes = n.parameters.stream().map(vdn -> vdn.getType()).collect(Collectors.toList());
        String encodedName = functionName(n.identifier, paramTypes, n.getType());
        return OneOfTwo.ofFirst(make.IRCall(make.IRName(encodedName), params));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ArrayAccessExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String ts = generator.newTemp();
        String ta = generator.newTemp();
        String tl = generator.newTemp();
        String lt = generator.newLabel();
        String lf = generator.newLabel();
        List<IRStmt> commands = new ArrayList<IRStmt>();

        IRExpr index = n.index.accept(this).assertFirst();
        IRExpr arr = n.child.accept(this).assertFirst();

        commands.add(make.IRMove(make.IRTemp(ts), index));
        commands.add(make.IRMove(make.IRTemp(ta), arr));

        IRExpr length = make.IRMem(
                make.IRBinOp(OpType.SUB, make.IRTemp(ts), make.IRConst(8)));
        commands.add(make.IRMove(make.IRTemp(tl), length));

        // Check for out of bounds
        commands.add(make.IRCJump(make.IRBinOp(OpType.AND,
                make.IRBinOp(OpType.LEQ, make.IRConst(0), make.IRTemp(ta)),
                make.IRBinOp(OpType.LT, make.IRTemp(ta), make.IRTemp(tl))), lt,
                lf));
        commands.add(make.IRLabel(lf));
        commands.add(make.IRExp(make.IRCall(make.IRName("_xi_out_of_bounds"))));
        commands.add(make.IRLabel(lt));

        IRExpr val = make.IRMem(make.IRBinOp(OpType.ADD, make.IRTemp(ta),
                make.IRBinOp(OpType.MUL, make.IRTemp(ta), make.IRConst(8))));
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
        String rightArrAddr = generator.newTemp();
        String leftArrSize = generator.newTemp();
        String rightArrSize = generator.newTemp();
        String summedArrSize = generator.newTemp();
        String summedArrAddr = generator.newTemp();

        seq.add(make.IRMove(make.IRTemp(leftArrAddr), n.left.accept(this).assertFirst()));
        seq.add(make.IRMove(make.IRTemp(rightArrAddr), n.right.accept(this).assertFirst());
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
        seq.add(make.IRMove(
            make.IRTemp(summedArrAddr),
            make.IRCall(
                make.IRName("_xi_malloc"),
                make.IRBinOp(OpType.MUL,
                    make.IRBinOp(OpType.ADD,
                        make.IRTemp(summedArrSize), make.IRConst(1)),
                    make.IRConst(8))
            )));
        seq.add(make.IRMove(make.IRMem((make.IRTemp(summedArrAddr))), make.IRTemp(summedArrSize)));
        // Move array address pointer to actual start of array
        seq.add(make.IRMove(make.IRTemp(summedArrAddr),
            make.IRBinOp(OpType.ADD, make.IRTemp(summedArrAddr), make.IRConst(8))));
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
        seq.add(make.IRLabel(leftSumming));
        seq.add(make.IRCJump(
            make.IRBinOp(OpType.LT, make.IRTemp(i), make.IRTemp(leftArrSize)),
            leftBody,
            rightSumming));
        seq.add(make.IRLabel(leftBody));
        seq.add(make.IRMove(
            make.IRMem(
                make.IRBinOp(OpType.ADD,
                    make.IRTemp(summedArrAddr),
                    make.IRBinOp(OpType.MUL,
                        make.IRTemp(i),
                        make.IRConst(8)))),
            make.IRBinOp(OpType.ADD,
                make.IRTemp(leftArrAddr),
                make.IRBinOp(OpType.MUL,
                    make.IRTemp(i),
                    make.IRConst(8)))));
        seq.add(make.IRMove(make.IRTemp(i), make.IRBinOp(OpType.ADD, make.IRTemp(i), make.IRConst(1))));
        seq.add(make.IRJump(make.IRName(leftSumming)));

        seq.add(make.IRMove(make.IRTemp(j), make.IRConst(0)));
        seq.add(make.IRLabel(rightSumming));
        seq.add(make.IRCJump(
            make.IRBinOp(OpType.LT, make.IRTemp(j), make.IRTemp(rightArrSize)),
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
            make.IRBinOp(OpType.ADD,
                make.IRTemp(rightArrAddr),
                make.IRBinOp(OpType.MUL,
                    make.IRTemp(j),
                    make.IRConst(8)))));
        seq.add(make.IRMove(make.IRTemp(j), make.IRBinOp(OpType.ADD, make.IRTemp(j), make.IRConst(1))));
        seq.add(make.IRJump(make.IRName(leftSumming)));

        seq.add(make.IRLabel(exit));

        return OneOfTwo.ofFirst(make.IRESeq(make.IRSeq(seq), make.IRTemp(summedArrAddr)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(AndExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        String x = generator.newTemp();
        String l1 = generator.newLabel();
        String l2 = generator.newLabel();
        String l3 = generator.newLabel();

        IRStmt left = n.left.accept(new CTranslationVisitor(generator, l1, l3));
        IRStmt right = n.right
                .accept(new CTranslationVisitor(generator, l2, l3));

        return OneOfTwo.ofFirst(make
                .IRESeq(make.IRSeq(make.IRMove(make.IRTemp(x), make.IRConst(0)),
                        left, make.IRLabel(l1), right, make.IRLabel(l2),
                        make.IRMove(make.IRTemp(x), make.IRConst(1)),
                        make.IRLabel(l3)), make.IRTemp(x)));
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

        String x = generator.newTemp();
        String l1 = generator.newLabel();
        String l2 = generator.newLabel();
        String l3 = generator.newLabel();

        IRStmt left = n.left.accept(new CTranslationVisitor(generator, l3, l1));
        IRStmt right = n.right
                .accept(new CTranslationVisitor(generator, l3, l2));

        return OneOfTwo.ofFirst(make
                .IRESeq(make.IRSeq(make.IRMove(make.IRTemp(x), make.IRConst(1)),
                        left, make.IRLabel(l1), right, make.IRLabel(l2),
                        make.IRMove(make.IRTemp(x), make.IRConst(0)),
                        make.IRLabel(l3)), make.IRTemp(x)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(RemExprNode n) {
        return binOp(IRBinOp.OpType.MOD, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(SubExprNode n) {
        return binOp(IRBinOp.OpType.SUB, n);
    }

    public OneOfTwo<IRExpr, IRStmt> visitArr(List<IRExpr> vals,
            Location location) {
        IRNodeFactory make = new IRNodeFactory_c(location);

        String memBlock = generator.newTemp();
        String pointerStart = generator.newTemp();
        int size = vals.size();
        IRExpr spaceNeeded = make.IRBinOp(OpType.MUL,
                make.IRConst(Configuration.WORD_SIZE),
                make.IRBinOp(OpType.ADD, make.IRConst(size), make.IRConst(1)));

        List<IRStmt> commands = new ArrayList<IRStmt>();
        IRExpr memLoc = make.IRCall(make.IRName("_xi_malloc"), spaceNeeded);
        commands.add(make.IRMove(make.IRTemp(memBlock), memLoc));
        commands.add(make.IRMove(make.IRMem(make.IRTemp(memBlock)),
                make.IRConst(size)));
        commands.add(make.IRMove(make.IRTemp(pointerStart),
                make.IRBinOp(OpType.ADD, make.IRTemp(memBlock),
                        make.IRConst(Configuration.WORD_SIZE))));

        // Setting the values of the indices in memory
        for (int i = 0; i < vals.size(); i++) {
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

        return OneOfTwo.ofFirst(make.IRConst(n.contents.charAt(0)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralIntExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        return OneOfTwo.ofFirst(make.IRConst(Long.parseLong(n.contents)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralStringExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        List<IRExpr> vals = new ArrayList<>();
        for (char c : n.contents.toCharArray()) {
            vals.add(make.IRConst(c));
        }
        return visitArr(vals, n.getLocation());
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(BoolNegExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        IRExpr e = n.expr.accept(this).assertFirst();
        return OneOfTwo
                .ofFirst(make.IRBinOp(IRBinOp.OpType.XOR, make.IRConst(1), e));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IntNegExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());

        IRExpr e = n.expr.accept(this).assertFirst();
        return OneOfTwo
                .ofFirst(make.IRBinOp(IRBinOp.OpType.SUB, make.IRConst(0), e));
    }
}
