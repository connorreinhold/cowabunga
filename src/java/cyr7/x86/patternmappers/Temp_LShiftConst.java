package cyr7.x86.patternmappers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExpr;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.pattern.BiPatternBuilder;
import cyr7.x86.tiler.ComplexTiler;
import cyr7.x86.tiler.TilerData;

public class Temp_LShiftConst extends PatternMapper<IRBinOp> {

    @Override
    public Optional<TilerData> match(IRBinOp n, ComplexTiler tiler,
            ASMLineFactory make) {
        var insns = new ArrayList<ASMLine>();

        if (n.opType() != OpType.LSHIFT)
            return Optional.empty();

        var tempLShiftConst = BiPatternBuilder
                .left()
                .instOf(ASMTempArg.class)
                .right()
                .instOf(IRConst.class)
                .finish()
                .mappingLeft(IRExpr.class,
                        (Function<IRExpr, ASMArg>) node -> node
                                .accept(tiler).result.get());

        if (tempLShiftConst.matches(new Object[] { n.left(), n.right() })) {
            ASMTempArg lhs = tempLShiftConst.leftObj();
            IRConst rhs = tempLShiftConst.rightObj();

            insns.addAll(tempLShiftConst.preMapLeft()
                    .getOptimalTiling().optimalInstructions);
            int cost = 1
                    + tempLShiftConst.preMapLeft().getOptimalTiling().tileCost;
            insns.add(make.LShift(lhs, new ASMConstArg(rhs.constant())));
            TilerData result = new TilerData(cost, insns, Optional.of(lhs));
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

}