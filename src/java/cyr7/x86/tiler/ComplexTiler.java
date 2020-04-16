package cyr7.x86.tiler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import cyr7.ir.IdGenerator;
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
import cyr7.ir.nodes.IRTemp;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.patternmappers.*;

public class ComplexTiler extends BasicTiler {

    private static final Comparator<TilerData> byCost
        = (lhs, rhs) ->
        lhs.tileCost == rhs.tileCost
            ? lhs.optimalInstructions.size() - rhs.optimalInstructions.size()
            : lhs.tileCost - rhs.tileCost;

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

        List<TilerData> possibleTilings = new ArrayList<>();

        switch (n.opType()) {
            case MUL:
                new ConstTimesTemp(false).match(n, this, make).ifPresent(possibleTilings::add);
                new ConstTimes_TempPlusOffset_(false).match(n, this, make).ifPresent(possibleTilings::add);
                break;
            case ADD:
                new ConstTimesTemp_PlusTemp(false).match(n, this, make).ifPresent(possibleTilings::add);
                new _ConstTimesTemp_PlusOffset(false).match(n, this, make).ifPresent(possibleTilings::add);
                new TempPlusTemp(false).match(n, this, make).ifPresent(possibleTilings::add);
                new ConstPlusTemp(false).match(n, this, make).ifPresent(possibleTilings::add);
                break;
            case SUB:
                new ConstTimesTemp_MinusOffset(false).match(n, this, make).ifPresent(possibleTilings::add);
                new TempMinusConst(false).match(n, this, make).ifPresent(possibleTilings::add);
                break;
        }
        
        possibleTilings.add(super.visit(n));
        TilerData optimal = possibleTilings.stream().min(byCost).get();
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRCall n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRConst n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRESeq n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRMem n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRName n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRTemp n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRCallStmt n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRCJump n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRCompUnit n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRExp n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRFuncDecl n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRJump n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRLabel n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRMove n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRReturn n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRSeq n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

}
