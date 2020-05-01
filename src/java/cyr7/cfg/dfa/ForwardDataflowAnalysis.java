package cyr7.cfg.dfa;

/**
 * A dataflow analysis.
 *
 * Has a lattice L with a top value, a transfer function in the form of a
 * visitor, and a meet operator.
 * @param <L>
 */
public interface ForwardDataflowAnalysis<L> {

    L topValue();
    ForwardTransferFunction<L> transfer();
    L meet(L lhs, L rhs);

}