package cyr7.ast.fold;

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
import cyr7.visitor.AbstractVisitor;

public class ASTCanonicalCheckerVisitor extends AbstractVisitor<Boolean> {

    public static final ASTCanonicalCheckerVisitor instance =
            new ASTCanonicalCheckerVisitor();
    
    private ASTCanonicalCheckerVisitor() {}
    
    @Override
    public Boolean visit(FunctionDeclNode n) {
        return n.block.accept(this);
    }

    @Override
    public Boolean visit(FunctionHeaderDeclNode n) {
        return true;
    }

    @Override
    public Boolean visit(IxiProgramNode n) {
        return true;
    }

    @Override
    public Boolean visit(UseNode n) {
        return true;
    }

    @Override
    public Boolean visit(VarDeclNode n) {
        return true;
    }

    @Override
    public Boolean visit(XiProgramNode n) {
        for (FunctionDeclNode f : n.functions) {
            if (!f.accept(this)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean visit(PrimitiveTypeNode n) {
        return true;
    }

    @Override
    public Boolean visit(TypeExprArrayNode n) {
        return (n.size.isEmpty() || n.size.get().accept(this))
                && n.child.accept(this);
    }

    @Override
    public Boolean visit(ArrayDeclStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(AssignmentStmtNode n) {
        return false;
    }

    @Override
    public Boolean visit(CompoundAssignStmtNode n) {
        return false;
    }

    @Override
    public Boolean visit(BlockStmtNode n) {
        for (StmtNode s : n.statements) {
            if (!s.accept(this)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean visit(ExprStmtNode n) {
        return n.expr.accept(this);
    }

    @Override
    public Boolean visit(IfElseStmtNode n) {
        return n.guard.accept(this) && n.ifBlock.accept(this)
                && (n.elseBlock.isEmpty() || n.elseBlock.get().accept(this));
    }

    @Override
    public Boolean visit(MultiAssignStmtNode n) {
        return n.initializer.accept(this);
    }

    @Override
    public Boolean visit(ProcedureStmtNode n) {
        return false;
    }

    @Override
    public Boolean visit(ReturnStmtNode n) {
        for (ExprNode e : n.exprs) {
            if (!e.accept(this)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean visit(VarDeclStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(VarInitStmtNode n) {
        return n.initializer.accept(this);
    }

    @Override
    public Boolean visit(WhileStmtNode n) {
        return n.guard.accept(this) && n.block.accept(this);
    }

    @Override
    public Boolean visit(FunctionCallExprNode n) {
        return false;
    }

    @Override
    public Boolean visit(ArrayAccessExprNode n) {
        return n.index.accept(this) && n.child.accept(this);
    }

    @Override
    public Boolean visit(VariableAccessExprNode n) {
        return true;
    }

    @Override
    public Boolean visit(BinExprNode n) {
        return n.left.accept(this) && n.right.accept(this);
    }

    @Override
    public Boolean visit(LiteralArrayExprNode n) {
        for (ExprNode e : n.arrayVals) {
            if (!e.accept(this)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean visit(LiteralBoolExprNode n) {
        return true;
    }

    @Override
    public Boolean visit(LiteralCharExprNode n) {
        return true;
    }

    @Override
    public Boolean visit(LiteralIntExprNode n) {
        return true;
    }

    @Override
    public Boolean visit(LiteralStringExprNode n) {
        return true;
    }

    @Override
    public Boolean visit(BoolNegExprNode n) {
        return n.expr.accept(this);
    }

    @Override
    public Boolean visit(IntNegExprNode n) {
        return n.expr.accept(this);
    }

    @Override
    public Boolean visit(LengthExprNode n) {
        return n.expr.accept(this);
    }

}
