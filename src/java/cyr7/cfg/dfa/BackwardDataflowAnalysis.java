package cyr7.cfg.dfa;

public interface BackwardDataflowAnalysis<L> {

    L topValue();
    BackwardTransferFunction<L> transfer();
    L meet(L lhs, L rhs);

}
