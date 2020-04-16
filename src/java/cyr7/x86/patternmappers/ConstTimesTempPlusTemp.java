package cyr7.x86.patternmappers;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExpr;
import cyr7.x86.asm.*;
import cyr7.x86.pattern.BiPatternBuilder;
import cyr7.x86.tiler.ComplexTiler;
import cyr7.x86.tiler.TilerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ConstTimesTempPlusTemp extends MemoryAddrPattern {

    public ConstTimesTempPlusTemp(boolean isMemPattern) {
        super(isMemPattern);
    }

    @Override
    protected Optional<ASMAddrExpr> matchAddress(
        IRBinOp n,
        ComplexTiler tiler,
        ASMLineFactory make,
        List<ASMLine> insns) {
        if (n.opType() != IRBinOp.OpType.ADD) {
            return Optional.empty();
        }

        var constTemp = BiPatternBuilder
                .left()
                .instOf(IRConst.class)
                .and(x -> x.constant() == 1 || x.constant() == 2 || x.constant() == 4 || x.constant() == 8)
                .right()
                .instOf(ASMTempArg.class)
                .finish()
                .mappingRight(IRExpr.class,
                        (Function<IRExpr, ASMArg>)
                                node -> node.accept(tiler).result.get())
                .enableCommutes();

        var constTempPlusTemp = BiPatternBuilder
                .left()
                .instOf(IRBinOp.class)
                .and(x -> x.opType() == IRBinOp.OpType.MUL)
                .and(x -> constTemp.matches(new Object[]{x.left(), x.right()}))
                .right()
                .instOf(ASMTempArg.class)
                .finish()
                .mappingRight(IRExpr.class,
                        (Function<IRExpr, ASMArg>)
                                node -> node.accept(tiler).result.get())
                .enableCommutes();

        if (constTempPlusTemp.matches(new Object[]{n.left(), n.right()})) {
            IRConst constArg = constTemp.leftObj();
            ASMTempArg tempArg1 = constTemp.rightObj();
            ASMTempArg tempArg2 = constTempPlusTemp.rightObj();

            ASMTempArg resultTemp = arg.temp(tiler.generator().newTemp(), ASMTempArg.Size.QWORD);
            insns.addAll(constTemp.preMapRight().getOptimalTiling().optimalInstructions);
            insns.addAll(constTempPlusTemp.preMapRight().getOptimalTiling().optimalInstructions);

            ASMAddrExpr addrExpr = arg.addr(
                Optional.of(arg.temp(tempArg2.name, ASMTempArg.Size.QWORD)),
                ASMAddrExpr.ScaleValues.fromConst(constArg.constant()).get(),
                Optional.of(arg.temp(tempArg1.name, ASMTempArg.Size.QWORD)),
                0
            );
            return Optional.of(addrExpr);
        }

        return Optional.empty();
    }
}
