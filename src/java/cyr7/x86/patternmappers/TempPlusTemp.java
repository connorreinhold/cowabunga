package cyr7.x86.patternmappers;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRExpr;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMRegSize;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.pattern.BiPatternBuilder;
import cyr7.x86.tiler.ComplexTiler;

public class TempPlusTemp extends MemoryAddrPattern {

    public TempPlusTemp(boolean isMemPattern) {
        super(isMemPattern);
    }

    @Override
    protected Optional<ASMAddrExpr> matchAddress(
        IRBinOp n,
        ComplexTiler tiler,
        ASMLineFactory make,
        List<ASMLine> insns) {
        var tempPlusTempPattern = BiPatternBuilder
            .left()
            .instOf(ASMTempArg.class)
            .right()
            .instOf(ASMTempArg.class)
            .finish()
            .mappingLeft(IRExpr.class,
                (Function<IRExpr, ASMArg>)
                    node -> node.accept(tiler).result.get())
            .mappingRight(IRExpr.class,
                (Function<IRExpr, ASMArg>)
                    node -> node.accept(tiler).result.get());

        if (tempPlusTempPattern.matches(new Object[]{n.left(), n.right()})) {
            ASMTempArg lhs = tempPlusTempPattern.leftObj();
            ASMTempArg rhs = tempPlusTempPattern.rightObj();

            insns.addAll(tempPlusTempPattern.preMapLeft().getOptimalTiling().optimalInstructions);
            insns.addAll(tempPlusTempPattern.preMapRight().getOptimalTiling().optimalInstructions);

            this.setCost(1
                    + tempPlusTempPattern.preMapLeft()
                            .getOptimalTiling().tileCost
                    + tempPlusTempPattern.preMapRight()
                            .getOptimalTiling().tileCost);

            ASMAddrExpr addrExpr = arg.addr(
                    Optional.of(arg.temp(lhs.name, ASMRegSize.QWORD)),
                    ScaleValues.ONE,
                    Optional.of(arg.temp(rhs.name, ASMRegSize.QWORD)),
                    0
                );
            return Optional.of(addrExpr);
        }
        return Optional.empty();
    }
}
