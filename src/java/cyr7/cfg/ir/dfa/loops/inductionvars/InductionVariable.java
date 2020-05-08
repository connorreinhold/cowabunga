package cyr7.cfg.ir.dfa.loops.inductionvars;

public interface InductionVariable {
    String basicRef();
    long getFactor();
    long getOffset();
}