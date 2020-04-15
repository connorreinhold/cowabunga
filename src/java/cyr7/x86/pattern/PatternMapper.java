package cyr7.x86.pattern;

import java.util.Optional;

import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.tiler.ComplexTiler;
import cyr7.x86.tiler.TilerData;

public abstract class PatternMapper<T> {
    protected static final ASMArgFactory arg = ASMArgFactory.instance;
    public abstract Optional<TilerData> match(T n, ComplexTiler tiler);
}
