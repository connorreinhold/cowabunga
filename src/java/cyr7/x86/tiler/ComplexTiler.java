package cyr7.x86.tiler;

import java.util.Optional;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRMem;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.pattern.MemAddrPatternChecker;

public class ComplexTiler extends BasicTiler {

    public ComplexTiler(IdGenerator generator, int numRetValues,
            String returnLbl, Optional<ASMTempArg> additionalRetValAddress,
            boolean stack16ByteAligned) {
        super(generator, numRetValues, returnLbl, additionalRetValAddress,
                stack16ByteAligned);
    }

    @Override
    public TilerData visit(IRBinOp n) {
        return super.visit(n);
    }

    @Override
    public TilerData visit(IRMem n) {
        Optional<TilerData> tile = n.expr().accept(new MemAddrPatternChecker());
        if (tile.isPresent()) {
            return tile.get();
        } else {
            return super.visit(n);
        }
    }

}
