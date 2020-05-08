package cyr7.cfg.ir.dfa;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import cyr7.cfg.ir.dfa.IrLiveVariableAnalysis.IrLiveVarLattice;
import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.util.Sets;

public enum IrLiveVariableAnalysis implements BackwardDataflowAnalysis<IrLiveVarLattice> {
    INSTANCE;

    @Override
    public IrLiveVarLattice topValue() {
        return new IrLiveVarLattice();
    }

    @Override
    public BackwardTransferFunction<IrLiveVarLattice> transfer() {
        return LiveVarTransferFunction.INSTANCE;
    }

    @Override
    public IrLiveVarLattice meet(IrLiveVarLattice lhs, IrLiveVarLattice rhs) {
        return IrLiveVarLattice.meet(lhs, rhs);
    }

    /**
     * Transfer function is defined as: in[n] = use[n] ∪ (out[n] — def [n])
     * @author ayang
     *
     */
    public enum LiveVarTransferFunction implements BackwardTransferFunction<IrLiveVarLattice> {
        INSTANCE;

        private IrLiveVarLattice transfer(Set<String> use, Set<String> out, Set<String> def) {
            Set<String> diff = Sets.difference(out, def);
            Set<String> total = Sets.union(use, diff);
            return new IrLiveVarLattice(total);
        }

        /**
         *  All variables in arguments are used.
         *  <p>
         *  All variables in collectors are defined.
         */
        @Override
        public IrLiveVarLattice transfer(CFGCallNode n, IrLiveVarLattice out) {
            return transfer(n.uses(), out.liveVars, n.defs());
        }

        /**
         * All variables in condition are used.
         * <p>
         * No variables are defined.
         */
        @Override
        public IrLiveVarLattice transfer(CFGIfNode n, IrLiveVarLattice out) {
            return transfer(n.uses(), out.liveVars, Collections.emptySet());
        }

        /**
         * All variables in source value and target value are used.
         * <p>
         * No variables are defined.
         */
        @Override
        public IrLiveVarLattice transfer(CFGMemAssignNode n, IrLiveVarLattice out) {
            return transfer(n.uses(), out.liveVars, Collections.emptySet());
        }

        /**
         * No variables are used. No variables are defined;
         */
        @Override
        public IrLiveVarLattice transfer(CFGReturnNode n, IrLiveVarLattice out) {
            return transfer(Collections.emptySet(),
                            out.liveVars, Collections.emptySet());
        }

        /**
         * All variables in rhs expression are used.
         * <p>
         * LHS variable is defined;
         */
        @Override
        public IrLiveVarLattice transfer(CFGVarAssignNode n, IrLiveVarLattice out) {
            return transfer(n.uses(), out.liveVars, n.defs());
        }

        @Override
        public IrLiveVarLattice transfer(CFGSelfLoopNode n,
                IrLiveVarLattice out) {
            return transfer(Collections.emptySet(),
                    out.liveVars, Collections.emptySet());
        }

        @Override
        public IrLiveVarLattice transfer(CFGBlockNode n, IrLiveVarLattice out) {
            return transfer(n.uses(), out.liveVars, n.defs());
        }
    }

    /**
     * The lattice is a set of strings, i.e. the set of live variables.
     *
     */
    public static class IrLiveVarLattice {
        @Override
        public int hashCode() {
            return Objects.hash(liveVars);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IrLiveVarLattice)) {
                return false;
            }
            IrLiveVarLattice other = (IrLiveVarLattice) obj;
            return Objects.equals(liveVars, other.liveVars);
        }

        public final Set<String> liveVars;

        public IrLiveVarLattice() {
            this.liveVars = Collections.emptySet();
        }

        public IrLiveVarLattice(Set<String> liveVars) {
            this.liveVars = Collections.unmodifiableSet(liveVars);
        }

        /**
         * The meet operator for this lattice is set union.
         */
        public static IrLiveVarLattice meet(IrLiveVarLattice lhs,
                                            IrLiveVarLattice rhs) {
            return new IrLiveVarLattice(Sets.union(lhs.liveVars, rhs.liveVars));
        }

    }

}
