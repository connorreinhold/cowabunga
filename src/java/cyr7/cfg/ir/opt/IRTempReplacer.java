package cyr7.cfg.ir.opt;

import java.util.Map;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;
import cyr7.visitor.MyIRVisitor;

public class IRTempReplacer {

    private IRTempReplacer() {}

    /**
     * Replaces all variables {@code v} used in {@code expr} with String to
     * String {@code mapping}. It is possible that variable substitution
     * requires several traversals through the map.
     * <p>
     * For example, suppose {@code mapping} contains the mappings
     * {x --> y}, {a --> x}, and {@code expr} is {@code a}. Then, {@code expr}
     * will be replaced as {@code y}. First, it gets {@code x}, but then
     * {@code x} is also mapped to {@code y}. At last, there is no further
     * mapping for {@code y}.
     *
     * @param expr The expression that may have temporaries to be replaced.
     * @param mapping Requires: there cannot be looped mappings, i.e. a mapping
     *                of {a --> b} and {b --> a} cannot be in {@code mapping}.
     * @return The expression {@code expr} with temporaries replaced.
     */
    public static IRExpr replace(IRExpr expr, Map<String, String> mapping) {
        return expr.accept(new IRReplaceTempVisitor(mapping));
    }

    private static class IRReplaceTempVisitor implements MyIRVisitor<IRExpr> {

        private final Map<String, String> mapping;

        public IRReplaceTempVisitor(Map<String, String> mapping) {
            this.mapping = mapping;
        }

        @Override
        public IRExpr visit(IRBinOp n) {
            var replaceLeft = n.left().accept(this);
            var replaceRight = n.right().accept(this);

            return new IRBinOp(n.location(), n.opType(), replaceLeft, replaceRight);
        }

        @Override
        public IRExpr visit(IRCall n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRConst n) {
            return n;
        }

        @Override
        public IRExpr visit(IRESeq n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRMem n) {
            return new IRMem(n.location(), n.expr().accept(this));
        }

        @Override
        public IRExpr visit(IRName n) {
            return n;
        }

        /**
         * Traverses to the oldest copy.
         */
        @Override
        public IRExpr visit(IRTemp n) {
            String key = n.name();
            while (this.mapping.containsKey(key)) {
                key = this.mapping.get(key);
            }
            return new IRTemp(n.location(), key);
        }

        @Override
        public IRExpr visit(IRCallStmt n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRCJump n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRCompUnit n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRExp n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRFuncDecl n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRJump n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRLabel n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRMove n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRReturn n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IRExpr visit(IRSeq n) {
            throw new UnsupportedOperationException();
        }
    }

}
