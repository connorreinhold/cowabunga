package cyr7.x86.patternmappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExpr;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.pattern.BiPatternBuilder;
import cyr7.x86.tiler.BinOpInstructionGenerator;
import cyr7.x86.tiler.ComplexTiler;
import cyr7.x86.tiler.TilerData;

public class Binop_TempArgConstPattern {

    private static final ASMArgFactory arg = ASMArgFactory.instance;

    public static Optional<TilerData> match(IRBinOp n, ComplexTiler tiler, IdGenerator generator) {
        var pattern = BiPatternBuilder
                .left()
                .instOf(ASMTempArg.class)
                .right()
                .instOf(IRConst.class)
                .and(x -> Is32Bits.check(x.constant()))
                .finish()
                .mappingLeft(IRExpr.class,
                        (Function<IRExpr, ASMArg>)
                        node -> node.accept(tiler).result.get());

        if (pattern.matches(new Object[] {n.left(), n.right()})) {
            List<ASMLine> insns = new ArrayList<>();
            ASMTempArg temp = pattern.leftObj();
            ASMConstArg constant = arg.constant(pattern.rightObj().constant());
            insns.addAll(pattern.preMapLeft().getOptimalTiling().optimalInstructions);
            final int cost = 1 + pattern.preMapLeft().getOptimalTiling().tileCost;
            return Optional.of(BinOpInstructionGenerator
                                    .generateInstruction(n, cost, temp,
                                            constant, insns, generator));
        }
        return Optional.empty();
    }

}