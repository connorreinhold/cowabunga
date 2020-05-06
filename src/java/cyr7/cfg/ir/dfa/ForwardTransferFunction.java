package cyr7.cfg.ir.dfa;

import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;

public interface ForwardTransferFunction<L> {

    L transfer(CFGCallNode n, L in);

    L transferTrue(CFGIfNode n, L in);

    L transferFalse(CFGIfNode n, L in);

    L transfer(CFGMemAssignNode n, L in);

    L transfer(CFGStartNode n, L in);

    L transfer(CFGVarAssignNode n, L in);

    L transfer(CFGSelfLoopNode n, L in);

}
