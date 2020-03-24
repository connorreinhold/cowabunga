package cyr7.x86;

import cyr7.ir.nodes.*;
import cyr7.semantics.types.FunctionType;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.*;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import java_cup.internal_error;
import cyr7.x86.TilerData;
import cyr7.exceptions.semantics.UnsummableValuesException;
import cyr7.ir.IdGenerator;
import cyr7.ir.interpret.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public class BasicTiler implements MyIRVisitor<TilerData> {

    private final IdGenerator generator;
    private final Map<String, FunctionType> fMap;
    private final int numRetValues;
    private final String returnLbl;

    public BasicTiler(IdGenerator generator, String tiledFunctionName, Map<String, FunctionType> fMap) {
        this.generator = generator;
        this.fMap = Collections.unmodifiableMap(fMap);
        this.numRetValues = this.fMap.get(tiledFunctionName).output.getTypes().size();
        this.returnLbl = "end_" + tiledFunctionName;
    }

    private ASMConstArg constant(long n) {
        return new ASMConstArg(n);
    }

    private ASMLabelArg label(String name) {
        return new ASMLabelArg(name);
    }

    private ASMRegArg reg(Register reg) {
        return new ASMRegArg(reg);
    }

    private ASMAddrExpr addr(Optional<Register> base, ScaleValues scale, Optional<Register> index, int displacement) {
        return new ASMAddrExpr(base, scale, index, displacement);
    }

    private ASMMemArg mem(ASMAddrExpr addr) {
        return new ASMMemArg(addr);
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
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.Add(ret, right.result.get()));
                break;
            case AND:
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.And(ret, right.result.get()));
                break;
            case ARSHIFT:
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.ARShift(ret, right.result.get()));
                break;
            case DIV:
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.Div(ret, right.result.get()));
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
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.LShift(ret, right.result.get()));
                break;
            case LT:
                break;
            case MOD:
                break;
            case MUL:
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.Mul(ret, right.result.get()));
                break;
            case NEQ:
                break;
            case OR:
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.Or(ret, right.result.get()));
                break;
            case RSHIFT:
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.RShift(ret, right.result.get()));
                break;
            case SUB:
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.Sub(ret, right.result.get()));
                break;
            case XOR:
                insns.add(ASMFactory.Mov(ret, left.result.get()));
                insns.add(ASMFactory.Xor(ret, right.result.get()));
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
        List<ASMLine> insns = List.of(ASMFactory.Mov(ret, new ASMConstArg(n.constant())));
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
        List.of(ASMFactory.Mov(ret, new ASMMemArg(address)));

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
            ASMFactory.Lea(new ASMRegArg(Register.RDI), new ASMConstArg(size));
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
        insn.add(ASMFactory.Cmp(temp, new ASMConstArg(1)));
        insn.add(ASMFactory.JumpE(new ASMLabelArg(n.trueLabel())));

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
            instructions.add(ASMFactory.Jump(target.result.get()));
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
                        instrs.add(ASMFactory.Mov(new ASMRegArg(Register.RAX), target.result.get()));
                        break;
                    case 1:
                        instrs.add(ASMFactory.Mov(new ASMRegArg(Register.RDX), target.result.get()));
                        break;
                    default:
                        asdlkfjasdlkfjasdflkjasdflkjasdflkjasdflvaj;sn;lfajsdfal;jdfalvjadfl;jna sdvjhafoha sgihesjof wefioj oighwraoig waouhfawouhaoshaskjgh aksfvhakbh akh
                }
            }
        } else if (n.target() instanceof IRTemp) {

        } else {
            List<ASMLine> instructions = new ArrayList<>();
            instructions.add(ASMFactory.Mov(target.result.get(), source.result.get()));
            return new TilerData(1 + target.tileCost + source.tileCost, instructions, Optional.empty());
        }
    }

    @Override
    public TilerData visit(IRReturn n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> instructions = new ArrayList<>();
        instructions.add(ASMFactory.Jump(new ASMLabelArg(this.returnLbl)));
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
