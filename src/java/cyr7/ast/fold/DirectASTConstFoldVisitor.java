package cyr7.ast.fold;

import java.util.Optional;
import java.util.stream.Collectors;

import cyr7.ast.AbstractNode;
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
import cyr7.ast.fold.DirectASTConstFoldVisitor.Result;
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
import cyr7.ast.type.TypeExprNode;
import cyr7.semantics.types.ExpandedType;
import cyr7.util.OneOfThree;
import cyr7.visitor.AbstractVisitor;

public class DirectASTConstFoldVisitor extends AbstractVisitor<Result> {

    public static class Result
            extends OneOfThree<ExprNode, StmtNode, AbstractNode> {

        protected Result(ExprNode first, StmtNode second, AbstractNode third) {
            super(first, second, third);
        }

        public static Result expr(ExprNode expr) {
            return new Result(expr, null, null);
        }

        public static Result stmt(StmtNode stmt) {
            return new Result(null, stmt, null);
        }

        public static Result other(AbstractNode other) {
            return new Result(null, null, other);
        }

    }

    private final ASTCanonicalCheckerVisitor isCanonical;

    public static final DirectASTConstFoldVisitor instance = new DirectASTConstFoldVisitor();

    private DirectASTConstFoldVisitor() {
        this.isCanonical = ASTCanonicalCheckerVisitor.instance;
    };

    @Override
    public Result visit(FunctionDeclNode n) {
        var foldedHeader = (FunctionHeaderDeclNode) n.header.accept(this)
                .assertThird();
        var foldedBody = (BlockStmtNode) n.block.accept(this).assertSecond();

        var foldedFuncDecl = new FunctionDeclNode(n.getLocation(), foldedHeader,
                foldedBody);

        return Result.other(foldedFuncDecl);
    }

    @Override
    public Result visit(FunctionHeaderDeclNode n) {
        var foldedArgs = n.args.stream()
                .map(a -> (VarDeclNode) a.accept(this).assertSecond())
                .collect(Collectors.toList());
        var foldedReturnTypes = n.returnTypes.stream()
                .map(r -> (TypeExprNode) r.accept(this).assertThird())
                .collect(Collectors.toList());
        var foldedHeader = new FunctionHeaderDeclNode(n.getLocation(),
                n.identifier, foldedArgs, foldedReturnTypes);

        return Result.other(foldedHeader);
    }

    @Override
    public Result visit(IxiProgramNode n) {
        var foldedFuncDecl = n.functionDeclarations.stream().map(
                fd -> (FunctionHeaderDeclNode) fd.accept(this).assertThird())
                .collect(Collectors.toList());
        return Result
                .other(new IxiProgramNode(n.getLocation(), foldedFuncDecl));
    }

    @Override
    public Result visit(UseNode n) {
        return Result.other(n);
    }

    @Override
    public Result visit(VarDeclNode n) {
        var foldedType = (TypeExprNode) n.typeExpr.accept(this).assertThird();
        return Result.stmt(
                new VarDeclNode(n.getLocation(), n.identifier, foldedType));
    }

    @Override
    public Result visit(XiProgramNode n) {
        var foldedUses = n.uses.stream()
                .map(u -> (UseNode) u.accept(this).assertThird())
                .collect(Collectors.toList());

        var foldedFunctions = n.functions.stream()
                .map(f -> (FunctionDeclNode) f.accept(this).assertThird())
                .collect(Collectors.toList());

        return Result.other(new XiProgramNode(n.getLocation(), foldedUses,
                foldedFunctions));
    }

    @Override
    public Result visit(PrimitiveTypeNode n) {
        return Result.other(n);
    }

    @Override
    public Result visit(TypeExprArrayNode n) {
        var foldedSize = n.size.isPresent()
                ? Optional.of(n.size.get().accept(this).assertFirst())
                : n.size;
        var foldedChild = (TypeExprArrayNode) n.child.accept(this)
                .assertThird();

        return Result.other(new TypeExprArrayNode(n.getLocation(), foldedChild,
                foldedSize));
    }

