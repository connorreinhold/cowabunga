package cyr7.ir.optimize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.util.OneOfThree;
import cyr7.visitor.MyIRVisitor;

public class IRAlgebraOptimizer {

    private IRAlgebraOptimizer() {}

    /**
     * Performs algebraic simplification/optimization on all
     * functions/statements/expressions in the {@code compUnit}.
     * @param comp
     * @return
     */
    public static IRCompUnit optimize(IRCompUnit compUnit) {
        return (IRCompUnit)compUnit.accept(AlgebraOptVisitor.INSTANCE)
                                   .assertSecond();
    }

    /**
     * Called by the OneOfThree<IRExpr, IRStmt, IRFuncDecl>'s OneOfThree<IRExpr,
     * IRStmt>.accept(IRVisitor) method. The returned value is relative to the
     * callee node. In this implementation for algebraic optimization, the
     * return type is an OneOfThree<IRExpr, IRStmt, IRFuncDecl>, in which the returned
     * OneOfThree<IRExpr, IRStmt, IRFuncDecl> represents a version of that node,
     * with algebraic simplifications/optimizations if possible.
     * <p>
     * For any node, the fields of nodes are recursively simplified.
     * The returned node has all of its child nodes folded if possible.
     *
     */
    private enum AlgebraOptVisitor
            implements MyIRVisitor<OneOfThree<IRExpr, IRStmt, IRFuncDecl>> {

        INSTANCE;

        private boolean isZero(IRExpr n) {
            return n.isConstant() && n.constant() == 0;
        }

        private boolean isOne(IRExpr n) {
            return n.isConstant() && n.constant() == 1;
        }

        /**
         * Requires: {@code n} is a constant and is a power of two. This can be
         * checked via isConstant() and isPowerOfTwo().
         */
        protected long logBase2(IRExpr n) {
            long num = n.constant();
            long position = 1;
            for (int i = 0; i < 64; i++) {
                if ((position & num) == position) {
                    return i;
                } else {
                    position <<= 1;
                }
            }
            throw new UnsupportedOperationException("Tried to get exponent of 0.");
        }

        private boolean isPowerOfTwo(IRExpr n) {
            if (n.isConstant()) {
                long num = n.constant();
                return num != 0 && ((num & (num - 1)) == 0);
            } else {
                return false;
            }
        }

        private IRExpr simplifyAdd(IRBinOp n) {
            IRExpr left = n.left();
            IRExpr right = n.right();
            if (this.isZero(left)) {
                return right;
            } else if (this.isZero(right)) {
                return left;
            } else {
                return n;
            }
        }

        private IRExpr simplifyDivision(IRBinOp n, IRNodeFactory make) {
            IRExpr left = n.left();
            IRExpr right = n.right();

            if (this.isZero(right)) {
                return n;
            } else if (this.isZero(left)) {
                return left;
            } else if (this.isOne(right)) {
                return left;
            }

            return n;
        }

        private IRExpr simplifyMod(IRBinOp n) {
            IRExpr left = n.left();
            IRExpr right = n.right();

            if (this.isZero(right)) {
                return n;
            } else if (this.isZero(left)) {
                return left;
            }

            return n;
        }

        private IRExpr simplifySub(IRBinOp n) {
            if (this.isZero(n.right())) {
                return n.left();
            }
            return n;
        }

        private IRExpr simplifyMul(IRBinOp n, IRNodeFactory make) {
            IRExpr left = n.left();
            IRExpr right = n.right();

            if (this.isZero(left)) {
                return left;
            } else if (this.isZero(right)) {
                return right;
            }

            if (this.isOne(left)) {
                return right;
            } else if (this.isOne(right)) {
                return left;
            }

            if (this.isPowerOfTwo(n.left())) {
                long power = this.logBase2(left);
                return make.IRBinOp(OpType.LSHIFT, right, make.IRConst(power));
            }

            if (this.isPowerOfTwo(n.right())) {
                long power = this.logBase2(right);
                return make.IRBinOp(OpType.LSHIFT, left, make.IRConst(power));
            }

            return n;
        }

        private IRExpr simplifyHighMul(IRBinOp n) {
            IRExpr left = n.left();
            IRExpr right = n.right();
            if (this.isZero(left))
                return left;
            if (this.isZero(right))
                return right;
            return n;
        }

        private IRExpr simplifyAnd(IRBinOp n) {
            IRExpr left = n.left();
            IRExpr right = n.right();
            if (this.isZero(left)) {
                return left;
            } else if (this.isZero(right)) {
                return right;
            }
            return n;
        }

        private IRExpr simplifyXOR(IRBinOp binop, IRNodeFactory make) {
            if (binop.left()
                    .equals(binop.right())) {
                return make.IRConst(0);
            }

            if (this.isOne(binop.left()) && binop.right() instanceof IRBinOp) {
                // Negate the right hand side
                IRBinOp n = (IRBinOp) binop.right();
                switch (n.opType()) {
                case EQ:
                    return make.IRBinOp(OpType.NEQ, n.left(), n.right());
                case GEQ:
                    return make.IRBinOp(OpType.LT, n.left(), n.right());
                case GT:
                    return make.IRBinOp(OpType.LEQ, n.left(), n.right());
                case LEQ:
                    return make.IRBinOp(OpType.GT, n.left(), n.right());
                case LT:
                    return make.IRBinOp(OpType.GEQ, n.left(), n.right());
                case NEQ:
                    return make.IRBinOp(OpType.EQ, n.left(), n.right());
                case XOR:
                    return this.isOne(n.left()) ? n.right() : binop;
                default:
                    break;
                }
            }
            return binop;
        }

        private IRExpr simplifyComparison(IRBinOp n, IRNodeFactory make) {
            IRExpr trueExpr = make.IRConst(1);
            IRExpr falseExpr = make.IRConst(0);

            if (n.left()
                    .equals(n.right())) {
                switch (n.opType()) {
                case EQ:
                    return trueExpr;
                case NEQ:
                    return falseExpr;
                case LT:
                    return falseExpr;
                case GT:
                    return falseExpr;
                case LEQ:
                    return trueExpr;
                case GEQ:
                    return trueExpr;
                default:
                    return n;
                }
            }
            return n;
        }

        /**
         *
         * If one of the binary operator's two operands is not a constant, then the
         * binary operator node is not a constant.
         * <p>
         * If binary operator's type is MOD or DIV, then if the right operand is is
         * zero, then return.
         */
        private IRExpr performConstantBinop(IRBinOp n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());

            final IRExpr left = n.left()
                    .accept(this)
                    .assertFirst();
            final IRExpr right = n.right()
                    .accept(this)
                    .assertFirst();

            IRBinOp foldedValue = make.IRBinOp(n.opType(), left, right);
            if (left.isConstant() && right.isConstant()) {
                return n;
            }

            // // Copied from staff-given interpreter code.
            switch (n.opType()) {
            case ADD:
                return this.simplifyAdd(foldedValue);
            case SUB:
                return this.simplifySub(foldedValue);
            case MUL:
                return this.simplifyMul(foldedValue, make);
            case HMUL:
                return this.simplifyHighMul(foldedValue);
            case DIV:
                return this.simplifyDivision(foldedValue, make);
            case MOD:
                return this.simplifyMod(foldedValue);
            case AND:
                return this.simplifyAnd(foldedValue);
            case OR:
                return this.simplifyAdd(foldedValue);
            case XOR:
                return this.simplifyXOR(foldedValue, make);
            case LSHIFT:
                break;
            case RSHIFT:
                break;
            case ARSHIFT:
                break;
            case EQ:
                return this.simplifyComparison(foldedValue, make);
            case NEQ:
                return this.simplifyComparison(foldedValue, make);
            case LT:
                return this.simplifyComparison(foldedValue, make);
            case GT:
                return this.simplifyComparison(foldedValue, make);
            case LEQ:
                return this.simplifyComparison(foldedValue, make);
            case GEQ:
                return this.simplifyComparison(foldedValue, make);
            default:
                throw new UnsupportedOperationException("Invalid binary operation");
            }

            return foldedValue;
        }

