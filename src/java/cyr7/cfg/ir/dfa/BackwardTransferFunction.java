package cyr7.cfg.ir.dfa;

import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGPhiFunction;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;

public interface BackwardTransferFunction<L> {

    L transfer(CFGCallNode n, L out);

    L transfer(CFGIfNode n, L out);

    L transfer(CFGMemAssignNode n, L out);

    L transfer(CFGReturnNode n, L out);

    L transfer(CFGVarAssignNode n, L out);

    L transfer(CFGSelfLoopNode n, L out);

    L transfer(CFGBlockNode n, L out);

    L transfer(CFGPhiFunction n, L out);
}
