package cyr7.x86.tiler;

import java.util.ArrayList;
import java.util.List;
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
import cyr7.ir.nodes.IRNode_c;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.ASMConstants;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempArg.Size;
import cyr7.x86.asm.ASMTempRegArg;

/**
 * Tiles IRStatement trees using the most rudimentary of patterns, i.e. the
 * children of each IRStatement resolve to some type of assembly argument. For
 * more sophisticated tiling patterns, please see {@link ComplexTiler}.
 */
public class BasicTiler implements MyIRVisitor<TilerData> {

    protected static final ASMArgFactory arg = ASMArgFactory.instance;

    protected final IdGenerator generator;
    private final int numRetValues;
    private final String returnLbl;

    /// This temporary contains the memory address of the space allocated to
    /// store return values beyond the second.
    private final Optional<ASMTempArg> additionalRetValAddress;

    private final boolean stack16ByteAligned;

    private boolean shouldMemoizeResult = false;

    public BasicTiler(
        IdGenerator generator,
        int numRetValues,
        String returnLbl,
        Optional<ASMTempArg> additionalRetValAddress,
        boolean stack16ByteAligned) {

        this.generator = generator;
        this.numRetValues = numRetValues;
        this.returnLbl = returnLbl;
        this.additionalRetValAddress = additionalRetValAddress;
        this.stack16ByteAligned = stack16ByteAligned;
    }

    public IdGenerator generator() {
        return this.generator;
    }

    @Override
    public TilerData visit(IRBinOp n) {
        ASMLineFactory make = new ASMLineFactory(n);
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> insns = new ArrayList<>();
        TilerData left = n.left().accept(this);
        TilerData right = n.right().accept(this);

        insns.addAll(left.optimalInstructions);
        insns.addAll(right.optimalInstructions);

        ASMArg leftArg = left.result.get();
        ASMArg rightArg = right.result.get();
        if (leftArg instanceof ASMMemArg && rightArg instanceof ASMMemArg) {
            // Move LHS to a temp if there are two memory args
            ASMArg leftTemp = arg.temp(generator.newTemp(), Size.QWORD);
            insns.add(make.Mov(leftTemp, leftArg));
            leftArg = leftTemp;
        }
        final int cost = 1 + left.tileCost + right.tileCost;

        return BinOpInstructionGenerator.generateInstruction(n, cost,
                leftArg, rightArg, insns, generator);
    }

    @Override
    public TilerData visit(IRCall n) {
        throw new UnsupportedOperationException(
                "Call is not a valid node at this stage.");
    }

    @Override
    public TilerData visit(IRConst n) {
        ASMLineFactory make = new ASMLineFactory(n);
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        ASMArg ret = arg.temp(generator.newTemp(), Size.QWORD);
        List<ASMLine> insns = List.of(
            make.MovAbs(ret, new ASMConstArg(n.constant())));
        TilerData result = new TilerData(1, insns, Optional.of(ret));
        return setResult(n, result);
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
        TilerData expr = n
            .expr()
            .accept(this);

        List<ASMLine> insns = new ArrayList<>();
        insns.addAll(expr.optimalInstructions);

        // As an invariant created by IR code generation, a memory access
        // is never directly nested inside a memory access, i.e.
        // Mem[Mem[x]] never occurs.
        if (!(expr.result.get() instanceof ASMTempRegArg)) {
            throw new RuntimeException("Something bad happened...");
        } else {
            ASMArg ret = new ASMMemArg((ASMTempRegArg) expr.result.get());
            TilerData result = new TilerData(
                1 + expr.tileCost,
                insns,
                Optional.of(ret));
            return setResult(n, result);
        }
    }

    @Override
    public TilerData visit(IRName n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        Optional<ASMArg> lbl = Optional.of(new ASMLabelArg(n.name()));
        TilerData result = new TilerData(1, List.of(), lbl);
        return setResult(n, result);
    }

    @Override
    public TilerData visit(IRTemp n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        ASMArg ret = new ASMTempArg(n.name(), Size.QWORD);
        TilerData result = new TilerData(
            0,
            new ArrayList<>(),
            Optional.of(ret));
        return setResult(n, result);
    }

