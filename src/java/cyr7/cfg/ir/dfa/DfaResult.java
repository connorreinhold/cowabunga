package cyr7.cfg.ir.dfa;

import java.util.Collections;
import java.util.Map;

import cyr7.cfg.ir.nodes.CFGNode;

public class DfaResult<L> {

    private final Map<CFGNode, L> in;
    private final Map<CFGNode, Map<CFGNode, L>> out;

    public DfaResult(Map<CFGNode, L> in, Map<CFGNode, Map<CFGNode, L>> out) {
        this.in = in;
        this.out = out;
    }

    public Map<CFGNode, L> in() {
        return Collections.unmodifiableMap(in);
    }

    public Map<CFGNode, Map<CFGNode, L>> out() {
        return Collections.unmodifiableMap(out);
    }

}
