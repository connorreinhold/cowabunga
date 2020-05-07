package cyr7.cfg.ir.dfa.loops;

public class BasicInductionVariable extends DerivedInductionVariable {
    int loopInc;
    String name;
    
    public BasicInductionVariable(String name, int loopInc){
        super(name, 1, 0);
        this.name = name;
        this.loopInc = loopInc;
    }
}
