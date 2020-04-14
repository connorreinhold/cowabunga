package cyr7.x86.tiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRTemp;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempArg.Size;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
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
        /*
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        switch (n.opType()) {
            case MUL:
                List<ASMLine> insns = new ArrayList<>();
                ASMLineFactory make = new ASMLineFactory(n);

                var pattern = BiPatternBuilder
                    .left()
                    .instOf(IRConst.class)
                    .and(x -> x.value() == 1 || x.value() == 2 || x.value() == 4 || x.value() == 8)
                    .right()
                    .instOf(IRTemp.class)
                    .finish()
                    .enableCommutes();

                if (pattern.matchesOpts(Optional.of(n.left()), Optional.of(n.right()))) {
                    ASMTempArg tArg = arg.temp(pattern.rightObj().name(), Size.QWORD);
                    ScaleValues scale = ScaleValues.toScaleValue(String.valueOf(pattern.leftObj().value()));
                    ASMArg source = arg.mem(arg.addr(Optional.empty(), scale, Optional.of(tArg), 0));
                    ASMArg res = arg.temp(generator.newTemp(), Size.QWORD);
                    insns.add(make.Lea(res, source));
                    //return new TilerData(1, insns, Optional.of(res));
                }
        }
*/
        return super.visit(n);
    }

}
