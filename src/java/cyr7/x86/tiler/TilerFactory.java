package cyr7.x86.tiler;

import cyr7.ir.IdGenerator;
import cyr7.semantics.types.FunctionType;
import cyr7.visitor.MyIRVisitor;

import java.util.Map;

public interface TilerFactory {

    static TilerFactory basicTilerFactory() {
        return BasicTiler::new;
    }

    MyIRVisitor<TilerData> constructTiler(
        IdGenerator generator,
        String tiledFunctionName,
        Map<String, FunctionType> fMap,
        String returnLbl);

}
