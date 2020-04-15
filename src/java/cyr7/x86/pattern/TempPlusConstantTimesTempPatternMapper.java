package cyr7.x86.pattern;

import java.util.Optional;
import java.util.function.Function;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRExpr;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempRegArg;
import cyr7.x86.tiler.ComplexTiler;
import cyr7.x86.tiler.TilerData;

public class TempPlusConstantTimesTempPatternMapper extends PatternMapper<IRBinOp> {
    BiPatternBuilder pattern = BiPatternBuilder
            .left()
            .instOf(ASMTempArg.class)
            .right()
            .instOf(IRBinOp.class)
            .and(x -> pattern.matches(new Object[] {x.left(), x.right()}))
            .finish()
            .mappingLeft(IRExpr.class, ComplexTiler.class,
                    (BiFunction<IRExpr, ComplexTiler, ASMArg>)
                        (node, tiler) -> node.accept(tiler).result.get())
            .enableCommutes();
    public Optional<TilerData> match(IRBinOp n, ComplexTiler tiler) {
        if (n.opType() == OpType.ADD) {
            return Optional.empty();
        }
        
        
        
        if (pattern.matches(new Object[] {n.left(), n.right()})) {
            ASMArg resultTemp arg.temp(generator.newTemp(), Size.QWORD);
            if (tempPlusTempPattern.matches(new Object[] {n.left(), n.right()})) {
                ASMTempArg lhs = tempPlusTempPattern.leftObj();
                ASMTempArg rhs = tempPlusTempPattern.rightObj();

                List<ASMLine> insns = new ArrayList<>();
                insns.addAll(tempPlusTempPattern.preMapLeft().getOptimalTiling().optimalInstructions);
                insns.addAll(tempPlusTempPattern.preMapRight().getOptimalTiling().optimalInstructions);
        }
        return Optional.empty();
        // Checks LHS is temp and for RHS run consantTimesTemp()
        // Checks for LHS run constantTimesTemp() and RHS is temp
    }

}
}