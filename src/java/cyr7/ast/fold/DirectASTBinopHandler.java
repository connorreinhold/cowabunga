package cyr7.ast.fold;

import java.math.BigInteger;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.binexpr.BinExprNode;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;

public class DirectASTBinopHandler extends AbstractASTBinopHandler {

    public static final DirectASTBinopHandler instance = new DirectASTBinopHandler();

    private DirectASTBinopHandler() {
    }

    @Override
    protected ExprNode handleAnd(BinExprNode n) {
        if (left instanceof LiteralBoolExprNode
                && right instanceof LiteralBoolExprNode) {
            var boolLeft = ((LiteralBoolExprNode) left).contents;
            var boolRight = ((LiteralBoolExprNode) right).contents;
            var newValue = boolLeft && boolRight;
            return new LiteralBoolExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleOr(BinExprNode n) {
        if (left instanceof LiteralBoolExprNode
                && right instanceof LiteralBoolExprNode) {
            var boolLeft = ((LiteralBoolExprNode) left).contents;
            var boolRight = ((LiteralBoolExprNode) right).contents;
            var newValue = boolLeft || boolRight;
            return new LiteralBoolExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleAdd(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = String.valueOf(intLeft + intRight);
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleSub(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = String.valueOf(intLeft - intRight);
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleMult(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = String.valueOf(intLeft * intRight);
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleDiv(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = String.valueOf(intLeft / intRight);
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleMod(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = String.valueOf(intLeft % intRight);
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleHighMult(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = BigInteger.valueOf(
                    Long.parseLong(((LiteralIntExprNode) left).contents));
            var intRight = BigInteger.valueOf(
                    Long.parseLong(((LiteralIntExprNode) right).contents));
            var newValue = String.valueOf(
                    intLeft.multiply(intRight).shiftRight(64).longValue());
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleGT(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = String.valueOf(intLeft > intRight);
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleGEQ(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = String.valueOf(intLeft >= intRight);
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleLT(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = String.valueOf(intLeft < intRight);
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleLEQ(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = String.valueOf(intLeft <= intRight);
            return new LiteralIntExprNode(n.getLocation(), newValue);
        }
        return n;
    }

    @Override
    protected ExprNode handleEQ(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = intLeft == intRight;
            return new LiteralBoolExprNode(n.getLocation(), newValue);
        } else if (left instanceof LiteralBoolExprNode
                && right instanceof LiteralBoolExprNode) {
            var boolLeft = ((LiteralBoolExprNode) left).contents;
            var boolRight = ((LiteralBoolExprNode) right).contents;
            var newValue = boolLeft == boolRight;
            return new LiteralBoolExprNode(n.getLocation(), newValue);
        } else if (left instanceof LiteralArrayExprNode
                && right instanceof LiteralArrayExprNode) {
            return new LiteralBoolExprNode(n.getLocation(), false);
        }
        return n;
    }

    @Override
    protected ExprNode handleNEQ(BinExprNode n) {
        if (left instanceof LiteralIntExprNode
                && right instanceof LiteralIntExprNode) {
            var intLeft = Long.parseLong(((LiteralIntExprNode) left).contents);
            var intRight = Long
                    .parseLong(((LiteralIntExprNode) right).contents);
            var newValue = intLeft != intRight;
            return new LiteralBoolExprNode(n.getLocation(), newValue);
        } else if (left instanceof LiteralBoolExprNode
                && right instanceof LiteralBoolExprNode) {
            var boolLeft = ((LiteralBoolExprNode) left).contents;
            var boolRight = ((LiteralBoolExprNode) right).contents;
            var newValue = boolLeft != boolRight;
            return new LiteralBoolExprNode(n.getLocation(), newValue);
        } else if (left instanceof LiteralArrayExprNode
                && right instanceof LiteralArrayExprNode) {
            return new LiteralBoolExprNode(n.getLocation(), true);
        }
        return n;
    }

}
