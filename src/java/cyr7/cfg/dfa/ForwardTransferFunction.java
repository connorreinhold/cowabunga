package cyr7.cfg.dfa;

import cyr7.cfg.nodes.ir.CFGCallNode;
import cyr7.cfg.nodes.ir.CFGIfNode;
import cyr7.cfg.nodes.ir.CFGMemAssignNode;
import cyr7.cfg.nodes.ir.CFGStartNode;
import cyr7.cfg.nodes.ir.CFGVarAssignNode;

public interface ForwardTransferFunction<L> {

    L transfer(CFGCallNode n, L in);

    L transferTrue(CFGIfNode n, L in);

    L transferFalse(CFGIfNode n, L in);

    L transfer(CFGMemAssignNode n, L in);

    L transfer(CFGStartNode n, L in);

    L transfer(CFGVarAssignNode n, L in);

}
