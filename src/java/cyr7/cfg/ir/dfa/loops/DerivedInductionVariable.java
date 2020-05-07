package cyr7.cfg.ir.dfa.loops;

public class DerivedInductionVariable {
    String basicRef;
    int offset;
    int factor;
    
    public DerivedInductionVariable(String basicRef, int offset, int factor) {
        this.basicRef = basicRef;
        this.offset = offset;
        this.factor = factor;
    }
}
