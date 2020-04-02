package cyr7.x86.tiler;

import cyr7.ir.IdGenerator;
import cyr7.visitor.MyIRVisitor;

public interface TilerFactory {

    static TilerFactory basicTilerFactory() {
        return BasicTiler::new;
    }

    MyIRVisitor<TilerData> constructTiler(
        IdGenerator generator,
        String tiledFunctionName,
            int numRetValues,
        String returnLbl);

}
