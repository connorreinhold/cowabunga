package cyr7.ir.fold.binophandler;

import java.util.Optional;

import cyr7.ir.fold.visitors.BooleanNegationConstFoldVisitor;
import cyr7.ir.fold.visitors.CheckNegationFoldConstVisitor;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRExpr;

public class IdentityBinopHandler extends AbstractBinopHandler {

	public static IdentityBinopHandler instance = new IdentityBinopHandler();	
	private IdentityBinopHandler() {}
	
    protected Optional<IRExpr> tryDeMorgan(IRBinOp n) {
    	OpType innerOperation;
    	if (n.opType() == OpType.AND) innerOperation = OpType.OR;
    	else if (n.opType() == OpType.OR) innerOperation = OpType.AND;
    	else throw new RuntimeException("Failing De Morgan precondition. Found " + n.opType());
    	
        var possibleLeftNeg = n.left().accept(CheckNegationFoldConstVisitor.instance);
        var possibleRightNeg = n.right().accept(CheckNegationFoldConstVisitor.instance);
        if (possibleLeftNeg.isPresent() && possibleRightNeg.isPresent()) {
            // Apply De Morgan's
            var left = possibleLeftNeg.get();
            var right = possibleRightNeg.get();
            return Optional.of(make.IRBinOp(OpType.XOR,
                    make.IRConst(1),
                    make.IRBinOp(innerOperation, left, right)));
        }
        return Optional.empty();
    }
	
	protected IRExpr handleAnd(IRBinOp n) {
        Optional<IRExpr> maybeDeMorgan = tryDeMorgan(n);
        if (maybeDeMorgan.isPresent()) {
            return maybeDeMorgan.get();
        }
        if (left.isConstant() && left.constant() == 1) {
            return right;
        } else if (right.isConstant() && right.constant() == 1) {
        	return left;
        }
        return n;
	}

	protected IRExpr handleOr(IRBinOp n) {
        Optional<IRExpr> maybeDeMorgan = tryDeMorgan(n);
        if (maybeDeMorgan.isPresent()) {
            return maybeDeMorgan.get();
        }
        if (n.left().isConstant() && n.left().constant() == 0) {
            return right;
        } else if (n.right().isConstant() && n.right().constant() == 0) {
            return left;
        }
        return n;
	}

	protected IRExpr handleAdd(IRBinOp n) {
        if (left.isConstant() && left.constant() == 0) {
            return right;
        } else if (n.right().isConstant() && n.right().constant() == 0) {
            return left;
        }
        return n;
	}

	protected IRExpr handleSub(IRBinOp n) {
        if (right.isConstant() && right.constant() == 0) {
            return left;
        }
        return n;
	}
	
    protected long logBase2(long num) {
        if (isPowerOfTwo(num)) {
            long position = 1;
            for (int i = 0; i < 64; i++) {
                if ((position & num) == 0) {
                    return i;
                } else {
                    position <<= 1;
                }
            }
        }
        throw new UnsupportedOperationException();
    }

    protected boolean isPowerOfTwo(long num) {
        return num != 0 && ((num & (num - 1)) == 0);
    }
	
	protected IRExpr handleMult(IRBinOp n) {
        if (left.isConstant() && left.constant() == 1) {
            return right;
        } else if (right.isConstant() && right.constant() == 1) {
            return left;
        } else if (right.isConstant()) {
            long rightConstant = right.constant();
            if (isPowerOfTwo(rightConstant)) {
                return make.IRBinOp(OpType.LSHIFT, left, make.IRConst(logBase2(rightConstant)));
            }
        } else if (left.isConstant()) {
            long leftConstant = n.left().constant();
            if (isPowerOfTwo(leftConstant)) {
                return make.IRBinOp(OpType.LSHIFT,
                        right, make.IRConst(logBase2(leftConstant)));
            }
        }
        return n;
	}

	protected IRExpr handleDiv(IRBinOp n) {
        if (right.isConstant() && right.constant() == 1) {
            return left;
        } else if (right.isConstant()) {
            long rightConstant = right.constant();
            long power = logBase2(rightConstant);
            if (isPowerOfTwo(rightConstant)) {
                return make.IRBinOp(OpType.ARSHIFT, left, make.IRConst(power));
            }
        }
        return n;
	}

	protected IRExpr handleMod(IRBinOp n) {
		return n;
	}

	protected IRExpr handleHighMult(IRBinOp n) {
		return n;
	}

	protected IRExpr handleGT(IRBinOp n) {
		return n;
	}

	protected IRExpr handleGEQ(IRBinOp n) {
		return n;
	}

	protected IRExpr handleLT(IRBinOp n) {
		return n;
	}

	protected IRExpr handleLEQ(IRBinOp n) {
		return n;
	}

	protected IRExpr handleEQ(IRBinOp n) {
		return n;
	}

	protected IRExpr handleNEQ(IRBinOp n) {
		return n;
	}

	protected IRExpr handleRShift(IRBinOp n) {
		return n;
	}

	protected IRExpr handleARShift(IRBinOp n) {
		return n;
	}

	protected IRExpr handleLShift(IRBinOp n) {
		return n;
	}

	protected IRExpr handleXOR(IRBinOp n) {
        if (n.left().isConstant() && n.left().constant() == 1) {
            // Apply negation tactics.
            return n.right().accept(new BooleanNegationConstFoldVisitor(n));
        }
        return n;
	}

}
