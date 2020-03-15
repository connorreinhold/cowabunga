package cyr7.ir.fold.identity;

import java.util.Optional;

import cyr7.ir.fold.visitors.BooleanNegationConstFoldVisitor;
import cyr7.ir.fold.visitors.CheckNegationFoldConstVisitor;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;

public class BinopHandler {

	private static IRNodeFactory make; 
	private static IRExpr left; 
	private static IRExpr right; 
	
	private BinopHandler() {}
	
	public static IRExpr accept(OpType opType, IRBinOp n) {
		make = new IRNodeFactory_c(n.location());
		
		left = n.left();
		right = n.right();
		
		switch (opType) {
		case ADD:
			return handleAdd(n);
		case AND:
			return handleAnd(n);
		case ARSHIFT:
			return handleARShift(n);
		case DIV:
			return handleDiv(n);
		case EQ:
			return handleEQ(n);
		case GEQ:
			return handleGEQ(n);
		case GT:
			return handleGT(n);
		case HMUL:
			return handleHighMult(n);
		case LEQ:
			return handleLEQ(n);
		case LSHIFT:
			return handleLShift(n);
		case LT:
			return handleLT(n);
		case MOD:
			return handleMod(n);
		case MUL:
			return handleMult(n);
		case NEQ:
			return handleNEQ(n);
		case OR:
			return handleOr(n);
		case RSHIFT:
			return handleRShift(n);
		case SUB:
			return handleSub(n);
		case XOR:
			return handleXOR(n);
		default:
			throw new RuntimeException("Missing case for opType: " + opType);
		}
	}

	
    private static Optional<IRExpr> tryDeMorgan(IRBinOp n) {
    	OpType innerOperation;
    	if (n.opType() == OpType.AND) innerOperation = OpType.OR;
    	else if (n.opType() == OpType.OR) innerOperation = OpType.AND;
    	else throw new RuntimeException("Failing De Morgan precondition. Found " + n.opType());
    	
        var possibleLeftNeg = n.left()
                .accept(CheckNegationFoldConstVisitor.instance);
        var possibleRightNeg = n.right()
                .accept(CheckNegationFoldConstVisitor.instance);
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
	
	private static IRExpr handleAnd(IRBinOp n) {
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

	private static IRExpr handleOr(IRBinOp n) {
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

	private static IRExpr handleAdd(IRBinOp n) {
        if (left.isConstant() && left.constant() == 0) {
            return right;
        } else if (n.right().isConstant() && n.right().constant() == 0) {
            return left;
        }
        return n;
	}

	private static IRExpr handleSub(IRBinOp n) {
        if (right.isConstant() && right.constant() == 0) {
            return left;
        }
        return n;
	}
	
    private static long logBase2(long num) {
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

    private static boolean isPowerOfTwo(long num) {
        return num != 0 && ((num & (num - 1)) == 0);
    }
	
	private static IRExpr handleMult(IRBinOp n) {
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

	private static IRExpr handleDiv(IRBinOp n) {
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

	private static IRExpr handleMod(IRBinOp n) {
		return n;
	}

	private static IRExpr handleHighMult(IRBinOp n) {
		return n;
	}

	private static IRExpr handleGT(IRBinOp n) {
		return n;
	}

	private static IRExpr handleGEQ(IRBinOp n) {
		return n;
	}

	private static IRExpr handleLT(IRBinOp n) {
		return n;
	}

	private static IRExpr handleLEQ(IRBinOp n) {
		return n;
	}

	private static IRExpr handleEQ(IRBinOp n) {
		return n;
	}

	private static IRExpr handleNEQ(IRBinOp n) {
		return n;
	}

	private static IRExpr handleRShift(IRBinOp n) {
		return n;
	}

	private static IRExpr handleARShift(IRBinOp n) {
		return n;
	}

	private static IRExpr handleLShift(IRBinOp n) {
		return n;
	}

	private static IRExpr handleXOR(IRBinOp n) {
        if (n.left().isConstant() && n.left().constant() == 1) {
            // Apply negation tactics.
            return n.right().accept(new BooleanNegationConstFoldVisitor(n));
        }
        return n;
	}

}
