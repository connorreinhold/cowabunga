package cyr7.x86.patternmappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cyr7.ir.nodes.IRBinOp;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMRegSize;
import cyr7.x86.tiler.ComplexTiler;
import cyr7.x86.tiler.TilerData;

public abstract class MemoryAddrPattern extends PatternMapper<IRBinOp> {

    protected boolean isMemPattern;
    private int cost = 1;

    protected MemoryAddrPattern(boolean isMemPattern) {
        this.isMemPattern = isMemPattern;
    }

    protected abstract Optional<ASMAddrExpr> matchAddress(
        IRBinOp n,
        ComplexTiler tiler,
        ASMLineFactory make,
        List<ASMLine> insns);

    protected void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public Optional<TilerData> match(IRBinOp n, ComplexTiler tiler, ASMLineFactory make) {
        List<ASMLine> insns = new ArrayList<>();

        cost = 1;

        Optional<ASMAddrExpr> addrExprOptional = matchAddress(n, tiler, make, insns);
        if (addrExprOptional.isEmpty()) {
            return Optional.empty();
        }
        ASMAddrExpr addrExpr = addrExprOptional.get();

        if (this.isMemPattern) {
            return Optional.of(
                new TilerData(cost,
                    insns,
                    Optional.of(arg.mem(addrExpr))
                ));
        } else {
            ASMTempArg resultTemp = arg.temp(tiler.generator().newTemp(), ASMRegSize.QWORD);
            ASMLine line = make.Lea(resultTemp, arg.mem(addrExpr));
            insns.add(line);
            return Optional.of(
                new TilerData(cost,
                    insns,
                    Optional.of(resultTemp)
                ));
        }
    }

}
