package cyr7.ir.fold;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import cyr7.ir.fold.visitors.AssociativePropertyVisitor;
import cyr7.ir.fold.visitors.BinopCalc;
import cyr7.ir.fold.visitors.BooleanNegationConstFoldVisitor;
import cyr7.ir.fold.visitors.CheckNegationFoldConstVisitor;
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
import java_cup.runtime.ComplexSymbolFactory.Location;

public final class ComplexConstantFoldVisitor
        implements MyIRVisitor<OneOfThree<IRExpr, IRStmt, IRFuncDecl>> {

    public static void main(String[] args) {
        IRNodeFactory make = new IRNodeFactory_c(new Location(-1, -1));

        IRExpr e = make.IRBinOp(OpType.ADD,
                make.IRBinOp(OpType.ADD, make.IRConst(12), make.IRTemp("t")),
                make.IRBinOp(OpType.ADD, make.IRConst(-3), make.IRTemp("a")));
        e = make.IRBinOp(OpType.XOR, make.IRConst(1),
                make.IRBinOp(OpType.XOR, make.IRConst(1), 
                make.IRBinOp(OpType.LEQ, 
                        make.IRTemp("d"), make.IRConst(3))));
        var visitor = new ComplexConstantFoldVisitor();

        var result = e.accept(visitor).assertFirst();

        System.out.println(result);
    }

    private long logBase2(long num) {
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

    private boolean isPowerOfTwo(long num) {
        return num != 0 && ((num & (num - 1)) == 0);
    }

    private Optional<IRExpr> tryDeMorgan(IRBinOp n, IRNodeFactory make,
            OpType innerOperation) {
        var possibleLeftNeg = n.left()
                .accept(CheckNegationFoldConstVisitor.instance);
        var possibleRightNeg = n.right()
                .accept(CheckNegationFoldConstVisitor.instance);
        if (possibleLeftNeg.isPresent() && possibleRightNeg.isPresent()) {
            // Apply De Morgan's
            return Optional.of(make.IRBinOp(OpType.XOR,
                    make.IRConst(1),
                    make.IRBinOp(innerOperation, n.left(), n.right())));
        }
        return Optional.empty();
    }

    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRBinOp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());

        IRExpr left = n.left().accept(this).assertFirst();
        IRExpr right = n.right().accept(this).assertFirst();


        n = make.IRBinOp(n.opType(), left, right);
        if (n.left().isConstant() && n.right().isConstant()) {
            return OneOfThree.ofFirst(BinopCalc.calc(n));
        }

        IRExpr result;
        Optional<IRExpr> maybeDeMorgan;
        switch (n.opType()) {
        case AND:
            maybeDeMorgan = this.tryDeMorgan(n, make, OpType.OR);
            if (maybeDeMorgan.isPresent()) {
                return OneOfThree.ofFirst(maybeDeMorgan.get());
            }
            if (n.left().isConstant() && n.left().constant() == 1) {
                return OneOfThree.ofFirst(n.right());
            } else if (n.right().isConstant() && n.right().constant() == 1) {
                return OneOfThree.ofFirst(n.left());
            }
            break;
        case OR:
            maybeDeMorgan = this.tryDeMorgan(n, make, OpType.OR);
            if (maybeDeMorgan.isPresent()) {
                return OneOfThree.ofFirst(maybeDeMorgan.get());
            }
            if (n.left().isConstant() && n.left().constant() == 0) {
                return OneOfThree.ofFirst(n.right());
            } else if (n.right().isConstant() && n.right().constant() == 0) {
                return OneOfThree.ofFirst(n.left());
            }
            break;
        case XOR:
            if (n.left().isConstant() && n.left().constant() == 1) {
                // Apply negation tactics.
                return OneOfThree.ofFirst(n.right()
                        .accept(new BooleanNegationConstFoldVisitor(n)));
            }
            break;
        case ADD:
            if (n.left().isConstant() && n.left().constant() == 0) {
                return OneOfThree.ofFirst(n.right());
            } else if (n.right().isConstant() && n.right().constant() == 0) {
                return OneOfThree.ofFirst(n.left());
            }
            break;
        case SUB:
            if (n.right().isConstant() && n.right().constant() == 0) {
                return OneOfThree.ofFirst(n.left());
            }
            break;
        case DIV:
            if (n.right().isConstant() && n.right().constant() == 1) {
                return OneOfThree.ofFirst(n.left());
            } else if (n.right().isConstant()) {
                long rightConstant = n.right().constant();
                if (isPowerOfTwo(rightConstant)) {
                    return OneOfThree.ofFirst(make.IRBinOp(OpType.ARSHIFT,
                            n.left(), make.IRConst(logBase2(rightConstant))));
                }
            }
            break;
        case MUL:
            if (n.left().isConstant() && n.left().constant() == 1) {
                return OneOfThree.ofFirst(n.right());
            } else if (n.right().isConstant() && n.right().constant() == 1) {
                return OneOfThree.ofFirst(n.left());
            } else if (n.right().isConstant()) {
                long rightConstant = n.right().constant();
                if (isPowerOfTwo(rightConstant)) {
                    return OneOfThree.ofFirst(make.IRBinOp(OpType.LSHIFT,
                            n.left(), make.IRConst(logBase2(rightConstant))));
                }
            } else if (n.left().isConstant()) {
                long leftConstant = n.left().constant();
                if (isPowerOfTwo(leftConstant)) {
                    return OneOfThree.ofFirst(make.IRBinOp(OpType.LSHIFT,
                            n.right(), make.IRConst(logBase2(leftConstant))));
                }
            }
            break;
        case MOD:
        case HMUL:
        case ARSHIFT:
        case LSHIFT:
        case RSHIFT:
        case GEQ:
        case GT:
        case LEQ:
        case LT:
        case EQ:
        case NEQ:
        default:
            break;
        }
        result = BinopCalc.calc(n);
        result = AssociativePropertyVisitor.valueOf(result);
        return OneOfThree.ofFirst(result);
    }

    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRCall n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRExpr> foldedArgs = n.args().stream().map(a -> {
            return a.accept(this).assertFirst();
        }).collect(Collectors.toList());

        IRExpr target = n.target().accept(this).assertFirst();

        return OneOfThree.ofFirst(make.IRCall(target, foldedArgs));
    }

    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRConst n) {
        return OneOfThree.ofFirst(n);
    }

    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRESeq n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        IRStmt stmt = n.stmt().accept(this).assertSecond();
        IRExpr expr = n.expr().accept(this).assertFirst();
        return OneOfThree.ofFirst(make.IRESeq(stmt, expr));
    }

    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRMem n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        return OneOfThree
                .ofFirst(make.IRMem(n.expr().accept(this).assertFirst()));
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
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRExp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        IRExpr expr = n.expr().accept(this).assertFirst();
        return OneOfThree.ofSecond(make.IRExp(expr));
    }

    /**
     * CFold[FuncDecl(name, body)] ::= FuncDecl(name, CFold[body])
     */
    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRFuncDecl n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        IRStmt body = n.body().accept(this).assertSecond();
        return OneOfThree.ofThird(make.IRFuncDecl(n.name(), body));
    }

    /**
     * CFold[Jump(etarget)] ::= Jump(CFold[etarget])
     */
    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRJump n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        IRExpr target = n.target().accept(this).assertFirst();
        return OneOfThree.ofSecond(make.IRJump(target));
    }

    /**
     * CFold[Label(l)] ::= Label(l)
     * <ul>
     * <li>No Change
     * </ul>
     */
    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRLabel n) {
        return OneOfThree.ofSecond(n);
    }

    /**
     * CFold[Move(edest, esrc)] ::= Move(CFold[edest], CFold[esrc])
     */
    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRMove n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        IRExpr src = n.source().accept(this).assertFirst();
        IRExpr dest = n.target().accept(this).assertFirst();
        return OneOfThree.ofSecond(make.IRMove(dest, src));
    }

    /**
     * CFold[Return] ::= Return
     * <ul>
     * <li>No Change
     * </ul>
     */
    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRReturn n) {
        return OneOfThree.ofSecond(n);
    }

    /**
     * CFold[Seq(...s)] ::= Seq(CFold[...s])
     */
    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRSeq n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRStmt> foldedStmts = n.stmts().stream()
                .map(s -> s.accept(this).assertSecond())
                .collect(Collectors.toList());
        return OneOfThree.ofSecond(make.IRSeq(foldedStmts));
    }


}
