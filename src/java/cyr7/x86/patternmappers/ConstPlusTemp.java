package cyr7.x86.patternmappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRTemp;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMRegSize;
import cyr7.x86.pattern.BiPatternBuilder;
import cyr7.x86.tiler.ComplexTiler;
import cyr7.x86.tiler.TilerData;

public class ConstPlusTemp extends MemoryAddrPattern {

    public ConstPlusTemp(boolean isMemPattern) {
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

        var pattern = BiPatternBuilder
            .left()
            .instOf(IRConst.class)
            .and(x -> Is32Bits.check(x.constant()))
            .right()
            .instOf(IRTemp.class)
            .finish()
            .enableCommutes();

        if (pattern.matches(new Object[]{ n.left(), n.right() })) {
            IRConst constArg = pattern.leftObj();
            IRTemp tempArg = pattern.rightObj();

            this.setCost(1 + pattern.preMapRight().getOptimalTiling().tileCost);
            return Optional.of(arg.addr(
                Optional.of(arg.temp(tempArg.name(), ASMRegSize.QWORD)),
                ScaleValues.ONE,
                Optional.empty(),
                constArg.constant()
            ));
        }

        return Optional.empty();
    }

    @Override
    public Optional<TilerData> match(IRBinOp n, ComplexTiler tiler, ASMLineFactory make) {
        List<ASMLine> insns = new ArrayList<>();
        Optional<ASMAddrExpr> addrExprOptional = matchAddress(n, tiler, make, insns);
        if (addrExprOptional.isEmpty()) {
            return Optional.empty();
        }
        ASMAddrExpr addrExpr = addrExprOptional.get();

        if (this.isMemPattern) {
            return Optional.of(
                new TilerData(1,
                    insns,
                    Optional.of(arg.mem(addrExpr))
                ));
        } else {
            ASMTempArg resultTemp = arg.temp(tiler.generator().newTemp(), ASMRegSize.QWORD);
            ASMLine line = make.Lea(
                resultTemp, arg.mem(addrExpr));
            insns.add(line);
            return Optional.of(
                new TilerData(1,
                    insns,
                    Optional.of(resultTemp)
                ));
        }
    }

}
