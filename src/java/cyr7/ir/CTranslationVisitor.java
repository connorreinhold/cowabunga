package cyr7.ir;

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
import cyr7.ast.stmt.CompoundAssignStmtNode;
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
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.visitor.AbstractVisitor;

public final class CTranslationVisitor extends AbstractVisitor<IRStmt> {

    private final IdGenerator generator;
    private final String tLabel, fLabel;

    public CTranslationVisitor(IdGenerator generator, String tLabel,
            String fLabel) {
        this.generator = generator;
        this.tLabel = tLabel;
        this.fLabel = fLabel;
    }

    public IRStmt andExprCTranslation(BinExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        String tPrime = generator.newLabel();
        return make.IRSeq(
                n.left.accept(
                        new CTranslationVisitor(generator, tPrime, fLabel)),
                make.IRLabel(tPrime), n.right.accept(
                        new CTranslationVisitor(generator, tLabel, fLabel)));
    }

    @Override
    public IRStmt visit(BoolNegExprNode n) {
        return n.expr
                .accept(new CTranslationVisitor(generator, fLabel, tLabel));
    }

    public IRStmt orExprCTranslation(BinExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        String fPrime = generator.newLabel();
        return make.IRSeq(
                n.left.accept(
                        new CTranslationVisitor(generator, tLabel, fPrime)),
                make.IRLabel(fPrime), n.right.accept(
                        new CTranslationVisitor(generator, tLabel, fLabel)));
    }

    @Override
    public IRStmt visit(LiteralBoolExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        IRName name = make.IRName(n.contents ? tLabel : fLabel);
        return make.IRJump(name);
    }

    // General

    private IRStmt cjump(ExprNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.getLocation());
        return make.IRCJump(
                n.accept(new ASTToIRVisitor(generator)).assertFirst(), tLabel,
                fLabel);
    }

    @Override
    public IRStmt visit(BinExprNode n) {
        switch (n.opType) {
        case AND:
            return andExprCTranslation(n);
        case OR:
            return orExprCTranslation(n);
        case ADD:
        case DIV:
        case EQ:
        case GEQ:
        case GT:
        case HIGH_MUL:
        case LEQ:
        case LT:
        case MOD:
        case MUL:
        case NEQ:
        case SUB:
            return cjump(n);
        default:
            break;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(FunctionCallExprNode n) {
        return cjump(n);
    }

    @Override
    public IRStmt visit(ArrayAccessExprNode n) {
        return cjump(n);
    }

    @Override
    public IRStmt visit(VariableAccessExprNode n) {
        return cjump(n);
    }

    @Override
    public IRStmt visit(LiteralArrayExprNode n) {
        return cjump(n);
    }

    @Override
    public IRStmt visit(LiteralCharExprNode n) {
        return cjump(n);
    }

    @Override
    public IRStmt visit(LiteralIntExprNode n) {
        return cjump(n);
    }

    @Override
    public IRStmt visit(LiteralStringExprNode n) {
        return cjump(n);
    }

    @Override
    public IRStmt visit(IntNegExprNode n) {
        return cjump(n);
    }

    @Override
    public IRStmt visit(LengthExprNode n) {
        return cjump(n);
    }

    // Unsupported

    @Override
    public IRStmt visit(FunctionDeclNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(FunctionHeaderDeclNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(IxiProgramNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(UseNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(VarDeclNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(XiProgramNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(PrimitiveTypeNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(TypeExprArrayNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(ArrayDeclStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(AssignmentStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(BlockStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(ExprStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(IfElseStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(MultiAssignStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(ProcedureStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(ReturnStmtNode n) {
        throw new UnsupportedOperationException();

    }

    @Override
    public IRStmt visit(VarDeclStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(VarInitStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(WhileStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRStmt visit(CompoundAssignStmtNode n) {
        throw new UnsupportedOperationException();
    }

}
