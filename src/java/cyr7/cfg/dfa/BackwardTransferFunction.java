package cyr7.cfg.dfa;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGVarAssignNode;

public interface BackwardTransferFunction<L> {

    L transfer(CFGCallNode n, L in);

    L transfer(CFGIfNode n, L in);

    L transfer(CFGMemAssignNode n, L in);

    L transfer(CFGReturnNode n, L in);

    L transfer(CFGVarAssignNode n, L in);

}
