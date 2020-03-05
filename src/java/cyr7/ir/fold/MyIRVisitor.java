package cyr7.ir.fold;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;

/**
 *
 * Called by the IRNode's {@link IRNode.accept(IRVisitor)} method.
 *
 * The returned value is relative to the callee node. For example, in an
 * implementation for constant folding, the return type may be IRNode, in which
 * the returned IRNode represents a constant-folded version of that node.
 *
 * @author ayang
 *
 * @param <T>
 */
public interface MyIRVisitor<T> {

    // Expressions

    public T visit(IRBinOp n);

    public T visit(IRCall n);

    public T visit(IRConst n);

    public T visit(IRESeq n);

    public T visit(IRMem n);

    public T visit(IRName n);

    public T visit(IRTemp n);

    // Statements

    public T visit(IRCallStmt n);

    public T visit(IRCJump n);

    public T visit(IRCompUnit n);

    public T visit(IRExp n);

    public T visit(IRFuncDecl n);

    public T visit(IRJump n);

    public T visit(IRLabel n);

    public T visit(IRMove n);

    public T visit(IRReturn n);

    public T visit(IRSeq n);


}
