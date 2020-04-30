package cyr7.cfg.dfa;

import cyr7.cfg.visitor.AbstractCFGVisitor;

public abstract class TransferFunction<L> extends AbstractCFGVisitor<L> {
    protected L in;
    void setIn(L in) {
        this.in = in;
    }
}
