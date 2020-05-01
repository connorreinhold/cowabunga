package cyr7.cfg.dfa;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;

public interface ForwardTransferFunction<L> {

    L transfer(CFGCallNode n, L in);

    L transferTrue(CFGIfNode n, L in);

    L transferFalse(CFGIfNode n, L in);

    L transfer(CFGMemAssignNode n, L in);

    L transfer(CFGStartNode n, L in);

    L transfer(CFGVarAssignNode n, L in);

}
