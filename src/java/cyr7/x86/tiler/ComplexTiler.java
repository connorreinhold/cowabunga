package cyr7.x86.tiler;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.*;
import cyr7.semantics.types.FunctionType;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ComplexTiler extends BasicTiler {

    public ComplexTiler(IdGenerator generator, int numRetValues,
            String returnLbl, Optional<ASMTempArg> additionalRetValAddress,
            boolean stack16ByteAligned) {
        super(generator, numRetValues, returnLbl, additionalRetValAddress,
                stack16ByteAligned);
    }

    public TilerData visit(IRBinOp n) {
        return super.visit(n);
    }
    
}
