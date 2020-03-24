package cyr7.x86;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cyr7.ir.IdGenerator;
import cyr7.ir.interpret.Configuration;
import cyr7.ir.nodes.IRBinOp;
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
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.semantics.types.FunctionType;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMRegArg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.Register;

public class BasicTiler implements MyIRVisitor<TilerData> {

    private final IdGenerator generator;
    private final Map<String, FunctionType> fMap;
    private final int numRetValues;
    private final String returnLbl;
    private final ASMArgFactory arg;
    private final ASMLineFactory make;

    public BasicTiler(IdGenerator generator, String tiledFunctionName, Map<String, FunctionType> fMap) {
        this.generator = generator;
        this.fMap = Collections.unmodifiableMap(fMap);
        this.numRetValues = this.fMap.get(tiledFunctionName).output.getTypes().size();
        this.returnLbl = "end_" + tiledFunctionName;
        this.arg = ASMArgFactory.instance;
        this.make = ASMLineFactory.instance;
    }

    @Override
    public TilerData visit(IRBinOp n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> insns = new ArrayList<>();
        ASMArg ret = new ASMTempArg(generator.newTemp());

        TilerData left = n.left().accept(this);
        TilerData right = n.right().accept(this);

        insns.addAll(left.optimalInstructions);
        insns.addAll(right.optimalInstructions);

        switch (n.opType()) {
            case ADD:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.Add(ret, right.result.get()));
                break;
            case AND:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.And(ret, right.result.get()));
                break;
            case ARSHIFT:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.ARShift(ret, right.result.get()));
                break;
            case DIV:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.Div(ret, right.result.get()));
                break;
            case EQ:
                break;
            case GEQ:
                break;
            case GT:
                break;
            case HMUL:
                break;
            case LEQ:
                break;
            case LSHIFT:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.LShift(ret, right.result.get()));
                break;
            case LT:
                break;
            case MOD:
                break;
            case MUL:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.Mul(ret, right.result.get()));
                break;
            case NEQ:
                break;
            case OR:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.Or(ret, right.result.get()));
                break;
            case RSHIFT:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.RShift(ret, right.result.get()));
                break;
            case SUB:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.Sub(ret, right.result.get()));
                break;
            case XOR:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.Xor(ret, right.result.get()));
                break;
            default:
                throw new UnsupportedOperationException("No case found.");
        }

        TilerData result = new TilerData(1 + left.tileCost + right.tileCost, insns, Optional.of(ret));
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRCall n) {
        throw new UnsupportedOperationException("Call is not a valid node at this stage.");
    }

    @Override
    public TilerData visit(IRConst n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        ASMArg ret = new ASMTempArg(generator.newTemp());
        List<ASMLine> insns = List
                .of(make.Mov(ret, new ASMConstArg(n.constant())));
        TilerData result = new TilerData(1, insns, Optional.of(ret));
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRESeq n) {
        throw new UnsupportedOperationException("ESeq is not a valid node at this stage.");
    }

    @Override
    public TilerData visit(IRMem n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        TilerData expr = n.expr().accept(this);
        List<ASMLine> insns = new ArrayList<ASMLine>();
        insns.addAll(expr.optimalInstructions);
        ASMArg ret = new ASMTempArg(generator.newTemp());

        // TODO: Figure out the correct address
        ASMAddrExpr address = new ASMAddrExpr(Optional.empty(), ScaleValues.ONE, Optional.empty(), 0);
        List.of(make.Mov(ret, new ASMMemArg(address)));

        TilerData result = new TilerData(1 + expr.tileCost, insns, Optional.of(ret));
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRName n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        Optional<ASMArg> lbl = Optional.of(new ASMLabelArg(n.name()));
        TilerData result = new TilerData(1, List.of(), lbl);
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRTemp n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        ASMArg ret = new ASMTempArg(n.name());
        TilerData result = new TilerData(0, new ArrayList<>(), Optional.of(ret));
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRCallStmt n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        IRExpr target = n.target();
        List<IRExpr> args = n.args();
        if (this.numRetValues > 2) {
            long size = (this.numRetValues - 2) * 8;
            make.Lea(arg.reg(Register.RDI), arg.constant(size));
        } else {

        }

        return null;
    }

    @Override
    public TilerData visit(IRCJump n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        List<ASMLine> insn = new ArrayList<>();
        TilerData cond = n.cond().accept(this);
        ASMArg temp = cond.result.map(r -> r)
                .orElseThrow(() -> new RuntimeException("Expected a temporary but was not found."));
        insn.addAll(cond.optimalInstructions);
        insn.add(make.Cmp(temp, new ASMConstArg(1)));
        insn.add(make.JumpE(new ASMLabelArg(n.trueLabel())));

        TilerData result = new TilerData(1 + cond.tileCost, insn, Optional.empty());
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRCompUnit n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> insns = new ArrayList<ASMLine>();
        int tileCosts = 0;
        for (Map.Entry<String, IRFuncDecl> functionDecl : n.functions().entrySet()) {
            TilerData functionResult = functionDecl.getValue().accept(this);
            insns.addAll(functionResult.optimalInstructions);
            tileCosts += functionResult.tileCost;
        }
        TilerData result = new TilerData(tileCosts, insns, Optional.empty());
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRExp n) {
        throw new UnsupportedOperationException("Exp is not a valid node at this stage.");
    }

    @Override
    public TilerData visit(IRFuncDecl n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> instructions = new ArrayList<>();
        instructions.add(new ASMLabel(n.name()));
        // TODO: add prologue
        TilerData body = n.body().accept(this);
        instructions.addAll(body.optimalInstructions);
        // TODO: add epilogue
        TilerData result = new TilerData(instructions.size(), instructions, Optional.empty());
        return result;
    }

    @Override
    public TilerData visit(IRJump n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> instructions = new ArrayList<>();
        TilerData target = n.target().accept(this);
        instructions.addAll(target.optimalInstructions);

        TilerData result = new TilerData(target.tileCost, instructions, Optional.empty());
        if (target.result.isPresent()) {
            instructions.add(make.Jump(target.result.get()));
            result = new TilerData(1 + target.tileCost, instructions, Optional.empty());
        }
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRLabel n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> instructions = List.of(new ASMLabel(n.name()));

        TilerData result = new TilerData(instructions.size(), instructions, Optional.empty());
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRMove n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData target = n.target().accept(this);
        TilerData source = n.source().accept(this);
        List<ASMLine> instrs = new ArrayList<>();
        instrs.addAll(target.optimalInstructions);
        instrs.addAll(source.optimalInstructions);

        if (n.source() instanceof IRTemp) {
            IRTemp sourceTemp = (IRTemp) n.source();
            String RET_PREFIX = Configuration.ABSTRACT_RET_PREFIX;
            if (sourceTemp.name().startsWith(RET_PREFIX)) {
                String index = sourceTemp.name().substring(RET_PREFIX.length());

                int i = 0;
                try {
                    i = Integer.parseInt(index);
                } catch (NumberFormatException e) {
                    // should never happen, but what if?
                }

                switch (i) {
                    case 0:
                    instrs.add(make.Mov(new ASMRegArg(Register.RAX),
                            target.result.get()));
                        break;
                    case 1:
                    instrs.add(make.Mov(new ASMRegArg(Register.RDX),
                            target.result.get()));
                        break;
                    default:
                    break;
//                        asdlkfjasdlkfjasdflkjasdflkjasdflkjasdflvaj;sn;lfajsdfal;jdfalvjadfl;jna sdvjhafoha sgihesjof wefioj oighwraoig waouhfawouhaoshaskjgh aksfvhakbh akh
                }
            }
        } else if (n.target() instanceof IRTemp) {
        } else {
            List<ASMLine> instructions = new ArrayList<>();
            instructions
                    .add(make.Mov(target.result.get(), source.result.get()));
            return new TilerData(1 + target.tileCost + source.tileCost, instructions, Optional.empty());
        }
        return null;
    }

    @Override
    public TilerData visit(IRReturn n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> instructions = new ArrayList<>();
        instructions.add(make.Jump(new ASMLabelArg(this.returnLbl)));
        TilerData result = new TilerData(instructions.size(), instructions, Optional.empty());
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRSeq n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        List<ASMLine> instructions = new ArrayList<>();
        int sumCosts = 0;
        for (IRStmt s : n.stmts()) {
            TilerData data = s.accept(this);
            assert data.result.isEmpty();

            instructions.addAll(data.optimalInstructions);
            sumCosts += data.tileCost;
        }
        TilerData result = new TilerData(1 + sumCosts, instructions, Optional.empty());
        n.setOptimalTilingOnce(result);
        return result;
    }
}