    @Override
    public Result visit(ArrayDeclStmtNode n) {
        var foldedType = (TypeExprArrayNode) n.type.accept(this).assertThird();
        return Result.stmt(new ArrayDeclStmtNode(n.getLocation(), n.identifier,
                foldedType));
    }

    @Override
    public Result visit(AssignmentStmtNode n) {
        var foldedLHS = n.lhs.accept(this).assertFirst();
        var foldedRHS = n.rhs.accept(this).assertFirst();
        return Result.stmt(
                new AssignmentStmtNode(n.getLocation(), foldedLHS, foldedRHS));
    }

    @Override
    public Result visit(CompoundAssignStmtNode n) {
        var foldedLHS = n.lhs.accept(this).assertFirst();
        var foldedRHS = n.rhs.accept(this).assertFirst();
        return Result.stmt(new CompoundAssignStmtNode(n.getLocation(), n.opType,
                foldedLHS, foldedRHS));
    }

    @Override
    public Result visit(BlockStmtNode n) {
        var foldedStmts = n.statements.stream()
                .map(s -> s.accept(this).assertSecond())
                .collect(Collectors.toList());
        return Result.stmt(new BlockStmtNode(n.getLocation(), foldedStmts));
    }

    @Override
    public Result visit(ExprStmtNode n) {
        var foldedExpr = n.expr.accept(this).assertFirst();
        return Result.stmt(new ExprStmtNode(n.getLocation(), foldedExpr));
    }

    @Override
    public Result visit(IfElseStmtNode n) {
        var foldedGuard = n.guard.accept(this).assertFirst();
        var foldedIfBody = n.ifBlock.accept(this).assertSecond();
        var foldedElseBody = n.elseBlock.isPresent()
                ? Optional.of(n.elseBlock.get().accept(this).assertSecond())
                : n.elseBlock;
        return Result.stmt(new IfElseStmtNode(n.getLocation(), foldedGuard,
                foldedIfBody, foldedElseBody));
    }

    @Override
    public Result visit(MultiAssignStmtNode n) {
        var foldedInitializer = (FunctionCallExprNode) n.initializer
                .accept(this).assertFirst();
        var foldedDeclList = n.varDecls.stream().map(d -> {
            if (d.isPresent()) {
                var foldedDecl = (VarDeclNode) d.get().accept(this)
                        .assertSecond();
                return Optional.of(foldedDecl);
            } else {
                return d;
            }
        }).collect(Collectors.toList());

        return Result.stmt(new MultiAssignStmtNode(n.getLocation(),
                foldedDeclList, foldedInitializer));
    }

    @Override
    public Result visit(ProcedureStmtNode n) {
        var procedureCall = (FunctionCallExprNode) n.procedureCall.accept(this)
                .assertFirst();

        return Result
                .stmt(new ProcedureStmtNode(n.getLocation(), procedureCall));
    }

    @Override
    public Result visit(ReturnStmtNode n) {
        var returnVals = n.exprs.stream().map(e -> e.accept(this).assertFirst())
                .collect(Collectors.toList());
        return Result.stmt(new ReturnStmtNode(n.getLocation(), returnVals));
    }

    @Override
    public Result visit(VarDeclStmtNode n) {
        var foldedVarDecl = (VarDeclNode) n.varDecl.accept(this).assertFirst();
        return Result.stmt(new VarDeclStmtNode(n.getLocation(), foldedVarDecl));
    }

    @Override
    public Result visit(VarInitStmtNode n) {
        var foldedInit = n.initializer.accept(this).assertFirst();
        var foldedVarDecl = (VarDeclNode) n.varDecl.accept(this).assertFirst();
        return Result.stmt(new VarInitStmtNode(n.getLocation(), foldedVarDecl,
                foldedInit));
    }

    @Override
    public Result visit(WhileStmtNode n) {
        var foldedGuard = n.guard.accept(this).assertFirst();
        var foldedBody = n.block.accept(this).assertSecond();

        var foldedWhile = new WhileStmtNode(n.getLocation(), foldedGuard,
                foldedBody);
        return Result.stmt(foldedWhile);
    }

