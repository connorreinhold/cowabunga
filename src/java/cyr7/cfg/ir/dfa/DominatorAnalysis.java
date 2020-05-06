package cyr7.cfg.ir.dfa;

import java.util.Collections;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.util.Sets;

public enum DominatorAnalysis implements ForwardDataflowAnalysis<Set<CFGNode>> {
    
    INSTANCE;

    @Override
    public Set<CFGNode> topValue() {
        return Collections.emptySet();
    }

    @Override
    public ForwardTransferFunction<Set<CFGNode>> transfer() {
        return TransferFunction.INSTANCE;
    }

    @Override
    public Set<CFGNode> meet(Set<CFGNode> lhs, Set<CFGNode> rhs) {
        return Sets.intersection(lhs, rhs);
    }
    
    private enum TransferFunction implements ForwardTransferFunction<Set<CFGNode>> {
        INSTANCE;

        @Override
        public Set<CFGNode> transfer(CFGCallNode n, Set<CFGNode> in) {
            return Sets.union(in, Set.of(n));
        }
        
        @Override
        public Set<CFGNode> transfer(CFGMemAssignNode n, Set<CFGNode> in) {
            return Sets.union(in, Set.of(n));
        }

        @Override
        public Set<CFGNode> transfer(CFGVarAssignNode n, Set<CFGNode> in) {
            return Sets.union(in, Set.of(n));
        }

        @Override
        public Set<CFGNode> transferTrue(CFGIfNode n, Set<CFGNode> in) {
            return Sets.union(in, Set.of(n));
        }

        @Override
        public Set<CFGNode> transferFalse(CFGIfNode n, Set<CFGNode> in) {
            return Sets.union(in, Set.of(n));
        }

        @Override
        public Set<CFGNode> transfer(CFGStartNode n, Set<CFGNode> in) {
            return Sets.union(in, Set.of(n));
        }
        
    }
    
}
