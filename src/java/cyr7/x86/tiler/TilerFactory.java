package cyr7.x86.tiler;

import cyr7.ir.IdGenerator;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.ASMTempArg;

import java.util.Optional;

public interface TilerFactory {

    static TilerFactory basicTilerFactory() {
        return BasicTiler::new;
    }

    MyIRVisitor<TilerData> constructTiler(
        IdGenerator generator,
        int numRetValues,
        String returnLbl,
        Optional<ASMTempArg> additionalRetValueAddress);

}
