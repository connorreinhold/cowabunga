package cyr7.ir.nodes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.semantics.types.FunctionType;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class IRNodeFactory_c implements IRNodeFactory {

    private final Location location;

    public IRNodeFactory_c(Location location) {
        this.location = location;
    }

    @Override
    public IRBinOp IRBinOp(OpType type, IRExpr left, IRExpr right) {
        return new IRBinOp(location, type, left, right);
    }

    @Override
    public IRCall IRCall(IRExpr target, IRExpr... args) {
        return new IRCall(location, target, args);
    }

    @Override
    public IRCall IRCall(IRExpr target, List<IRExpr> args) {
        return new IRCall(location, target, args, 10);
    }

    @Override
    public IRCall IRCall(IRExpr target, List<IRExpr> args, int numOfRV) {
        return new IRCall(location, target, args, numOfRV);
    }

    @Override
    public IRCJump IRCJump(IRExpr expr, String trueLabel) {
        return new IRCJump(location, expr, trueLabel);
    }

    @Override
    public IRCJump IRCJump(IRExpr expr, String trueLabel, String falseLabel) {
        return new IRCJump(location, expr, trueLabel, falseLabel);
    }

    @Override
    public IRCJump IRCJump(IRExpr expr, String trueLabel, Optional<String> falseLabel) {
        return new IRCJump(location, expr, trueLabel, falseLabel);
    }

    @Override
    public IRCompUnit IRCompUnit(String name) {
        return new IRCompUnit(location, name);
    }

    @Override
    public IRCompUnit IRCompUnit(String name,
            Map<String, IRFuncDecl> functions) {
        return new IRCompUnit(location, name, functions);
    }

    @Override
    public IRConst IRConst(long value) {
        return new IRConst(location, value);
    }

    @Override
    public IRESeq IRESeq(IRStmt stmt, IRExpr expr) {
        return new IRESeq(location, stmt, expr);
    }

    @Override
    public IRExp IRExp(IRExpr expr) {
        return new IRExp(location, expr);
    }

    @Override
    public IRFuncDecl IRFuncDecl(String name, IRStmt stmt, FunctionType type) {
        return new IRFuncDecl(location, name, stmt, type);
    }

    @Override
    public IRFuncDecl IRFuncDecl(String name, IRStmt stmt) {
        return new IRFuncDecl(location, name, stmt);
    }

    @Override
    public IRJump IRJump(IRExpr expr) {
        return new IRJump(location, expr);
    }

    @Override
    public IRLabel IRLabel(String name) {
        return new IRLabel(location, name);
    }

    @Override
    public IRMem IRMem(IRExpr expr) {
        return new IRMem(location, expr);
    }

    @Override
    public IRCallStmt IRCallStmt(List<String> collectors, IRExpr target,
            List<IRExpr> args) {
        return new IRCallStmt(location, collectors, target, args);
    }

    @Override
    public IRCallStmt IRCallStmt(IRExpr target) {
        return new IRCallStmt(location, List.of(), target, List.of());
    }

    @Override
    public IRMove IRMove(IRExpr target, IRExpr expr) {
        return new IRMove(location, target, expr);
    }

    @Override
    public IRName IRName(String name) {
        return new IRName(location, name);
    }

    @Override
    public IRReturn IRReturn() {
        return new IRReturn(location);
    }

    @Override
    public IRSeq IRSeq(IRStmt... stmts) {
        return new IRSeq(location, stmts);
    }

    @Override
    public IRSeq IRSeq(List<IRStmt> stmts) {
        return new IRSeq(location, stmts);
    }

    @Override
    public IRTemp IRTemp(String name) {
        return new IRTemp(location, name);
    }

}