    // Expressions

    @Override
    public Result visit(FunctionCallExprNode n) {
        var foldedParams = n.parameters.stream()
                .map(e -> e.accept(this).assertFirst())
                .collect(Collectors.toList());
        var foldedCall = new FunctionCallExprNode(n.getLocation(), n.identifier,
                foldedParams);
        foldedCall.setType(n.getType());
        return Result.expr(foldedCall);
    }

    @Override
    public Result visit(ArrayAccessExprNode n) {
        var foldedChild = n.child.accept(this).assertFirst();
        var foldedIndex = n.index.accept(this).assertFirst();
        var foldedArrAccess = new ArrayAccessExprNode(n.getLocation(),
                foldedChild, foldedIndex);
        foldedArrAccess.setType(n.getType());
        return Result.expr(foldedArrAccess);
    }

    @Override
    public Result visit(VariableAccessExprNode n) {
        return Result.expr(n);
    }

    @Override
    public Result visit(BinExprNode n) {
        return Result.expr(DirectASTBinopHandler.instance.accept(n,
                n.accept(isCanonical)));
    }

    @Override
    public Result visit(LiteralArrayExprNode n) {
        var foldedArr = new LiteralArrayExprNode(n.getLocation(),
                n.arrayVals.stream().map(e -> e.accept(this).assertFirst())
                        .collect(Collectors.toList()));
        foldedArr.setType(n.getType());
        return Result.expr(foldedArr);
    }

    @Override
    public Result visit(LiteralBoolExprNode n) {
        return Result.expr(n);
    }

    @Override
    public Result visit(LiteralCharExprNode n) {
        return Result.expr(n);
    }

    @Override
    public Result visit(LiteralIntExprNode n) {
        return Result.expr(n);
    }

    @Override
    public Result visit(LiteralStringExprNode n) {
        return Result.expr(n);
    }

    @Override
    public Result visit(BoolNegExprNode n) {
        var innerExpr = n.expr.accept(this).assertFirst();
        if (innerExpr instanceof LiteralBoolExprNode) {
            var boolNode = (LiteralBoolExprNode) innerExpr;
            var updatedBool = !boolNode.contents;
            var updatedBoolNode = new LiteralBoolExprNode(
                    innerExpr.getLocation(), updatedBool);
            return Result.expr(updatedBoolNode);
        } else {
            var negNode = new IntNegExprNode(n.getLocation(), innerExpr);
            negNode.setType(n.getType());
            return Result.expr(negNode);
        }
    }

    @Override
    public Result visit(IntNegExprNode n) {
        var innerExpr = n.expr.accept(this).assertFirst();
        if (innerExpr instanceof LiteralIntExprNode) {
            var intNode = (LiteralIntExprNode) innerExpr;
            var updatedInt = Long.parseLong(intNode.contents) * -1;
            var updatedIntNode = new LiteralIntExprNode(innerExpr.getLocation(),
                    String.valueOf(updatedInt));
            updatedIntNode.setType(ExpandedType.intType);
            return Result.expr(updatedIntNode);
        } else {
            var negNode = new IntNegExprNode(n.getLocation(), innerExpr);
            negNode.setType(n.getType());
            return Result.expr(negNode);
        }
    }

    @Override
    public Result visit(LengthExprNode n) {
        // Replace length(a) with the size if a is an array
        // with no side effect expressions, such as function calls.
        var innerExpr = n.expr.accept(this).assertFirst();
        if (innerExpr instanceof LiteralArrayExprNode) {
            var arr = (LiteralArrayExprNode) innerExpr;
            if (arr.accept(isCanonical).booleanValue()) {
                var updatedNode = new LiteralIntExprNode(arr.getLocation(),
                        String.valueOf(arr.arrayVals.size()));
                updatedNode.setType(ExpandedType.intType);
                return Result.expr(updatedNode);
            }
        }
        var lengthNode = new LengthExprNode(n.getLocation(), innerExpr);
        lengthNode.setType(n.getType());
        return Result.expr(lengthNode);
    }

}
