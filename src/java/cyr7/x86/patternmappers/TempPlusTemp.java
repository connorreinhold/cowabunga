package cyr7.x86.patternmappers;

import cyr7.ir.nodes.IRBinOp;
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
import cyr7.x86.tiler.TilerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TempPlusTemp extends MemoryAddrPattern {

    public TempPlusTemp(boolean isMemPattern) {
        super(isMemPattern);
    }
    
    @Override
    public Optional<TilerData> match(IRBinOp n, ComplexTiler tiler, ASMLineFactory make) {
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

            List<ASMLine> insns = new ArrayList<>();
            insns.addAll(tempPlusTempPattern.preMapLeft().getOptimalTiling().optimalInstructions);
            insns.addAll(tempPlusTempPattern.preMapRight().getOptimalTiling().optimalInstructions);

            ASMAddrExpr addrExpr = arg.addr(
                    Optional.of(arg.temp(lhs.name, Size.QWORD)),
                    ScaleValues.ONE,
                    Optional.of(arg.temp(rhs.name, Size.QWORD)),
                    0
                );
            if (this.isMemPattern) {
                return Optional.of(
                    new TilerData(1,
                        insns,
                        Optional.of(arg.mem(addrExpr))
                    ));
            } else {
                ASMTempArg resultTemp = arg.temp(tiler.generator().newTemp(), Size.QWORD);
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
        return Optional.empty();
    }
}
