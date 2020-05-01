package cyr7.cfg.dfa;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LiveVariablesAnalysis implements BackwardDataflowAnalysis<Set<String>> {

    INSTANCE;

    private final TransferFunction transferFunction = new TransferFunction();

    @Override
    public Set<String> topValue() {
        return Set.of();
    }

    @Override
    public BackwardTransferFunction<Set<String>> transfer() {
        return transferFunction;
    }

    @Override
    public Set<String> meet(Set<String> lhs, Set<String> rhs) {
        return Stream.concat(lhs.stream(), rhs.stream()).collect(Collectors.toSet());
    }

    static class TransferFunction implements BackwardTransferFunction<Set<String>> {

        @Override
        public Set<String> transfer(CFGCallNode n, Set<String> in) {
            return null;
        }

        @Override
        public Set<String> transfer(CFGIfNode n, Set<String> in) {
            return null;
        }

        @Override
        public Set<String> transfer(CFGMemAssignNode n, Set<String> in) {
            return null;
        }

        @Override
        public Set<String> transfer(CFGReturnNode n, Set<String> in) {
            return null;
        }

        @Override
        public Set<String> transfer(CFGVarAssignNode n, Set<String> in) {
            return null;
        }

    }

}

