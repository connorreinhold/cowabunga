package cyr7.x86.tiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRBinOp;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.pattern.BiPatternBuilder;

public class ComplexTiler extends BasicTiler {

    public ComplexTiler(IdGenerator generator, int numRetValues,
                        String returnLbl,
                        Optional<ASMTempArg> additionalRetValAddress,
                        boolean stack16ByteAligned) {
        super(generator, numRetValues, returnLbl, additionalRetValAddress,
            stack16ByteAligned);
    }

    @Override
    public TilerData visit(IRBinOp n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        switch (n.opType()) {
            case MUL:
                List<ASMLine> insns = new ArrayList<>();
                TilerData left = n.left().accept(this);
                TilerData right = n.right().accept(this);

                insns.addAll(left.optimalInstructions);
                insns.addAll(right.optimalInstructions);

                var pattern = BiPatternBuilder
                    .left()
                    .instOf(ASMConstArg.class)
                    .and(x -> x.constant == 1 || x.constant == 2 || x.constant == 4 || x.constant == 8)
                    .right()
                    .instOf(ASMTempArg.class)
                    .finish()
                    .enableCommutes();

                if (pattern.matchesOpts(left.result, right.result)) {

                }

        }

        return super.visit(n);
    }

}
