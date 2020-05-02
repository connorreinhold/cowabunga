package cyr7.cfg.dfa;

import cyr7.cfg.nodes.ir.CFGCallNode;
import cyr7.cfg.nodes.ir.CFGIfNode;
import cyr7.cfg.nodes.ir.CFGMemAssignNode;
import cyr7.cfg.nodes.ir.CFGReturnNode;
import cyr7.cfg.nodes.ir.CFGVarAssignNode;

public interface BackwardTransferFunction<L> {

    L transfer(CFGCallNode n, L in);

    L transfer(CFGIfNode n, L in);

    L transfer(CFGMemAssignNode n, L in);

    L transfer(CFGReturnNode n, L in);

    L transfer(CFGVarAssignNode n, L in);

}
