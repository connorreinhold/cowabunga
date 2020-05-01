package cyr7.ir.block;

import java.util.LinkedList;
import java.util.List;

import cyr7.ir.IdGenerator;
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
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.visitor.MyIRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public final class BlockGenerator {

    /**
     * A block is a unit of code that always starts at its first statement and
     * jumps at its last statement.
     *
     * Every block returned by this method begins with a label and ends with an
     * unconditional jump.
     *
     * @param stmts
     * A list of statements such that every CJUMP has both true and false labels
     */
    public static List<BasicBlock> getBlocks(IdGenerator generator, List<IRStmt> stmts) {
        // assert all CJUMPs have a false label (they all have a true labels)
        assert stmts.stream()
            .filter(s -> s instanceof IRCJump)
            .map(s -> (IRCJump) s)
            .allMatch(IRCJump::hasFalseLabel);

        TerminatesBlockVisitor blockTerminator = new TerminatesBlockVisitor();
        List<BasicBlock> blocks = new LinkedList<>();
        List<IRStmt> currentBlockStmts = new LinkedList<>();

        for (IRStmt s : stmts) {
            IRNodeFactory make = new IRNodeFactory_c(s.location());

            if (s.accept(blockTerminator)) {
                // s is a block terminator

                /*
                 * Complete by adding the terminating statement to the block
                 * being built. Append a label to the front of this block if
                 * necessary.
                 */

                currentBlockStmts.add(s);
                addLabelToStartIfNeeded(generator, currentBlockStmts);

                blocks.add(new BasicBlock(currentBlockStmts));
                currentBlockStmts.clear();
            } else if (s instanceof IRLabel) {
                IRLabel label = (IRLabel) s;
                // s is the start of the next label
                if (!currentBlockStmts.isEmpty()) {
                    // Make a new block if there is already a non-empty block being built.
                    if (!currentBlockStmts.get(currentBlockStmts.size() - 1)
                            .accept(blockTerminator)) {
                        // the current block doesn't have a terminator, so we
                        // add a jump statement to the next block
                        var jumpStmt = make.IRJump(make.IRName(label.name()));
                        currentBlockStmts.add(jumpStmt);
                    }
                    addLabelToStartIfNeeded(generator, currentBlockStmts);
                    blocks.add(new BasicBlock(currentBlockStmts));
                    currentBlockStmts.clear();
                }

                currentBlockStmts.add(s);
            } else {
                currentBlockStmts.add(s);
            }
        }

        // all blocks begin with a label
        assert blocks.stream()
            .allMatch(s -> s.stmts.get(0) instanceof IRLabel);

        assert blocks.stream()
            .allMatch(s -> s.stmts.get(s.stmts.size() - 1).accept(new TerminatesBlockVisitor()));

        if (!currentBlockStmts.isEmpty()) {
            /*
             * The last statement of a function block should be a return.
             * However, CTranslationVisitor may naively insert a label to the
             * "rest" of the function after a return. Therefore, there may be
             * labels generated after the last return.
             */

            assert currentBlockStmts.size() == 1 : currentBlockStmts.toString();
            assert currentBlockStmts.get(0) instanceof IRLabel;
            blocks.add(new BasicBlock(currentBlockStmts));
        }

        return blocks;
    }

    private static void addLabelToStartIfNeeded(IdGenerator generator,
            List<IRStmt> stmts) {
        if (stmts.isEmpty() || !(stmts.get(0) instanceof IRLabel)) {
            stmts.add(0, new IRLabel(
                    new Location("IRLabel inserted by BlockGenerator", -2, -2),
                generator.newLabel()));
        }
    }

    /**
     * A statement is a block terminator if it is one of: JUMP, CJUMP, or
     * RETURN.
     *
     * @author ayang
     *
     */
    private static class TerminatesBlockVisitor implements MyIRVisitor<Boolean> {

        /**
         * Call is not an statement in LIR. Use CallStmt instead.
         */
        @Override
        public Boolean visit(IRCall n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean visit(IRBinOp n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean visit(IRConst n) {
            throw new UnsupportedOperationException();
        }

        /**
         * This is not a statement in LIR
         */
        @Override
        public Boolean visit(IRESeq n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean visit(IRMem n) {
            return false;
        }

        @Override
        public Boolean visit(IRName n) {
            return false;
        }

        @Override
        public Boolean visit(IRTemp n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean visit(IRCallStmt n) {
            return false;
        }

        @Override
        public Boolean visit(IRCJump n) {
            /*
             * We assume that every cjump terminates a block, i.e. that that
             * both labels of the cjump are present so that the next block will
             * never fall through
             */
            assert n.hasFalseLabel();
            return true;
        }

        @Override
        public Boolean visit(IRCompUnit n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean visit(IRExp n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean visit(IRFuncDecl n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean visit(IRJump n) {
            return true;
        }

        @Override
        public Boolean visit(IRLabel n) {
            return false;
        }

        @Override
        public Boolean visit(IRMove n) {
            return false;
        }

        @Override
        public Boolean visit(IRReturn n) {
            return true;
        }

        /**
         * No Seq in LIR
         */
        @Override
        public Boolean visit(IRSeq n) {
            throw new UnsupportedOperationException();
        }

    }

    private BlockGenerator() {}

}
