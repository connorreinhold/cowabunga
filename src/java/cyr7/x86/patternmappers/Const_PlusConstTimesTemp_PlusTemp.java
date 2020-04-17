package cyr7.x86.patternmappers;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExpr;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempArg.Size;
import cyr7.x86.pattern.BiPatternBuilder;
import cyr7.x86.tiler.ComplexTiler;

// This matches c*t + n
public class Const_PlusConstTimesTemp_PlusTemp extends MemoryAddrPattern {

    public Const_PlusConstTimesTemp_PlusTemp(boolean isMemPattern) {
        super(isMemPattern);
    }

    @Override
    protected Optional<ASMAddrExpr> matchAddress(
        IRBinOp n,
        ComplexTiler tiler,
        ASMLineFactory make,
        List<ASMLine> insns) {
        if (n.opType() != OpType.ADD) {
            return Optional.empty();
        }

        var constTimesTemp = BiPatternBuilder
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

        var scaleTimesTempPlusTemp = BiPatternBuilder
            .left()
            .instOf(IRBinOp.class)
            .and(x -> x.opType() == OpType.MUL)
            .and(x -> constTimesTemp.matches(new Object[] { x.left(), x.right() }))
            .right()
            .instOf(ASMTempArg.class)
            .finish()
            .mappingRight(IRExpr.class, 
            (Function<IRExpr, ASMArg>)
                node -> node.accept(tiler).result.get())
            .enableCommutes();

        var constPlusRest = BiPatternBuilder
            .left()
            .instOf(IRConst.class)
            .right()
            .instOf(IRBinOp.class)
            .and(x -> x.opType() == OpType.ADD)
            .and(x -> scaleTimesTempPlusTemp.matches(new Object[] {x.left(), x.right()}))
            .finish()
            .enableCommutes();

        if (constPlusRest.matches(new Object[]{ n.left(), n.right() })) {
            ASMTempArg baseArg = scaleTimesTempPlusTemp.rightObj();
            IRConst scaleArg = constTimesTemp.leftObj();
            ASMTempArg indexArg = constTimesTemp.rightObj();
            IRConst offsetArg = constPlusRest.leftObj();

            insns.addAll(scaleTimesTempPlusTemp.preMapRight().getOptimalTiling().optimalInstructions);
            insns.addAll(constTimesTemp.preMapRight().getOptimalTiling().optimalInstructions);

            this.setCost(1 + scaleTimesTempPlusTemp.preMapRight().getOptimalTiling().tileCost
                           + constTimesTemp.preMapRight().getOptimalTiling().tileCost);

            ASMAddrExpr addrExpr = arg.addr(
                    Optional.of(arg.temp(baseArg.name, Size.QWORD)),
                    ScaleValues.fromConst(scaleArg.constant()).get(),
                    Optional.of(arg.temp(indexArg.name, Size.QWORD)),
                    offsetArg.constant()
                );
            return Optional.of(addrExpr);
        }
        return Optional.empty();
    }
}