    @Override
    public TilerData visit(IRCallStmt n) {
        ASMLineFactory make = new ASMLineFactory(n);
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        int cost = 0;
        List<ASMLine> insn = new ArrayList<>();
        List<ASMArg> arguments = new ArrayList<>();

        List<TilerData> argTiles = n.args()
            .stream()
            .map(a -> a.accept(this)).collect(Collectors.toList());

        for (TilerData tile: argTiles) {
            cost += tile.tileCost;
            arguments.add(tile.result.get());
            insn.addAll(tile.optimalInstructions);
        }

        TilerData targetTile = n
                .target()
                .accept(this);

        cost += targetTile.tileCost;
        insn.addAll(targetTile.optimalInstructions);

        TilerData result = CallInstructionGenerator.generate(n, cost, targetTile.result.get(),
                n.collectors(), arguments, insn, stack16ByteAligned);
        return setResult(n, result);
    }

    @Override
    public TilerData visit(IRCJump n) {
        ASMLineFactory make = new ASMLineFactory(n);
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        List<ASMLine> insn = new ArrayList<>();
        TilerData cond = n.cond()
                          .accept(this);
        ASMArg temp = cond.result.orElseThrow(() ->
            new RuntimeException("Expected a temporary but was not found."));
        insn.addAll(cond.optimalInstructions);
        insn.add(make.Cmp(temp, new ASMConstArg(1)));
        insn.add(make.JumpE(new ASMLabelArg(n.trueLabel())));

        TilerData result = new TilerData(
            1 + cond.tileCost,
            insn,
            Optional.empty());
        return setResult(n, result);
    }

    @Override
    public TilerData visit(IRCompUnit n) {
        throw new UnsupportedOperationException(
                "CompUnit is not translated by the BasicTiler.");
    }

    @Override
    public TilerData visit(IRExp n) {
        throw new UnsupportedOperationException(
                "Exp is not a valid node at this stage.");
    }

    @Override
    public TilerData visit(IRFuncDecl n) {
        throw new UnsupportedOperationException(
                "FuncDecl is not translated by the BasicTiler.");
    }

    @Override
    public TilerData visit(IRJump n) {
        ASMLineFactory make = new ASMLineFactory(n);
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> instructions = new ArrayList<>();
        TilerData target = n.target()
                            .accept(this);
        instructions.addAll(target.optimalInstructions);

        TilerData result;
        if (target.result.isPresent()) {
            instructions.add(make.Jump(target.result.get()));
            result = new TilerData(
                1 + target.tileCost,
                instructions,
                Optional.empty());
        } else {
            throw new RuntimeException("Missing target result");
        }
        return setResult(n, result);
    }

    @Override
    public TilerData visit(IRLabel n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> instructions = List.of(new ASMLabel(n.name()));

        TilerData result = new TilerData(
            instructions.size(),
            instructions,
            Optional.empty());
        return setResult(n, result);
    }

