package cyr7.x86.tiler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import cyr7.ir.IdGenerator;
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
import cyr7.ir.nodes.IRNode_c;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.pattern.BiPatternBuilder;
import cyr7.x86.patternmappers.ConstPlusTemp;
import cyr7.x86.patternmappers.ConstTimesTemp;
import cyr7.x86.patternmappers.ConstTimesTemp_MinusOffset;
import cyr7.x86.patternmappers.ConstTimesTemp_PlusOffset;
import cyr7.x86.patternmappers.ConstTimesTemp_PlusTemp;
import cyr7.x86.patternmappers.ConstTimesTemp_PlusTemp_PlusOffset;
import cyr7.x86.patternmappers.ConstTimes_TempMinusOffset;
import cyr7.x86.patternmappers.ConstTimes_TempPlusOffset;
import cyr7.x86.patternmappers.Const_PlusConstTimesTemp_PlusTemp;
import cyr7.x86.patternmappers.TempMinusConst;
import cyr7.x86.patternmappers.TempPlusTemp;
import cyr7.x86.patternmappers.Temp_LShiftConst;
import cyr7.x86.patternmappers.Temp_PlusConstTimesTemp_PlusOffset;

public class ComplexTiler extends BasicTiler {


    private final Comparator<TilerData> byCost = (lhs, rhs) ->
            lhs.tileCost == rhs.tileCost
            ? lhs.optimalInstructions.size() - rhs.optimalInstructions.size()
            : lhs.tileCost - rhs.tileCost;


