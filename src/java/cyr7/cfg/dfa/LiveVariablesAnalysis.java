package cyr7.cfg.dfa;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;
import cyr7.cfg.visitor.AbstractCFGVisitor;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LiveVariablesAnalysis implements ForwardDataflowAnalysis<Set<String>> {

    INSTANCE;

    @Override
    public Direction direction() {
        return Direction.BACKWARD;
    }

    @Override
    public Set<String> topValue() {
        return Set.of();
    }

    @Override
    public Set<String> transfer(CFGNode node, Set<String> element) {
    }

    @Override
    public Set<String> meet(Set<String> lhs, Set<String> rhs) {
        // union
        return Stream.concat(lhs.stream(), rhs.stream()).collect(Collectors.toSet());
    }

    static class TransferFunction extends AbstractCFGVisitor<Set<String>> {

        Set<String> in = Set.of();

        public void setIn(Set<String> in) {
            this.in = in;
        }

        @Override
        public Set<String> visit(CFGCallNode n) {
            return null;
        }

        @Override
        public Set<String> visit(CFGIfNode n) {
            return null;
        }

        @Override
        public Set<String> visit(CFGVarAssignNode n) {
            return null;
        }

        @Override
        public Set<String> visit(CFGMemAssignNode n) {
            return null;
        }

        @Override
        public Set<String> visit(CFGReturnNode n) {
            return null;
        }

        @Override
        public Set<String> visit(CFGStartNode n) {
            return null;
        }
    }

}

