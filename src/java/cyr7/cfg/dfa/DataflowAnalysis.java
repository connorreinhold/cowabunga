package cyr7.cfg.dfa;

import cyr7.cfg.nodes.CFGNode;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * A forward dataflow analysis.
 *
 * Has a lattice L with a top value, a transfer function in the form of a
 * visitor, and a meet operator.
 * @param <L>
 */
public class DataflowAnalysis<L> {

    public final L topValue;
    public final BiFunction<CFGNode, L, L> transfer;
    public final BinaryOperator<L> meet;

    public DataflowAnalysis(
        L topValue,
        BiFunction<CFGNode, L, L> transfer,
        BinaryOperator<L> meet) {

        this.topValue = topValue;
        this.transfer = transfer;
        this.meet = meet;
    }

    public DataflowAnalysis(
        L topValue,
        TransferFunction<L> transfer,
        BinaryOperator<L> meet) {

        this.topValue = topValue;
        this.transfer = (n, l) -> { transfer.setIn(l); return n.accept(transfer); };
        this.meet = meet;
    }

}