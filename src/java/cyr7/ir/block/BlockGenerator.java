package cyr7.ir.block;

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

import java.util.LinkedList;
import java.util.List;

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

                currentBlockStmts.add(s);

                addLabelToStartIfNeeded(generator, currentBlockStmts);
                if (currentBlockStmts.size() == 1) {
                    System.out.println();
                }
                blocks.add(new BasicBlock(currentBlockStmts));
                currentBlockStmts.clear();

            } else if (s instanceof IRLabel) {
                IRLabel label = (IRLabel) s;
                // s is the start of the next label

                if (!currentBlockStmts.isEmpty()) {
                    if (!currentBlockStmts.get(currentBlockStmts.size() - 1).accept(blockTerminator)) {
                        // the current block doesn't have a terminator, so we
                        // add a jump statement to the next block

                        currentBlockStmts.add(make.IRJump(make.IRName(label.name())));
                    }

                    addLabelToStartIfNeeded(generator, currentBlockStmts);
                    if (currentBlockStmts.size() == 1) {
                        System.out.println();
                    }
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
             * However, various optimization stages (I think CTranslation ???)
             * might cause statements to be generated after the last return.
             * Therefore, these statements should never occur. Just in case,
             * there is a call to `_xi_out_of_bounds` which should terminate
             * the program.
             *
             * Finally, since it's a guarantee of the function that all blocks
             * end with jump statements, this block includes a jump to a fake
             * label
             */

            addLabelToStartIfNeeded(generator, currentBlockStmts);

            Location location = new Location("Fatal Error inserted by BlockTraceOptimizer", 0, 0);
            currentBlockStmts.add(
                new IRCallStmt(location,
                    List.of(),
                    new IRName(location, "_xi_out_of_bounds"),
                    List.of()));
            currentBlockStmts.add(
                new IRJump(
                    location,
                    new IRName(location, generator.newLabel())
                ));

            blocks.add(new BasicBlock(currentBlockStmts));
        }

        return blocks;
    }

    private static void addLabelToStartIfNeeded(IdGenerator generator, List<IRStmt> stmts) {
        if (stmts.isEmpty() || !(stmts.get(0) instanceof IRLabel)) {
            stmts.add(0, new IRLabel(
                new Location("Label Inserted by BlockGenerator", 0, 0),
                generator.newLabel()));
        }
    }

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