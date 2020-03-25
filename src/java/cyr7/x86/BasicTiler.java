package cyr7.x86;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final ASMArg rax = new ASMRegArg(Register.RAX);
    private final ASMArg rsp = new ASMRegArg(Register.RSP);
    private final ASMArg rdi = new ASMRegArg(Register.RDI);
    private final ASMArg rsi = new ASMRegArg(Register.RSI);
    private final ASMArg rdx = new ASMRegArg(Register.RDX);
    private final ASMArg rcx = new ASMRegArg(Register.RCX);
    private final ASMArg r8 = new ASMRegArg(Register.R8);
    private final ASMArg r9 = new ASMRegArg(Register.R9);

    public BasicTiler(IdGenerator generator, String tiledFunctionName,
            Map<String, FunctionType> fMap) {
        this.generator = generator;
        this.fMap = Collections.unmodifiableMap(fMap);
        this.numRetValues = this.fMap.get(tiledFunctionName).output.getTypes()
                .size();
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
            insns.add(make.Push(rax));
            insns.add(make.Push(rdx));
            insns.add(make.Mov(rax, left.result.get()));
            insns.add(make.CQO());
            insns.add(make.Div(right.result.get()));
            insns.add(make.Mov(ret, rax));
            insns.add(make.Pop(rdx));
            insns.add(make.Pop(rax));
            break;
        case EQ:
            insns.add(make.Cmp(left.result.get(), right.result.get()));
            insns.add(make.SetZ(ret));
            break;
        case GEQ: {
            ASMArg ret2 = new ASMTempArg(generator.newTemp());
            insns.add(make.Cmp(left.result.get(), right.result.get()));
            insns.add(make.SetLE(ret));
            insns.add(make.Xor(ret, arg.constant(1)));
            insns.add(make.SetZ(ret2));
            insns.add(make.Or(ret, ret2));
        }
            break;
        case GT:
            insns.add(make.Cmp(left.result.get(), right.result.get()));
            insns.add(make.SetLE(ret));
            insns.add(make.Xor(ret, arg.constant(1)));
            break;
        case HMUL:
            insns.add(make.Push(rax));
            insns.add(make.Mul(ret, left.result.get(), right.result.get()));
            insns.add(make.Mov(ret, rax));
            insns.add(make.Pop(rax));
            break;
        case LEQ:
            insns.add(make.Cmp(left.result.get(), right.result.get()));
            insns.add(make.SetLE(ret));
            break;
        case LSHIFT:
            insns.add(make.Mov(ret, left.result.get()));
            insns.add(make.LShift(ret, right.result.get()));
            break;
        case LT: {
            ASMArg ret2 = new ASMTempArg(generator.newTemp());
            insns.add(make.Cmp(left.result.get(), right.result.get()));
            insns.add(make.SetLE(ret));
            insns.add(make.SetNE(ret2));
            insns.add(make.And(ret, ret2));
            break;
        }
        case MOD:
            insns.add(make.Push(rax));
            insns.add(make.Push(rdx));
            insns.add(make.Mov(rax, left.result.get()));
            insns.add(make.CQO());
            insns.add(make.Div(right.result.get()));
            insns.add(make.Mov(ret, rdx));
            insns.add(make.Pop(rdx));
            insns.add(make.Pop(rax));
            break;
        case MUL:
            insns.add(make.Mul(ret, left.result.get(), right.result.get()));
            break;
        case NEQ:
            insns.add(make.Cmp(left.result.get(), right.result.get()));
            insns.add(make.SetNE(ret));
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

        TilerData result = new TilerData(1 + left.tileCost + right.tileCost,
                insns, Optional.of(ret));
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRCall n) {
        throw new UnsupportedOperationException(
                "Call is not a valid node at this stage.");
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
        throw new UnsupportedOperationException(
                "ESeq is not a valid node at this stage.");
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
        ASMAddrExpr address = new ASMAddrExpr(Optional.empty(), ScaleValues.ONE,
                Optional.empty(), 0);
        List.of(make.Mov(ret, new ASMMemArg(address)));

        TilerData result = new TilerData(1 + expr.tileCost, insns,
                Optional.of(ret));
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
        TilerData result = new TilerData(0, new ArrayList<>(),
                Optional.of(ret));
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRCallStmt n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        List<ASMLine> insn = new ArrayList<>();
        TilerData targetTile = n.target().accept(this);
        List<TilerData> argTiles = new ArrayList<>(n.args().stream()
                .map(a -> a.accept(this)).collect(Collectors.toList()));

        int lastRegisterArg;
        int tileCost = 0;
        if (this.numRetValues > 2) {
            lastRegisterArg = 5;
            long size = (this.numRetValues - 2) * 8;
            insn.add(make.Lea(rdi, arg.constant(size)));
            insn.add(make.Sub(rsp, rdi));
            insn.add(make.Mov(rdi, rsp));

            for (int i = 0; i < lastRegisterArg; i++) {
                TilerData argTile = argTiles.get(i);
                tileCost += argTile.tileCost;
                insn.addAll(argTile.optimalInstructions);
                switch (i) {
                case 0:
                    insn.add(make.Mov(rsi, argTile.result.get()));
                    break;
                case 1:
                    insn.add(make.Mov(rdx, argTile.result.get()));
                    break;
                case 2:
                    insn.add(make.Mov(rcx, argTile.result.get()));
                    break;
                case 3:
                    insn.add(make.Mov(r8, argTile.result.get()));
                    break;
                case 4:
                    insn.add(make.Mov(r9, argTile.result.get()));
                    break;
                default:
                    throw new RuntimeException("Unexpected Error with index");
                }
            }
        } else {
            lastRegisterArg = 6;
            for (int i = 0; i < lastRegisterArg; i++) {
                TilerData argTile = argTiles.get(i);
                tileCost += argTile.tileCost;
                insn.addAll(argTile.optimalInstructions);
                switch (i) {
                case 0:
                    insn.add(make.Mov(rdi, argTile.result.get()));
                    break;
                case 1:
                    insn.add(make.Mov(rsi, argTile.result.get()));
                    break;
                case 2:
                    insn.add(make.Mov(rdx, argTile.result.get()));
                    break;
                case 3:
                    insn.add(make.Mov(rcx, argTile.result.get()));
                    break;
                case 4:
                    insn.add(make.Mov(r8, argTile.result.get()));
                    break;
                case 5:
                    insn.add(make.Mov(r9, argTile.result.get()));
                    break;
                default:
                    throw new RuntimeException("Unexpected Error with index");
                }
            }
        }
        for (int i = argTiles.size() - 1; i >= lastRegisterArg; i--) {
            TilerData argTile = argTiles.get(i);
            tileCost += argTile.tileCost;
            insn.addAll(argTile.optimalInstructions);
            insn.add(make.Push(argTile.result.get()));
        }

        tileCost += targetTile.tileCost;
        insn.addAll(targetTile.optimalInstructions);
        insn.add(make.Call(targetTile.result.get()));
        TilerData result = new TilerData(tileCost, insn, Optional.empty());
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRCJump n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        List<ASMLine> insn = new ArrayList<>();
        TilerData cond = n.cond().accept(this);
        ASMArg temp = cond.result.map(r -> r)
                .orElseThrow(() -> new RuntimeException(
                        "Expected a temporary but was not found."));
        insn.addAll(cond.optimalInstructions);
        insn.add(make.Cmp(temp, new ASMConstArg(1)));
        insn.add(make.JumpE(new ASMLabelArg(n.trueLabel())));

        TilerData result = new TilerData(1 + cond.tileCost, insn,
                Optional.empty());
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
        for (Map.Entry<String, IRFuncDecl> functionDecl : n.functions()
                .entrySet()) {
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
        throw new UnsupportedOperationException(
                "Exp is not a valid node at this stage.");
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

        TilerData result = new TilerData(instructions.size(), instructions,
                Optional.empty());
        n.setOptimalTilingOnce(result);
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

        TilerData result = new TilerData(target.tileCost, instructions,
                Optional.empty());
        if (target.result.isPresent()) {
            instructions.add(make.Jump(target.result.get()));
            result = new TilerData(1 + target.tileCost, instructions,
                    Optional.empty());
        } else {
            throw new RuntimeException("Missing target result");
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

        TilerData result = new TilerData(instructions.size(), instructions,
                Optional.empty());
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
        instrs.addAll(source.optimalInstructions);
        instrs.addAll(target.optimalInstructions);

        final String ARG_PREFIX = Configuration.ABSTRACT_ARG_PREFIX;
        final String RET_PREFIX = Configuration.ABSTRACT_RET_PREFIX;
        var rdi = arg.reg(Register.RDI);
        var rsi = arg.reg(Register.RSI);
        var rdx = arg.reg(Register.RDX);
        var rcx = arg.reg(Register.RCX);
        var r8 = arg.reg(Register.R8);
        var r9 = arg.reg(Register.R9);
        var rax = arg.reg(Register.RAX);

        TilerData result;
        if (n.target() instanceof IRTemp
                && ((IRTemp) n.target()).name().startsWith(ARG_PREFIX)) {
            // Handle in CallStmt
            result = new TilerData(0, List.of(), Optional.empty());
        } else if (n.source() instanceof IRTemp
                && ((IRTemp) n.source()).name().startsWith(ARG_PREFIX)) {
            String index = ((IRTemp) n.source()).name()
                    .substring(ARG_PREFIX.length());
            int i = Integer.parseInt(index);
            ASMArg targetTemp = target.result.get();
            if (this.numRetValues > 2) {
                switch (i) {
                case 0:
                    instrs.add(make.Mov(targetTemp, rsi));
                    break;
                case 1:
                    instrs.add(make.Mov(targetTemp, rdx));
                    break;
                case 2:
                    instrs.add(make.Mov(targetTemp, rcx));
                    break;
                case 3:
                    instrs.add(make.Mov(targetTemp, r8));
                    break;
                case 4:
                    instrs.add(make.Mov(targetTemp, r9));
                    break;
                default:
                    int offset = 8 * (i - 6);
                    var addr = arg.addr(Optional.of(Register.RBP),
                            ScaleValues.ONE, Optional.empty(), offset);
                    var mem = arg.mem(addr);
                    instrs.add(make.Mov(targetTemp, mem));
                    break;
                }
            } else {
                switch (i) {
                case 0:
                    instrs.add(make.Mov(targetTemp, rdi));
                    break;
                case 1:
                    instrs.add(make.Mov(targetTemp, rsi));
                    break;
                case 2:
                    instrs.add(make.Mov(targetTemp, rdx));
                    break;
                case 3:
                    instrs.add(make.Mov(targetTemp, rcx));
                    break;
                case 4:
                    instrs.add(make.Mov(targetTemp, r8));
                    break;
                case 5:
                    instrs.add(make.Mov(targetTemp, r9));
                    break;
                default:
                    int offset = 8 * (i - 5);
                    var addr = arg.addr(Optional.of(Register.RBP),
                            ScaleValues.ONE, Optional.empty(), offset);
                    var mem = arg.mem(addr);
                    instrs.add(make.Mov(targetTemp, mem));
                    break;
                }
            }
            result = new TilerData(1 + target.tileCost, instrs,
                    Optional.empty());
        } else if (n.source() instanceof IRTemp
                && ((IRTemp) n.source()).name().startsWith(RET_PREFIX)) {
            String index = ((IRTemp) n.source()).name()
                    .substring(RET_PREFIX.length());
            int i = Integer.parseInt(index);
            switch (i) {
            case 0:
                instrs.add(make.Mov(rax, source.result.get()));
                break;
            case 1:
                instrs.add(make.Mov(rdx, source.result.get()));
                break;
            default:
                int offset = 8 * (i - 1);
                var addr = arg.addr(Optional.of(Register.RDI), ScaleValues.ONE,
                        Optional.empty(), offset);
                var mem = arg.mem(addr);
                instrs.add(make.Mov(mem, source.result.get()));
                break;
            }
            result = new TilerData(1 + source.tileCost, instrs,
                    Optional.empty());
        } else if (n.target() instanceof IRTemp
                && ((IRTemp) n.target()).name().startsWith(RET_PREFIX)) {
            String index = ((IRTemp) n.target()).name()
                    .substring(RET_PREFIX.length());
            int i = Integer.parseInt(index);
            switch (i) {
            case 0:
                instrs.add(make.Mov(target.result.get(), rax));
                break;
            case 1:
                instrs.add(make.Mov(target.result.get(), rdx));
                break;
            default:
                int offset = 8 * (i - 1);
                var addr = arg.addr(Optional.of(Register.RDI), ScaleValues.ONE,
                        Optional.empty(), offset);
                var mem = arg.mem(addr);
                instrs.add(make.Mov(target.result.get(), mem));
                break;
            }
            result = new TilerData(1 + target.tileCost, instrs,
                    Optional.empty());
        } else {
            instrs.add(make.Mov(target.result.get(), source.result.get()));
            result = new TilerData(1 + target.tileCost + source.tileCost,
                    instrs, Optional.empty());
        }
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRReturn n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> instrs = new ArrayList<>();
        instrs.add(make.Jump(new ASMLabelArg(this.returnLbl)));
        TilerData result = new TilerData(instrs.size(), instrs,
                Optional.empty());
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
        TilerData result = new TilerData(1 + sumCosts, instructions,
                Optional.empty());
        n.setOptimalTilingOnce(result);
        return result;
    }
}