    @Override
    public TilerData visit(IRMove n) {
        ASMLineFactory make = new ASMLineFactory(n);
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

        TilerData result;

        if (n.target() instanceof IRTemp
            && ((IRTemp) n.target()).name().startsWith(ARG_PREFIX)) {

            // Case: Move(ARG_i, t)
            // Handle in CallStmt
            result = new TilerData(0, List.of(), Optional.empty());
            assert false;

        } else if (n.source() instanceof IRTemp
            && ((IRTemp) n.source()).name().startsWith(ARG_PREFIX)) {

            // Case Move(t, ARG_i)
            String index = ((IRTemp) n.source()).name().substring(ARG_PREFIX.length());

            int i = Integer.parseInt(index);
            /*
             * If the number of return values in this callee function is greater
             * than 2, then the caller function must had allocated space in the
             * stack to store the third and onward return values. This address
             * is stored in the rdi register, the first argument. If this the
             * case, then the true first argument of the function would be in
             * rsi, usually used for the second argument.
             */
            ASMArg targetTemp = target.result.get();
            if (this.numRetValues > 2) {
                switch (i) {
                case 0:
                    instrs.add(make.Mov(targetTemp, ASMReg.RSI));
                    break;
                case 1:
                    instrs.add(make.Mov(targetTemp, ASMReg.RDX));
                    break;
                case 2:
                    instrs.add(make.Mov(targetTemp, ASMReg.RCX));
                    break;
                case 3:
                    instrs.add(make.Mov(targetTemp, ASMReg.R8));
                    break;
                case 4:
                    instrs.add(make.Mov(targetTemp, ASMReg.R9));
                    break;
                default:
                    int offset = 8 * (i - 5 + ASMConstants.WORD_OFFSET_TO_ARGS);
                    var addr = arg.addr(Optional.of(ASMReg.RBP),
                            ScaleValues.ONE,
                            Optional.empty(),
                            offset);
                    var mem = arg.mem(addr);
                    instrs.add(make.Mov(targetTemp, mem));
                    break;
                }
            } else {
                switch (i) {
                case 0:
                    instrs.add(make.Mov(targetTemp, ASMReg.RDI));
                    break;
                case 1:
                    instrs.add(make.Mov(targetTemp, ASMReg.RSI));
                    break;
                case 2:
                    instrs.add(make.Mov(targetTemp, ASMReg.RDX));
                    break;
                case 3:
                    instrs.add(make.Mov(targetTemp, ASMReg.RCX));
                    break;
                case 4:
                    instrs.add(make.Mov(targetTemp, ASMReg.R8));
                    break;
                case 5:
                    instrs.add(make.Mov(targetTemp, ASMReg.R9));
                    break;
                default:
                    int offset = 8 * (i - 6 + ASMConstants.WORD_OFFSET_TO_ARGS);
                    var addr = arg.addr(Optional.of(ASMReg.RBP),
                            ScaleValues.ONE,
                            Optional.empty(),
                            offset);
                    var mem = arg.mem(addr);
                    instrs.add(make.Mov(targetTemp, mem));
                    break;
                }
            }
            result = new TilerData(
                1 + target.tileCost,
                instrs,
                Optional.empty());

        } else if (n.target() instanceof IRTemp
            && ((IRTemp) n.target()).name().startsWith(RET_PREFIX)) {

            // Case Move(RET_i, t)
            String index =
                ((IRTemp) n.target()).name().substring(RET_PREFIX.length());

            int i = Integer.parseInt(index);
            switch (i) {
            case 0:
                instrs.add(make.Mov(ASMReg.RAX, source.result.get()));
                break;
            case 1:
                instrs.add(make.Mov(ASMReg.RDX, source.result.get()));
                break;
            default:
                int offset = 8 * (i - 2);
                var addr = arg.addr(Optional.of(additionalRetValAddress.get()),
                        ScaleValues.ONE,
                        Optional.empty(),
                        offset);
                var mem = arg.mem(addr);
                instrs.add(make.Mov(mem, source.result.get()));
                break;
            }
            result = new TilerData(
                1 + source.tileCost,
                instrs,
                Optional.empty());

        } else if (n.source() instanceof IRTemp
            && ((IRTemp) n.source()).name().startsWith(RET_PREFIX)) {

            // Case Move(t, RET_i)
            // Handle in CallStmt
            result = new TilerData(0, List.of(), Optional.empty());
            assert false;

        } else if (target.result.get() instanceof ASMMemArg
            && source.result.get() instanceof ASMMemArg) {

            ASMTempArg sourceTemp = arg.temp(generator.newTemp(), Size.QWORD);
            instrs.add(make.Mov(sourceTemp, source.result.get()));
            instrs.add(make.Mov(target.result.get(), sourceTemp));
            result = new TilerData(
                1 + target.tileCost + source.tileCost,
                instrs,
                Optional.empty());

        } else {
            instrs.add(make.Mov(target.result.get(), source.result.get()));
            result = new TilerData(
                1 + target.tileCost + source.tileCost,
                instrs,
                Optional.empty());
        }

        return setResult(n, result);
    }

    @Override
    public TilerData visit(IRReturn n) {
        ASMLineFactory make = new ASMLineFactory(n);
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMLine> instrs = new ArrayList<>();
        instrs.add(make.Jump(new ASMLabelArg(this.returnLbl)));
        TilerData result = new TilerData(
            instrs.size(),
            instrs,
            Optional.empty());
        return setResult(n, result);
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
        TilerData result = new TilerData(
            1 + sumCosts,
            instructions,
            Optional.empty());
        return setResult(n, result);
    }

    protected final void disableBasicTilerMemoizeResults() {
        shouldMemoizeResult = false;
    }

    private TilerData setResult(IRNode_c n, TilerData tilerData) {
        if (shouldMemoizeResult) {
            n.setOptimalTilingOnce(tilerData);
        }
        return tilerData;
    }

}