        /**
         * CFold[ESeq(...s, e)] ::= ESeq(CFold[...s], CFold[e])
         */
        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRESeq n) {
            throw new UnsupportedOperationException(
                    "Found ESeq when constant folding LIR Tree.");
        }

        /**
         * CFold[Exp(e)] ::= Exp(CFold[etarget])
         */
        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRExp n) {
            throw new UnsupportedOperationException(
                    "Found Exp while constant folding LIR Tree.");
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRBinOp n) {
            return OneOfThree.ofFirst(this.performConstantBinop(n));
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRCall n) {
            throw new UnsupportedOperationException(
                    "Found CALL while constant folding LIR Tree.");
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRConst n) {
            return OneOfThree.ofFirst(n);
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRMem n) {
            final var memExpr = n.expr().accept(this).assertFirst();
            return OneOfThree.ofFirst(new IRMem(n.location(), memExpr));
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRName n) {
            return OneOfThree.ofFirst(n);
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRTemp n) {
            return OneOfThree.ofFirst(n);
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRCallStmt n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());

            IRExpr target = n.target().accept(this).assertFirst();
            List<IRExpr> foldedArgs = n.args().stream().map(a -> {
                return a.accept(this).assertFirst();
            }).collect(Collectors.toList());

            return OneOfThree
                    .ofSecond(make.IRCallStmt(n.collectors(), target, foldedArgs));
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRCJump n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());
            IRExpr cond = n.cond().accept(this).assertFirst();
            return OneOfThree
                    .ofSecond(make.IRCJump(cond, n.trueLabel(), n.falseLabel()));
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRCompUnit n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());
            Map<String, IRFuncDecl> functions = new HashMap<>();
            n.functions().forEach((k, v) -> {
                IRFuncDecl f = v.accept(this).assertThird();
                functions.put(k, f);
            });
            return OneOfThree.ofSecond(make.IRCompUnit(n.name(), functions));
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRFuncDecl n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());
            IRStmt body = n.body().accept(this).assertSecond();
            return OneOfThree.ofThird(make.IRFuncDecl(n.name(), body, n.type()));
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRJump n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());
            IRExpr target = n.target().accept(this).assertFirst();
            return OneOfThree.ofSecond(make.IRJump(target));
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRLabel n) {
            return OneOfThree.ofSecond(n);
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRMove n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());
            IRExpr src = n.source().accept(this).assertFirst();
            IRExpr dest = n.target().accept(this).assertFirst();
            return OneOfThree.ofSecond(make.IRMove(dest, src));
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRReturn n) {
            return OneOfThree.ofSecond(n);
        }

        @Override
        public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRSeq n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());
            List<IRStmt> foldedStmts = n.stmts().stream()
                    .map(s -> s.accept(this).assertSecond())
                    .collect(Collectors.toList());
            return OneOfThree.ofSecond(make.IRSeq(foldedStmts));
        }
    }
}
