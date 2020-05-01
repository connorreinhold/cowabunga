package cyr7.cfg.dfa;

import cyr7.cfg.dfa.CCPAnalysis.LatticeElement;
import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;
import cyr7.ir.BinOpInterpreter;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;
import cyr7.util.Sets;
import cyr7.visitor.MyIRVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CCPAnalysis implements ForwardDataflowAnalysis<LatticeElement> {

    @Override
    public LatticeElement topValue() {
        // all code is reachable and all variables are initially set to top
        return new LatticeElement(false);
    }

    @Override
    public ForwardTransferFunction<LatticeElement> transfer() {
        return TransferFunction.INSTANCE;
    }

    @Override
    public LatticeElement meet(LatticeElement lhs, LatticeElement rhs) {
        Set<String> variables = Sets.union(lhs.values.keySet(), rhs.values.keySet());

        Map<String, VLatticeElement> values = new HashMap<>(variables.size());
        for (String variable : variables) {
            values.put(variable, meet(lhs.getValue(variable), rhs.getValue(variable)));
        }

        return new LatticeElement(lhs.unreachable && rhs.unreachable, values);
    }

    private static VLatticeElement meet(VLatticeElement lhs, VLatticeElement rhs) {
        if (lhs.isTop() || rhs.isBot()) {
            return rhs;
        } else if (lhs.isBot() || rhs.isTop()) {
            return lhs;
        } else {
            // they're both values
            long left = lhs.value(), right = rhs.value();

            if (left == right) {
                return VLatticeElement.value(left);
            } else {
                return VLatticeElement.bot;
            }
        }
    }

    private interface VLatticeElement {

        boolean isTop();
        boolean isBot();
        long value();

        VLatticeElement top = new VLatticeElement() {
            @Override public boolean isTop() { return true; }
            @Override public boolean isBot() { return false; }
            @Override public long value() { return 0; }
        };

        VLatticeElement bot = new VLatticeElement() {
            @Override public boolean isTop() { return false; }
            @Override public boolean isBot() { return true; }
            @Override public long value() { return 0; }
        };

        static VLatticeElement value(long val) {
            return new VLatticeElement() {
                @Override public boolean isTop() { return false; }
                @Override public boolean isBot() { return false; }
                @Override public long value() { return val; }
            };
        }

    }

    public static final class LatticeElement {

        public final boolean unreachable;
        private final Map<String, VLatticeElement> values;

        public LatticeElement(boolean unreachable) {
            this.unreachable = unreachable;
            this.values = new HashMap<>(4);
        }

        public LatticeElement(
            boolean unreachable,
            Map<String, VLatticeElement> values) {

            this.unreachable = unreachable;
            this.values = Map.copyOf(values);
        }

        public VLatticeElement getValue(String variable) {
            return values.getOrDefault(variable, VLatticeElement.top);
        }

    }

    private enum TransferFunction implements ForwardTransferFunction<LatticeElement> {

        INSTANCE;

        @Override
        public LatticeElement transfer(CFGCallNode n, LatticeElement in) {
            return null;
        }

        @Override
        public LatticeElement transferTrue(CFGIfNode n, LatticeElement in) {
            return null;
        }

        @Override
        public LatticeElement transferFalse(CFGIfNode n, LatticeElement in) {
            return null;
        }

        @Override
        public LatticeElement transfer(CFGMemAssignNode n, LatticeElement in) {
            return null;
        }

        @Override
        public LatticeElement transfer(CFGStartNode n, LatticeElement in) {
            return null;
        }

        @Override
        public LatticeElement transfer(CFGVarAssignNode n, LatticeElement in) {
            return null;
        }

    }

    private static final class AbstractInterpreter implements MyIRVisitor<VLatticeElement> {

        private final Map<String, VLatticeElement> env;

        private AbstractInterpreter(Map<String, VLatticeElement> env) {
            this.env = env;
        }

        @Override
        public VLatticeElement visit(IRBinOp n) {
            VLatticeElement
                left = n.left().accept(this),
                right = n.right().accept(this);

            if (left.isTop() || right.isTop()) {
                return VLatticeElement.top;
            } else if (left.isBot() || right.isBot()) {
                return VLatticeElement.bot;
            } else {
                // they're both values
                long value = BinOpInterpreter.interpret(
                    n.opType(),
                    left.value(),
                    right.value());
                return VLatticeElement.value(value);
            }
        }

        @Override
        public VLatticeElement visit(IRCall n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRConst n) {
            return VLatticeElement.value(n.constant());
        }

        @Override
        public VLatticeElement visit(IRESeq n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRMem n) {
            return VLatticeElement.bot;
        }

        @Override
        public VLatticeElement visit(IRName n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRTemp n) {
            return env.getOrDefault(n.name(), VLatticeElement.top);
        }

        @Override
        public VLatticeElement visit(IRCallStmt n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRCJump n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRCompUnit n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRExp n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRFuncDecl n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRJump n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRLabel n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRMove n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRReturn n) {
            throw new AssertionError();
        }

        @Override
        public VLatticeElement visit(IRSeq n) {
            throw new AssertionError();
        }

    }

}
