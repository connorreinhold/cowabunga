package cyr7.cfg.ir.dfa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cyr7.cfg.ir.dfa.CopyPropagationAnalysis.CopyPropLattice;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.ir.nodes.IRTemp;

public enum CopyPropagationAnalysis implements
        ForwardDataflowAnalysis<CopyPropLattice> {
    INSTANCE;

    @Override
    public CopyPropLattice topValue() {
        return CopyPropLattice.topElement();
    }

    /**
     * Transfer function: gen[n] U (in[n] - kill[n])
     */
    @Override
    public ForwardTransferFunction<CopyPropLattice> transfer() {
        return new ForwardTransferFunction<CopyPropLattice>() {

            @Override
            public CopyPropLattice transfer(CFGCallNode n, CopyPropLattice in) {
                Map<String, String> updated = new HashMap<>(in.copies);
                // Kill all previous copies that map to the newly defined
                // variable.
                n.call.collectors().forEach(updated::remove);
                updated.values().removeAll(n.call.collectors());
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
                updated.remove(n.variable);
                updated.values().removeAll(Collections.singleton(n.variable));
                if (n.value instanceof IRTemp) {
                    String source = ((IRTemp)n.value).name();
                    updated.put(n.variable, source);
                }

                return new CopyPropLattice(updated);
            }

            @Override
            public CopyPropLattice transfer(CFGSelfLoopNode n,
                    CopyPropLattice in) {
                return in.clone();
            }
        };
    }

    /**
     * Removes conflicting mappings, e.g. {@code lhs} has {x --> y}
     * and {@code rhs} has {x --> z}. Conflicting copies, so they are removed.
     */
    @Override
    public CopyPropLattice meet(CopyPropLattice lhs, CopyPropLattice rhs) {
        Map<String, String> updated = new HashMap<>(lhs.copies);
        updated.keySet().removeIf(key -> {
            return !rhs.copies.containsKey(key)
                    || !rhs.copies.get(key).equals(updated.get(key));
        });
        return new CopyPropLattice(updated);
    }

    public static class CopyPropLattice {

        @Override
        public int hashCode() {
            return Objects.hash(copies);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CopyPropLattice)) {
                return false;
            }
            CopyPropLattice other = (CopyPropLattice) obj;
            return Objects.equals(copies, other.copies);
        }

        /**
         * Maps the definition to the value.
         * <p>
         * Example: y = x is mapped as {y --> x}
         */
        public final Map<String, String> copies;


        /**
         * Inverse of {@code copies} mapping.
         */
//        public final Map<String, Set<String>> inverseOfCopies;


        private CopyPropLattice() {
            this.copies = Collections.unmodifiableMap(new HashMap<>());
//            this.inverseOfCopies = Collections.unmodifiableMap(new HashMap<>());
        }

        protected CopyPropLattice(Map<String, String> copies) {
            this.copies = Collections.unmodifiableMap(copies);

//            Map<String, Set<String>> inverted = new HashMap<>();
//            copies.forEach((k, v) -> {
//                if (inverted.containsKey(v)) {
//                    inverted.get(v).add(k);
//                } else {
//                    inverted.put(v, new HashSet<>());
//                    inverted.get(v).add(k);
//                }
//            });
//            this.inverseOfCopies = Collections.unmodifiableMap(inverted);
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