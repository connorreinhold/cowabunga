package cyr7.ir.fold;

/**
 * Called by the OneOfThree<IRExpr, IRStmt, IRFuncDecl>'s OneOfThree<IRExpr,
 * IRStmt>.accept(IRVisitor) method. The returned value is relative to the
 * callee node. In this implementation for constant folding, the return type is
 * an OneOfThree<IRExpr, IRStmt, IRFuncDecl>, in which the returned
 * OneOfThree<IRExpr, IRStmt, IRFuncDecl> represents a constant-folded version
 * of that node, if possible.
 * <p>
 * For any node, the fields of nodes are recursively folded. The returned node
 * has all of its child nodes folded if possible.
 *
 * @author ayang
 *
 */
public class MIRConstFoldVisitor extends GeneralConstFoldVisitor {
}
