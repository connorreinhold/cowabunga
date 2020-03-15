package cyr7.ir.fold.binophandler;

import java.util.Optional;

import cyr7.ir.fold.visitors.BooleanNegationConstFoldVisitor;
import cyr7.ir.fold.visitors.CheckNegationFoldConstVisitor;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRBinOp.OpType;

public abstract class AbstractBinopHandler {

	protected IRNodeFactory make; 
	protected IRExpr left;
	protected IRExpr right;	
	
	public IRExpr accept(OpType opType, IRBinOp n) {
		this.make = new IRNodeFactory_c(n.location());
		this.left = n.left();
		this.right = n.right();
		
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

	protected abstract IRExpr handleAnd(IRBinOp n);
	protected abstract IRExpr handleOr(IRBinOp n);

	protected abstract IRExpr handleAdd(IRBinOp n);
	protected abstract IRExpr handleSub(IRBinOp n);
	protected abstract IRExpr handleMult(IRBinOp n);
	protected abstract IRExpr handleDiv(IRBinOp n);
	protected abstract IRExpr handleMod(IRBinOp n);
	protected abstract IRExpr handleHighMult(IRBinOp n);
	
	protected abstract IRExpr handleGT(IRBinOp n);
	protected abstract IRExpr handleGEQ(IRBinOp n);
	protected abstract IRExpr handleLT(IRBinOp n);
	protected abstract IRExpr handleLEQ(IRBinOp n);
	protected abstract IRExpr handleEQ(IRBinOp n);
	protected abstract IRExpr handleNEQ(IRBinOp n);
	
	protected abstract IRExpr handleRShift(IRBinOp n);
	protected abstract IRExpr handleARShift(IRBinOp n);
	protected abstract IRExpr handleLShift(IRBinOp n);
	protected abstract IRExpr handleXOR(IRBinOp n);
}
