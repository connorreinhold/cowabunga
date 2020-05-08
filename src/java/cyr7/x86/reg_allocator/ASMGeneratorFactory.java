package cyr7.x86.reg_allocator;

import cyr7.ir.IdGenerator;
import cyr7.x86.tiler.TilerFactory;

public interface ASMGeneratorFactory {

    ASMGenerator constructGenerator(TilerFactory tilerFactory, IdGenerator generator);

}
