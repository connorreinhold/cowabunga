package cyr7.cfg.ir.dfa;

import java.util.Collections;
import java.util.Set;

import cyr7.cfg.ir.dfa.DominatorAnalysis.LatticeElement;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.util.Sets;

public enum DominatorAnalysis implements ForwardDataflowAnalysis<LatticeElement> {
    
    INSTANCE;

    public interface LatticeElement {
        
        static LatticeElement initialize(Set<CFGNode> dominators) {
            return new DominatorLatticeElement(dominators);
        }
        
        LatticeElement top = initialize(Collections.emptySet());
        
        Set<CFGNode> dominators();
    }
    
    private final static class DominatorLatticeElement implements LatticeElement{
        private final Set<CFGNode> dominators;
        
        public DominatorLatticeElement(Set<CFGNode> dominators) {
            this.dominators = Set.copyOf(dominators);
        }
        
        public Set<CFGNode> dominators() {
            return dominators;
        }
        
        @Override
        public String toString() {
            return dominators.toString();
        }
    }
    

    @Override
    public LatticeElement topValue() {
        return LatticeElement.top;
    }

    @Override
    public ForwardTransferFunction<LatticeElement> transfer() {
        return TransferFunction.INSTANCE;
    }

    @Override
    public LatticeElement meet(LatticeElement lhs, LatticeElement rhs) {
        Set<CFGNode> dominators = Sets.intersection(lhs.dominators(), 
                rhs.dominators());
        return LatticeElement.initialize(dominators);
    }
    
    private enum TransferFunction implements ForwardTransferFunction<LatticeElement> {
        INSTANCE;

        @Override
        public LatticeElement transfer(CFGCallNode n, LatticeElement in) {
            Set<CFGNode> dominators = Sets.union(in.dominators(), Set.of(n));
            return LatticeElement.initialize(dominators);
        }
        
        @Override
        public LatticeElement transfer(CFGMemAssignNode n, LatticeElement in) {
            Set<CFGNode> dominators = Sets.union(in.dominators(), Set.of(n));
            return LatticeElement.initialize(dominators);
        }

        @Override
        public LatticeElement transfer(CFGVarAssignNode n, LatticeElement in) {
            Set<CFGNode> dominators = Sets.union(in.dominators(), Set.of(n));
            return LatticeElement.initialize(dominators);
        }

        @Override
        public LatticeElement transferTrue(CFGIfNode n, LatticeElement in) {
            Set<CFGNode> dominators = Sets.union(in.dominators(), Set.of(n));
            return LatticeElement.initialize(dominators);
        }

        @Override
        public LatticeElement transferFalse(CFGIfNode n, LatticeElement in) {
            Set<CFGNode> dominators = Sets.union(in.dominators(), Set.of(n));
            return LatticeElement.initialize(dominators);
        }

        @Override
        public LatticeElement transfer(CFGStartNode n, LatticeElement in) {
            Set<CFGNode> dominators = Sets.union(in.dominators(), Set.of(n));
            return LatticeElement.initialize(dominators);
        }
        
    }
    
}
