package cyr7.cfg.ir.dfa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cyr7.cfg.ir.dfa.CopyPropagationAnalysis.CopyPropLattice;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.ir.nodes.IRTemp;

public class CopyPropagationAnalysis implements
        ForwardDataflowAnalysis<CopyPropLattice> {

    public final static CopyPropagationAnalysis INSTANCE
            = new CopyPropagationAnalysis();

    @Override
    public CopyPropLattice topValue() {
        return CopyPropLattice.topElement();
    }

    @Override
    public ForwardTransferFunction<CopyPropLattice> transfer() {
        return new ForwardTransferFunction<CopyPropLattice>() {

            @Override
            public CopyPropLattice transfer(CFGCallNode n, CopyPropLattice in) {
                Map<String, String> updated = new HashMap<>(in.copies);

                // Kill all previous copies that map to the newly defined
                // variable.
                n.call.collectors().forEach(def -> {
                    updated.values().removeAll(Collections.singleton(def));
                });
                return new CopyPropLattice(updated);
            }

            @Override
            public CopyPropLattice transferTrue(CFGIfNode n, CopyPropLattice in) {
                return in.clone();
            }

            @Override
            public CopyPropLattice transferFalse(CFGIfNode n,
                    CopyPropLattice in) {
                return in.clone();
            }

            @Override
            public CopyPropLattice transfer(CFGMemAssignNode n,
                    CopyPropLattice in) {
                return in.clone();
            }

            @Override
            public CopyPropLattice transfer(CFGStartNode n, CopyPropLattice in) {
                return in.clone();
            }

            @Override
            public CopyPropLattice transfer(CFGVarAssignNode n,
                    CopyPropLattice in) {
                Map<String, String> updated = new HashMap<>(in.copies);

                // Kill all previous copies that map to the newly defined
                // variable.
                updated.values().removeAll(Collections.singleton(n.variable));
                if (n.value instanceof IRTemp) {
                    String source = ((IRTemp)n.value).name();
                    updated.put(source, n.variable);
                }
                return new CopyPropLattice(updated);
            }
        };
    }

    /**
     * Removes conflicting mappings, e.g. {@code lhs} has { x --> y}
     * and {@code rhs} has {x --> z}. Conflicting copies, so they are removed.
     */
    @Override
    public CopyPropLattice meet(CopyPropLattice lhs, CopyPropLattice rhs) {
        Map<String, String> updated = new HashMap<>(lhs.copies);
        rhs.copies.forEach((val, copyOf) -> {
            if (updated.containsKey(val)) {
                updated.remove(val);
            } else {
                updated.put(val, copyOf);
            }
        });
        return new CopyPropLattice(updated);
    }

    public static class CopyPropLattice {

        /**
         * Maps the value to the definition.
         * <p>
         * Example: y = x is mapped as {x --> y}
         */
        public final Map<String, String> copies;

        private CopyPropLattice() {
            this.copies = Collections.unmodifiableMap(new HashMap<>());
        }

        protected CopyPropLattice(Map<String, String> copies) {
            this.copies = Collections.unmodifiableMap(new HashMap<>());
        }

        @Override
        public CopyPropLattice clone() {
            return new CopyPropLattice(this.copies);
        }

        /**
         * Top Element is an empty mapping.
         */
       public static CopyPropLattice topElement() {
            return new CopyPropLattice();
        }
    }

}