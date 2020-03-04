package cyr7.ir;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import cyr7.ir.nodes.*;
import cyr7.util.OneOfTwo;
import cyr7.visitor.AbstractVisitor;

public class AstToIrVisitor extends AbstractVisitor<OneOfTwo<IRExpr, IRStmt>> {

    private final IdGenerator generator = new DefaultIdGenerator();

    public AstToIrVisitor() {
    }

    // Top Level

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(FunctionDeclNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(FunctionHeaderDeclNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IxiProgramNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(UseNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VarDeclNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(XiProgramNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(PrimitiveTypeNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(TypeExprArrayNode n) {
        return null;
    }

    // Statements

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ArrayDeclStmtNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(AssignmentStmtNode n) {
        IRExpr rhs = n.rhs.accept(this).assertFirst();
        IRExpr lhs = n.lhs.accept(this).assertFirst();
        // TODO: Check if the type of lhs is array or just a singular variable
        return OneOfTwo.ofSecond(new IRMove(lhs, rhs));
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
        List<IRExpr> rets = n.exprs.stream()
                .map(stmt -> stmt.accept(this).assertFirst())
                .collect(Collectors.toList());
        return OneOfTwo.ofSecond(new IRReturn(rets));
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

        IRExpr grd = n.guard.accept(this).assertFirst();
        IRStmt blk = n.block.accept(this).assertSecond();

        return OneOfTwo.ofSecond(new IRSeq(
                new IRLabel(lh), new IRCJump(grd, lt, lf),
                new IRLabel(lt), new IRSeq(blk, new IRJump(new IRName(lh))),
                new IRLabel(lf)
        ));
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
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(ArrayAccessExprNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(VariableAccessExprNode n) {
        return null;
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

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(LiteralArrayExprNode n) {
        return null;
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
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(BoolNegExprNode n) {
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IntNegExprNode n) {
        return null;
    }
}
