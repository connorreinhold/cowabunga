package cyr7.parser;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.*;
import cyr7.ast.expr.literalexpr.*;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.expr.unaryexpr.LengthExprNode;
import cyr7.ast.stmt.*;
import cyr7.ast.toplevel.*;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.ast.type.TypeExprNode;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Optional;

public final class SExpVisitor extends AbstractVisitor<Optional<Void>> {

    private final SExpPrinter printer;

    public SExpVisitor(Writer writer) {
        this.printer = new CodeWriterSExpPrinter(new PrintWriter(writer));
    }

    public void flush() {
        printer.flush();
    }

    @Override
    protected void finalize() {
        flush();
    }

    @Override
    public Optional<Void> visit(FunctionDeclNode n) {
        printer.startList();

        printer.printAtom(n.header.identifier);

        printer.startList();
        for (VarDeclNode argDecl : n.header.args) {
            argDecl.accept(this);
        }
        printer.endList();

        printer.startList();
        for (TypeExprNode typeNode : n.header.returnTypes) {
            typeNode.accept(this);
        }
        printer.endList();

        n.block.accept(this);

        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(FunctionHeaderDeclNode n) {
        printer.startUnifiedList();

        printer.printAtom(n.identifier);

        printer.startList();
        n.args.forEach(a -> {
            a.accept(this);
        });
        printer.endList();

        printer.startList();
        n.returnTypes.forEach(r -> {
            r.accept(this);
        });
        printer.endList();
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(IxiProgramNode n) {
        printer.startList();
        printer.printAtom("");
        printer.startUnifiedList();
        n.functionDeclarations.forEach(f -> {
            f.accept(this);
        });
        printer.endList();
        printer.printAtom("");
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(UseNode n) {
        printer.startList();
        printer.printAtom("use");
        printer.printAtom(n.interfaceName);
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(VarDeclNode n) {
        printer.startList();
        printer.printAtom(n.identifier);
        n.typeExpr.accept(this);
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(XiProgramNode n) {
        printer.startList();

        printer.startUnifiedList();
        for (UseNode use : n.uses) {
            use.accept(this);
        }
        printer.endList();

        printer.startUnifiedList();
        for (FunctionDeclNode functionDeclNode : n.functions) {
            functionDeclNode.accept(this);
        }
        printer.endList();

        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(PrimitiveTypeNode n) {
        printer.printAtom(n.type.toString().toLowerCase());

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(TypeExprArrayNode n) {
        printer.startList();

        printer.printAtom("[]");

        n.child.accept(this);

        n.size.ifPresent(exprNode -> exprNode.accept(this));

        printer.endList();

        return Optional.empty();
    }

    /* ---- ASSIGN ---- */

    @Override
    public Optional<Void> visit(ArrayDeclStmtNode n) {
        printer.startList();
        printer.printAtom(n.identifier);
        n.type.accept(this);
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(AssignmentStmtNode n) {
        printer.startList();
        printer.printAtom("=");
        n.lhs.accept(this);
        n.rhs.accept(this);
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(BlockStmtNode n) {
        printer.startList();

        for (StmtNode statement : n.statements) {
            statement.accept(this);
        }

        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(ExprStmtNode n) {
        printer.startList();
        printer.printAtom("=");
        printer.printAtom("_");
        n.expr.accept(this);
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(IfElseStmtNode n) {
        printer.startList();
        printer.printAtom("if");
        n.guard.accept(this);
        n.ifBlock.accept(this);
        n.elseBlock.ifPresent(stmtNode -> stmtNode.accept(this));
        printer.endList();
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(MultiAssignStmtNode n) {
        printer.startList();

        printer.printAtom("=");

        printer.startList();
        for (Optional<VarDeclNode> varDecl : n.varDecls) {
            if (varDecl.isPresent()) {
                varDecl.get().accept(this);
            } else {
                printer.printAtom("_");
            }
        }
        printer.endList();

        n.initializer.accept(this);

        printer.endList();
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(ProcedureStmtNode n) {
        n.procedureCall.accept(this);

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(ReturnStmtNode n) {
        printer.startList();
        printer.printAtom("return");
        for (ExprNode expr : n.exprs) {
            expr.accept(this);
        }
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(VarDeclStmtNode n) {
        n.varDecl.accept(this);

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(VarInitStmtNode n) {
        printer.startList();
        printer.printAtom("=");
        n.varDecl.accept(this);
        n.initializer.accept(this);
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(WhileStmtNode n) {
        printer.startList();
        printer.printAtom("while");
        n.guard.accept(this);
        n.block.accept(this);
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(LiteralArrayExprNode n) {
        printer.startList();
        for (ExprNode e : n.arrayVals) {
            e.accept(this);
        }
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(FunctionCallExprNode n) {
        printer.startList();

        printer.printAtom(n.identifier);

        for (ExprNode node : n.parameters) {
            node.accept(this);
        }

        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(AddExprNode n) {
        printBinExpr("+", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(AndExprNode n) {
        printBinExpr("&", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(DivExprNode n) {
        printBinExpr("/", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(EqualsExprNode n) {
        printBinExpr("==", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(GTEExprNode n) {
        printBinExpr(">=", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(GTExprNode n) {
        printBinExpr(">", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(HighMultExprNode n) {
        printBinExpr("*>>", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(LTEExprNode n) {
        printBinExpr("<=", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(LTExprNode n) {
        printBinExpr("<", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(MultExprNode n) {
        printBinExpr("*", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(NotEqualsExprNode n) {
        printBinExpr("!=", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(OrExprNode n) {
        printBinExpr("|", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(RemExprNode n) {
        printBinExpr("%", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(SubExprNode n) {
        printBinExpr("-", n);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(LiteralBoolExprNode n) {
        printer.printAtom(String.valueOf(n.contents));
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(LiteralCharExprNode n) {
        printer.printAtom("'" + Util.unescapeCharacterString(n.contents) + "'");
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(LiteralIntExprNode n) {
        printer.printAtom(n.contents);
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(LiteralStringExprNode n) {
        printer.printAtom("\"" + Util.unescapeString(n.contents) + "\"");
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(BoolNegExprNode n) {
        printer.startList();
        printer.printAtom("!");
        n.expr.accept(this);
        printer.endList();
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(IntNegExprNode n) {
        printer.startList();
        printer.printAtom("-");
        n.expr.accept(this);
        printer.endList();
        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(LengthExprNode n) {
        printer.startList();

        printer.printAtom("length");

        n.expr.accept(this);

        printer.endList();

        return Optional.empty();
    }

    private Optional<Void> printBinExpr(String symbol, BinExprNode n) {
        printer.startList();
        printer.printAtom(symbol);
        n.left.accept(this);
        n.right.accept(this);
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(ArrayAccessExprNode n) {
        printer.startList();
        printer.printAtom("[]");
        n.child.accept(this);
        n.index.accept(this);
        printer.endList();

        return Optional.empty();
    }

    @Override
    public Optional<Void> visit(VariableAccessExprNode n) {
        printer.printAtom(n.identifier);

        return Optional.empty();
    }

}
