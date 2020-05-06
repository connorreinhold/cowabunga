package cyr7.cfg.asm.dfa;

import cyr7.cfg.asm.nodes.AsmCFGNode;

public interface BackwardDataflowAnalysis<L> {

    L topValue();
    L transfer(AsmCFGNode n, L out);
    L meet(L lhs, L rhs);

}
