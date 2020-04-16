package cyr7.x86.patternmappers;

import cyr7.ir.nodes.IRBinOp;

public abstract class MemoryAddrPattern extends PatternMapper<IRBinOp>{
    protected boolean isMemPattern;
    
    protected MemoryAddrPattern(boolean isMemPattern) {
        this.isMemPattern = isMemPattern;
    }
    
}
