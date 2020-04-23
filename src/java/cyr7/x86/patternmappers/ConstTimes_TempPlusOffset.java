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
import cyr7.x86.asm.ASMReg.Size;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.pattern.BiPatternBuilder;
import cyr7.x86.tiler.ComplexTiler;

// This matches c*(t+n)
public class ConstTimes_TempPlusOffset extends MemoryAddrPattern {

    public ConstTimes_TempPlusOffset(boolean isMemPattern) {
        super(isMemPattern);
    }

    @Override
    protected Optional<ASMAddrExpr> matchAddress(
        IRBinOp n,
        ComplexTiler tiler,
        ASMLineFactory make,
        List<ASMLine> insns) {
        if (n.opType() != OpType.MUL) {
            return Optional.empty();
        }

        var tempPlusOffset = BiPatternBuilder
            .left()
                .instOf(ASMTempArg.class)
                .right()
                .instOf(IRConst.class)
                .finish()
                .mappingLeft(IRExpr.class,
                    (Function<IRExpr, ASMArg>)
                        node -> node.accept(tiler).result.get())
                .enableCommutes();

        var constTempOffset = BiPatternBuilder
            .left()
            .instOf(IRBinOp.class)
            .and(x -> x.opType() == OpType.ADD)
            .and(x -> tempPlusOffset.matches(new Object[] { x.left(), x.right() }))
            .right()
            .instOf(IRConst.class)
            .and(x -> x.constant() == 1 || x.constant() == 2 || x.constant() == 4 || x.constant() == 8)
            .finish()
            .enableCommutes();

        if (constTempOffset.matches(new Object[]{ n.left(), n.right() })) {
            ASMTempArg tempArg = tempPlusOffset.leftObj();
            IRConst offset = tempPlusOffset.rightObj();
            IRConst cArg = constTempOffset.rightObj();

            insns.addAll(tempPlusOffset.preMapLeft().getOptimalTiling().optimalInstructions);

            this.setCost(1
                    + tempPlusOffset.preMapLeft().getOptimalTiling().tileCost);
            ASMAddrExpr addrExpr = arg.addr(
                    Optional.empty(),
                    ScaleValues.fromConst(cArg.constant()).get(),
                    Optional.of(arg.temp(tempArg.name, Size.QWORD)),
                    offset.constant()*cArg.constant()
                );
            return Optional.of(addrExpr);
        }

        return Optional.empty();
    }
}
