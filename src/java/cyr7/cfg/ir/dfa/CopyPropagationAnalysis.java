package cyr7.cfg.ir.dfa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.ir.nodes.IRTemp;

public class CopyPropagationAnalysis implements
        ForwardDataflowAnalysis<LatticeElement> {

    public final static CopyPropagationAnalysis INSTANCE
            = new CopyPropagationAnalysis();

    @Override
    public LatticeElement topValue() {
        return LatticeElement.topElement();
    }

    @Override
    public ForwardTransferFunction<LatticeElement> transfer() {
        return new ForwardTransferFunction<LatticeElement>() {

            @Override
            public LatticeElement transfer(CFGCallNode n, LatticeElement in) {
                Map<String, String> updated = new HashMap<>(in.copies);

                // Kill all previous copies that map to the newly defined
                // variable.
                n.call.collectors().forEach(def -> {
                    updated.values().removeAll(Collections.singleton(def));
                });
                return new LatticeElement(updated);
            }

            @Override
            public LatticeElement transferTrue(CFGIfNode n, LatticeElement in) {
                return in.clone();
            }

            @Override
            public LatticeElement transferFalse(CFGIfNode n,
                    LatticeElement in) {
                return in.clone();
            }

            @Override
            public LatticeElement transfer(CFGMemAssignNode n,
                    LatticeElement in) {
                return in.clone();
            }

            @Override
            public LatticeElement transfer(CFGStartNode n, LatticeElement in) {
                return in.clone();
            }

            @Override
            public LatticeElement transfer(CFGVarAssignNode n,
                    LatticeElement in) {
                Map<String, String> updated = new HashMap<>(in.copies);

                // Kill all previous copies that map to the newly defined
                // variable.
                updated.values().removeAll(Collections.singleton(n.variable));
                if (n.value instanceof IRTemp) {
                    String source = ((IRTemp)n.value).name();
                    updated.put(source, n.variable);
                }
                return new LatticeElement(updated);
            }
        };
    }

    /**
     * Removes conflicting mappings, e.g. {@code lhs} has { x --> y}
     * and {@code rhs} has {x --> z}. Conflicting copies, so they are removed.
     *
     */
    @Override
    public LatticeElement meet(LatticeElement lhs, LatticeElement rhs) {
        Map<String, String> updated = new HashMap<>(lhs.copies);
        rhs.copies.forEach((val, copyOf) -> {
            if (updated.containsKey(val)) {
                updated.remove(val);
            } else {
                updated.put(val, copyOf);
            }
        });
        return new LatticeElement(updated);
    }

}

class LatticeElement {

    /**
     * Maps the value to the definition.
     * <p>
     * Example: y = x is mapped as {x --> y}
     */
    protected final Map<String, String> copies;

    private LatticeElement() {
        this.copies = Collections.unmodifiableMap(new HashMap<>());
    }

    protected LatticeElement(Map<String, String> copies) {
        this.copies = Collections.unmodifiableMap(new HashMap<>());
    }

    @Override
    public LatticeElement clone() {
        return new LatticeElement(this.copies);
    }

    /**
     * Top Element is an empty mapping.
     */
   public static LatticeElement topElement() {
        return new LatticeElement();
    }
}