    private TilerData setBestTile(IRNode_c n, List<TilerData> tilings) {
        TilerData optimal = tilings.stream().min(byCost).get();
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    private TilerData setBestTile(IRNode_c n, TilerData tile) {
        n.setOptimalTilingOnce(tile);
        return tile;
    }

    public ComplexTiler(IdGenerator generator, int numRetValues,
            String returnLbl, Optional<ASMTempArg> additionalRetValAddress,
            boolean stack16ByteAligned) {
        super(generator, numRetValues, returnLbl, additionalRetValAddress,
                stack16ByteAligned);

        disableBasicTilerMemoizeResults();
    }


    @Override
    public TilerData visit(IRBinOp n) {
        ASMLineFactory make = new ASMLineFactory(n);
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        ArrayList<TilerData> possibleTilings = new ArrayList<>();

        switch (n.opType()) {
            case MUL:
                new ConstTimesTemp(false).match(n, this, make).ifPresent(possibleTilings::add);
                new ConstTimes_TempPlusOffset(false).match(n, this, make).ifPresent(possibleTilings::add);
                new ConstTimes_TempMinusOffset(false).match(n, this, make).ifPresent(possibleTilings::add);
                break;
            case ADD:
                new ConstTimesTemp_PlusTemp(false).match(n, this, make).ifPresent(possibleTilings::add);
                new ConstTimesTemp_PlusOffset(false).match(n, this, make).ifPresent(possibleTilings::add);
                new TempPlusTemp(false).match(n, this, make).ifPresent(possibleTilings::add);
                new ConstPlusTemp(false).match(n, this, make).ifPresent(possibleTilings::add);
                new Temp_PlusConstTimesTemp_PlusOffset(false).match(n, this, make).ifPresent(possibleTilings::add);
                new ConstTimesTemp_PlusTemp_PlusOffset(false).match(n, this, make).ifPresent(possibleTilings::add);
                new Const_PlusConstTimesTemp_PlusTemp(false).match(n, this, make).ifPresent(possibleTilings::add);
                break;
            case SUB:
                new ConstTimesTemp_MinusOffset(false).match(n, this, make).ifPresent(possibleTilings::add);
                new TempMinusConst(false).match(n, this, make).ifPresent(possibleTilings::add);
                break;
            case LSHIFT:
                new Temp_LShiftConst(generator, false).match(n, this, make)
                        .ifPresent(possibleTilings::add);
                break;
            default: {
                break;
            }
        }

        var pattern = BiPatternBuilder
                .left()
                .instOf(ASMTempArg.class)
                .right()
                .instOf(IRConst.class)
                .finish()
                .mappingLeft(IRExpr.class,
                        (Function<IRExpr, ASMArg>)
                        node -> node.accept(this).result.get());

        if (pattern.matches(new Object[] {n.left(), n.right()})) {
            List<ASMLine> insns = new ArrayList<>();
            ASMTempArg temp = pattern.leftObj();
            ASMConstArg constant = arg.constant(pattern.rightObj().constant());
            insns.addAll(pattern.preMapLeft().getOptimalTiling().optimalInstructions);
            final int cost = 1 + pattern.preMapLeft().getOptimalTiling().tileCost;
            possibleTilings.add(BinOpInstructionGenerator
                                    .generateInstruction(n, cost, temp,
                                            constant, insns, generator));
        }

        var pattern2 = BiPatternBuilder
                .left()
                .instOf(IRConst.class)
                .right()
                .instOf(ASMTempArg.class)
                .finish()
                .mappingRight(IRExpr.class, (Function<IRExpr, ASMArg>)
                        node -> node.accept(this).result.get());

        if (pattern2.matches(new Object[] {n.left(), n.right()})) {
            List<ASMLine> insns = new ArrayList<>();
            ASMConstArg constant = arg.constant(pattern2.leftObj().constant());
            ASMTempArg temp = pattern2.rightObj();

            insns.addAll(pattern2.preMapRight().getOptimalTiling().optimalInstructions);
            final int cost = 1 + pattern2.preMapRight().getOptimalTiling().tileCost;
            possibleTilings.add(BinOpInstructionGenerator
                                    .generateInstruction(n, cost, constant,
                                            temp, insns, generator));
        }

        possibleTilings.add(super.visit(n));
        return this.setBestTile(n, possibleTilings);
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
        return this.setBestTile(n, super.visit(n));
    }

    @Override
    public TilerData visit(IRESeq n) {
        throw new UnsupportedOperationException(
                "ESeq is not a valid node at this stage.");
    }

    @Override
    public TilerData visit(IRMem n) {
        ASMLineFactory make = new ASMLineFactory(n);
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        ArrayList<TilerData> possibleTilings = new ArrayList<>();
        if (n.expr() instanceof IRBinOp) {
            IRBinOp exprBinOp = (IRBinOp) n.expr();
            switch (exprBinOp.opType()) {
                case MUL:
                    new ConstTimesTemp(true).match(exprBinOp, this, make).ifPresent(possibleTilings::add);
                    new ConstTimes_TempPlusOffset(true).match(exprBinOp, this,
                        make).ifPresent(possibleTilings::add);
                    new ConstTimes_TempMinusOffset(true).match(exprBinOp, this, make).ifPresent(possibleTilings::add);
                    break;
                case ADD:
                    new ConstTimesTemp_PlusTemp(true).match(exprBinOp, this, make).ifPresent(possibleTilings::add);
                    new ConstTimesTemp_PlusOffset(true).match(exprBinOp, this,
                        make).ifPresent(possibleTilings::add);
                    new TempPlusTemp(true).match(exprBinOp, this, make).ifPresent(possibleTilings::add);
                    new ConstPlusTemp(true).match(exprBinOp, this, make).ifPresent(possibleTilings::add);
                    new Temp_PlusConstTimesTemp_PlusOffset(true).match(exprBinOp, this, make).ifPresent(possibleTilings::add);
                    new ConstTimesTemp_PlusTemp_PlusOffset(true).match(exprBinOp, this, make).ifPresent(possibleTilings::add);
                    new Const_PlusConstTimesTemp_PlusTemp(false).match(exprBinOp, this, make).ifPresent(possibleTilings::add);
                    break;
                case SUB:
                    new ConstTimesTemp_MinusOffset(true).match(exprBinOp, this,
                        make).ifPresent(possibleTilings::add);
                    new TempMinusConst(true).match(exprBinOp, this, make).ifPresent(possibleTilings::add);
                    break;
                case LSHIFT:
                    new Temp_LShiftConst(generator, true).match(exprBinOp, this,
                            make).ifPresent(possibleTilings::add);
                    break;
                default:
                    break;
            }
        }

        possibleTilings.add(super.visit(n));
        return this.setBestTile(n, possibleTilings);
    }

    @Override
    public TilerData visit(IRName n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return this.setBestTile(n, super.visit(n));
    }

    @Override
    public TilerData visit(IRTemp n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return this.setBestTile(n, super.visit(n));
    }

    @Override
    public TilerData visit(IRCallStmt n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        List<TilerData> possibleTilings = new ArrayList<>();


        List<ASMArg> arguments = new ArrayList<>();
        List<ASMLine> instructions = new ArrayList<>();
        int cost = 1;
        for (IRExpr a: n.args()) {
            TilerData argTile = a.accept(this);
            cost += argTile.tileCost;
            if (a instanceof IRConst) {
                arguments.add(arg.constant(a.constant()));
            } else {
                arguments.add(argTile.result.get());
                instructions.addAll(argTile.optimalInstructions);
            }
        }
        TilerData targetTile = n.target().accept(this);
        cost += targetTile.tileCost;
        instructions.addAll(targetTile.optimalInstructions);
        possibleTilings.add(CallInstructionGenerator.generate(n, cost,
                targetTile.result.get(), n.collectors(),
                arguments, instructions, this.stack16ByteAligned));


        possibleTilings.add(super.visit(n));

        return this.setBestTile(n, possibleTilings);
    }

    @Override
    public TilerData visit(IRCJump n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return this.setBestTile(n, super.visit(n));
    }

    @Override
    public TilerData visit(IRCompUnit n) {
        throw new UnsupportedOperationException(
                "CompUnit cannot be tilied by the ComplexTiler.");
    }

    @Override
    public TilerData visit(IRExp n) {
        throw new UnsupportedOperationException(
                "Exp is not a valid node at the tiling stage.");
    }

    @Override
    public TilerData visit(IRFuncDecl n) {
        throw new UnsupportedOperationException(
                "FuncDecl cannot be tiled by the ComplexTiler.");
    }

    @Override
    public TilerData visit(IRJump n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return this.setBestTile(n, super.visit(n));
    }

    @Override
    public TilerData visit(IRLabel n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return this.setBestTile(n, super.visit(n));
    }

    @Override
    public TilerData visit(IRMove n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        List<TilerData> possibleTilings = new ArrayList<>();


        List<ASMLine> instructions = new ArrayList<>();
        TilerData target = n.target().accept(this);
        ASMArg targetArg = target.result.get();
        TilerData source = n.source().accept(this);
        ASMArg sourceArg;
        if (n.source() instanceof IRConst) {
            sourceArg = arg.constant(n.source().constant());
        } else {
            sourceArg = source.result.get();
        }
        instructions.addAll(source.optimalInstructions);
        instructions.addAll(target.optimalInstructions);
        final int cost = 1 + target.tileCost + source.tileCost;
        possibleTilings.add(MoveInstructionGenerator.generate(n, cost,
                targetArg, sourceArg,
                numRetValues, generator, additionalRetValAddress, instructions));

        possibleTilings.add(super.visit(n));

        return this.setBestTile(n, possibleTilings);
    }

    @Override
    public TilerData visit(IRReturn n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return this.setBestTile(n, super.visit(n));
    }

    @Override
    public TilerData visit(IRSeq n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return this.setBestTile(n, super.visit(n));
    }

}
