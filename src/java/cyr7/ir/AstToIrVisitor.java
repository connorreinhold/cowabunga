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
import cyr7.util.OneOfTwo;
import cyr7.visitor.AbstractVisitor;

public class AstToIrVisitor extends AbstractVisitor<OneOfTwo<IRExpr, IRStmt>> {

    private final IdGenerator generator = new DefaultIdGenerator();

    public AstToIrVisitor() {
    }

    private String functionName(String n, List<ExprNode> paramTypes,
            ExpandedType returnType) {
        String name = "_I" + n.replace("_", "__") + "_";
        List<String> params = new ArrayList<>();
        paramTypes.forEach(t -> params.add(typeIdentifier(t.getType())));
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
        /*
        List<IRStmt> seq = new ArrayList<>();
        seq.add(new IRLabel(functionName(n.header.identifier, n.header.args, n.header.returnTypes)));
        IRSeq func = new IRSeq();
        */
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(FunctionHeaderDeclNode n) { throw new UnsupportedOperationException(); }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IxiProgramNode n) { throw new UnsupportedOperationException(); }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(UseNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VarDeclNode n) {
        return OneOfTwo.ofSecond(new IRMove(new IRTemp(n.identifier), new IRConst(0)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(XiProgramNode n) {
        String file = n.getLocation().getUnit();
        file = file.substring(0, file.lastIndexOf('.'));
        IRCompUnit program = new IRCompUnit(file);

        for (FunctionDeclNode fun: n.functions) {
            program.appendFunc((IRFuncDecl) fun.accept(this).assertFirst());
        }

        return OneOfTwo.ofSecond(program);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(PrimitiveTypeNode n) { throw new UnsupportedOperationException(); }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(TypeExprArrayNode n) { throw new UnsupportedOperationException(); }

    // Statements

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ArrayDeclStmtNode n) {
        String memBlock = generator.newTemp();
        String pointerStart = generator.newTemp();
        List<IRStmt> commands = new ArrayList<IRStmt>();
        if (n.type.size.isPresent()) {
            IRExpr size = n.type.size.get().accept(this).assertFirst();
            IRExpr spaceNeeded = new IRBinOp(OpType.MUL, new IRConst(Configuration.WORD_SIZE),
                    new IRBinOp(OpType.ADD, size, new IRConst(1)));

            IRExpr memLoc = new IRCall(new IRName("_xi_alloc"), spaceNeeded);
            commands.add(new IRMove(new IRTemp(memBlock), memLoc));
            commands.add(new IRMove(new IRMem(new IRTemp(memBlock)), size));
            commands.add(new IRMove(new IRTemp(pointerStart), new IRBinOp(
                    OpType.ADD, new IRTemp(memBlock), new IRConst(Configuration.WORD_SIZE))));
        }
        return OneOfTwo.ofSecond(new IRSeq(commands));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(AssignmentStmtNode n) {
        IRExpr rhs = n.rhs.accept(this).assertFirst();
        if (ExpandedType.voidArrayType.isASubtypeOf(n.lhs.getType())) {
            IRExpr lhs = n.lhs.accept(this).assertFirst();
            return OneOfTwo.ofSecond(new IRMove(lhs, rhs));
        } else {
            VariableAccessExprNode var = (VariableAccessExprNode) n.lhs;
            return OneOfTwo
                    .ofSecond(new IRMove(new IRTemp(var.identifier), rhs));
        }
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(BlockStmtNode n) {
        List<IRStmt> stmts = n.statements.stream()
                .map(stmt -> stmt.accept(this).assertSecond())
                .collect(Collectors.toList());
        return OneOfTwo.ofSecond(new IRSeq(stmts));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ExprStmtNode n) {
        IRExpr e = n.expr.accept(this).assertFirst();
        return OneOfTwo.ofSecond(new IRExp(e));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IfElseStmtNode n) {
        String lt = generator.newLabel();
        String lf = generator.newLabel();
        List<IRStmt> commands = new ArrayList<>();
        commands.add(
                n.guard.accept(new CTranslationVisitor(generator, lt, lf)));
        commands.add(new IRLabel(lt));
        commands.add(n.ifBlock.accept(this).assertSecond());
        commands.add(new IRLabel(lf));
        if (n.elseBlock.isPresent()) {
            commands.add(n.elseBlock.get().accept(this).assertSecond());
        }
        return OneOfTwo.ofSecond(new IRSeq(commands));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(MultiAssignStmtNode n) {
        List<IRStmt> commands = new ArrayList<>();
        IRExpr functionCall = n.initializer.accept(this).assertFirst();
        commands.add(new IRExp(functionCall));
        int retNum = 0;
        for (Optional<VarDeclNode> var : n.varDecls) {
            if (var.isPresent()) {
                commands.add(new IRMove(new IRTemp(var.get().identifier),
                        new IRTemp(generator.retTemp(retNum))));
            }
            retNum++;
        }
        return OneOfTwo.ofSecond(new IRSeq(commands));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ProcedureStmtNode n) {
        return OneOfTwo.ofSecond(
                new IRExp(n.procedureCall.accept(this).assertFirst()));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ReturnStmtNode n) {
        AtomicInteger retNum = new AtomicInteger();
        List<IRStmt> rets = n.exprs.stream()
                .map(stmt -> new IRMove(new IRTemp("_RET"+ retNum.getAndIncrement()),
                        stmt.accept(this).assertFirst()))
                .collect(Collectors.toList());
        rets.add(new IRReturn());
        return OneOfTwo.ofSecond(new IRSeq(rets));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VarDeclStmtNode n) {
        // Initialize it to 0
        return OneOfTwo.ofSecond(
                new IRMove(new IRTemp(n.varDecl.identifier), new IRConst(0)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VarInitStmtNode n) {
        String name = n.varDecl.identifier;
        IRExpr expr = n.initializer.accept(this).assertFirst();

        return OneOfTwo.ofSecond(new IRMove(new IRTemp(name), expr));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(WhileStmtNode n) {
        String lh = generator.newLabel();
        String lt = generator.newLabel();
        String lf = generator.newLabel();

        IRStmt grd = n.guard.accept(new CTranslationVisitor(generator, lt, lf));
        IRStmt blk = n.block.accept(this).assertSecond();

        return OneOfTwo.ofSecond(new IRSeq(new IRLabel(lh),
                grd, new IRLabel(lt),
                new IRSeq(blk, new IRJump(new IRName(lh))), new IRLabel(lf)));
    }

    // Expressions

    private OneOfTwo<IRExpr, IRStmt> binOp(IRBinOp.OpType opType,
            BinExprNode n) {
        IRExpr left = n.left.accept(this).assertFirst();
        IRExpr right = n.right.accept(this).assertFirst();
        return OneOfTwo.ofFirst(new IRBinOp(opType, left, right));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(FunctionCallExprNode n) {
        List<IRExpr> params = n.parameters.stream()
                .map(stmt -> stmt.accept(this).assertFirst())
                .collect(Collectors.toList());
        String encodedName = functionName(n.identifier, n.parameters,
                n.getType());
        return OneOfTwo.ofFirst(new IRCall(new IRName(encodedName), params));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ArrayAccessExprNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VariableAccessExprNode n) {
        return OneOfTwo.ofFirst(new IRTemp(n.identifier));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(AddExprNode n) {
        return binOp(IRBinOp.OpType.ADD, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(AndExprNode n) {
        String x = generator.newTemp();
        String l1 = generator.newLabel();
        String l2 = generator.newLabel();
        String l3 = generator.newLabel();

        IRStmt left = n.left.accept(new CTranslationVisitor(generator, l1, l3));
        IRStmt right = n.right
                .accept(new CTranslationVisitor(generator, l2, l3));

        return OneOfTwo.ofFirst(
                new IRESeq(new IRSeq(new IRMove(new IRTemp(x), new IRConst(0)),
                        left, new IRLabel(l1), right, new IRLabel(l2),
                        new IRMove(new IRTemp(x), new IRConst(1)),
                        new IRLabel(l3)), new IRTemp(x)));
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
        String x = generator.newTemp();
        String l1 = generator.newLabel();
        String l2 = generator.newLabel();
        String l3 = generator.newLabel();

        IRStmt left = n.left.accept(new CTranslationVisitor(generator, l3, l1));
        IRStmt right = n.right
                .accept(new CTranslationVisitor(generator, l3, l2));

        return OneOfTwo.ofFirst(
                new IRESeq(new IRSeq(new IRMove(new IRTemp(x), new IRConst(1)),
                        left, new IRLabel(l1), right, new IRLabel(l2),
                        new IRMove(new IRTemp(x), new IRConst(0)),
                        new IRLabel(l3)), new IRTemp(x)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(RemExprNode n) {
        return binOp(IRBinOp.OpType.MOD, n);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(SubExprNode n) {
        return binOp(IRBinOp.OpType.SUB, n);
    }

    public OneOfTwo<IRExpr, IRStmt> visitArr(List<IRExpr> vals) {
        String memBlock = generator.newTemp();
        String pointerStart = generator.newTemp();
        int size = vals.size();
        IRExpr spaceNeeded = new IRBinOp(OpType.MUL, new IRConst(Configuration.WORD_SIZE),
                new IRBinOp(OpType.ADD, new IRConst(size), new IRConst(1)));

        List<IRStmt> commands = new ArrayList<IRStmt>();
        IRExpr memLoc = new IRCall(new IRName("_xi_alloc"), spaceNeeded);
        commands.add(new IRMove(new IRTemp(memBlock), memLoc));
        commands.add(
                new IRMove(new IRMem(new IRTemp(memBlock)), new IRConst(size)));
        commands.add(new IRMove(new IRTemp(pointerStart),
                new IRBinOp(OpType.ADD, new IRTemp(memBlock), new IRConst(Configuration.WORD_SIZE))));

        // Setting the values of the indices in memory
        for (int i = 0; i < vals.size(); i++) {
            IRExpr valueLoc = new IRMem(new IRBinOp(OpType.ADD,
                    new IRTemp(pointerStart),
                    new IRBinOp(OpType.MUL, new IRConst(Configuration.WORD_SIZE), new IRConst(i))));
            commands.add(new IRMove(valueLoc, vals.get(i)));
        }
        return OneOfTwo.ofFirst(
                new IRESeq(new IRSeq(commands), new IRTemp(pointerStart)));

    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralArrayExprNode n) {
        List<IRExpr> values = n.arrayVals.stream()
                .map(stmt -> stmt.accept(this).assertFirst())
                .collect(Collectors.toList());
        return visitArr(values);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralBoolExprNode n) {
        return OneOfTwo.ofFirst(new IRConst(n.contents ? 1 : 0));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralCharExprNode n) {
        return OneOfTwo.ofFirst(new IRConst(n.contents.charAt(0)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralIntExprNode n) {
        return OneOfTwo.ofFirst(new IRConst(Long.parseLong(n.contents)));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralStringExprNode n) {
        List<IRExpr> vals = new ArrayList<>();
        for (char c : n.contents.toCharArray()) {
            vals.add(new IRConst(c));
        }
        return visitArr(vals);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(BoolNegExprNode n) {
        IRExpr e = n.expr.accept(this).assertFirst();
        return OneOfTwo
                .ofFirst(new IRBinOp(IRBinOp.OpType.XOR, new IRConst(1), e));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IntNegExprNode n) {
        IRExpr e = n.expr.accept(this).assertFirst();
        return OneOfTwo
                .ofFirst(new IRBinOp(IRBinOp.OpType.SUB, new IRConst(0), e));
    }
}
