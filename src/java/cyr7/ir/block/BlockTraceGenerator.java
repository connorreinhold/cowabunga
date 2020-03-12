package cyr7.ir.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cli.CLI;
import cyr7.ir.CTranslationVisitor;
import cyr7.ir.IdGenerator;
import cyr7.ir.block.util.LabelsInJumpStmtsVisitor;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
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
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.visitor.MyIRVisitor;

final class BlockTraceGenerator {

    public static List<List<BasicBlock>> getTraces(IdGenerator generator, List<BasicBlock> basicBlocks) {
        List<List<BasicBlock>> traces = new LinkedList<>();
        Queue<BasicBlock> queue = new LinkedList<>(basicBlocks);
        Set<BasicBlock> markedBlocks = new HashSet<>();

        Map<String, BasicBlock> labelToBlock = new HashMap<>();
        basicBlocks.forEach(b ->
            b.first().ifPresent(l -> labelToBlock.put(l.name(), b))
        );

        while (!queue.isEmpty()) {
            LinkedList<BasicBlock> trace = new LinkedList<>();
            BasicBlock block = queue.remove();

            nextBlock: while (!markedBlocks.contains(block)) {
                markedBlocks.add(block);
                trace.add(block);

                for (String label : block.getJumpLabels()) {
                    BasicBlock successor = labelToBlock.get(label);
                    if (successor != null && !markedBlocks.contains(successor)) {
                        block = successor;
                        continue nextBlock;
                    }
                }

                Optional<BasicBlock> fallthroughBlock = fallthroughBlock(basicBlocks, block);
                if (fallthroughBlock.isPresent() && !markedBlocks.contains(fallthroughBlock.get())) {
                    block = fallthroughBlock.get();
                }
            }

            traces.add(trace);
        }

        optimize(generator, traces);

        return traces;
    }

    /**
     * Get the block that would have fallen through for block if it exists.
     * @param blocks
     * @param block
     * @return
     */
    private static Optional<BasicBlock> fallthroughBlock(List<BasicBlock> blocks, BasicBlock block) {
        Iterator<BasicBlock> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == block) {
                if (iterator.hasNext()) {
                    return Optional.of(iterator.next());
                } else {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Removes useless jumps and organizes cjumps such that the false label
     * always occurs directly after the cjump
     *
     * @param generator
     * @param traces
     */
    private static void optimize(IdGenerator generator, List<List<BasicBlock>> traces) {
        traces.forEach(trace -> {
            for (int i = 0; i < trace.size(); i++) {
                BasicBlock b = trace.get(i);

                if (b.last().isPresent() && i < trace.size() - 1) {
                    IRStmt last = b.last().get();

                    BasicBlock nextBlock = trace.get(i + 1);
                    List<IRStmt> replacement=
                        last.accept(new FinalBlockStmtVisitor(generator, nextBlock));
                    b.replaceLastStmt(replacement);
                }
            }
        });
    }

    private static class FinalBlockStmtVisitor implements MyIRVisitor<List<IRStmt>> {

        private final String errorMsg = "The accessed node is an expression.";
        private final Optional<String> firstLabelOfNextBlock;

        private final IdGenerator generator;

        public FinalBlockStmtVisitor(IdGenerator generator, BasicBlock nextBlock) {
            this.generator = generator;
            this.firstLabelOfNextBlock = nextBlock.first().map(IRLabel::name);
        }

        @Override
        public List<IRStmt> visit(IRBinOp n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRCall n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRConst n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRESeq n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRMem n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRName n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRTemp n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRCallStmt n) {
            return List.of(n);
        }

        @Override
        public List<IRStmt> visit(IRCJump n) {
            IRNodeFactory make = new IRNodeFactory_c(n.location());

            String trueLabel = n.trueLabel();
            String falseLabel = n.falseLabel();

            if (firstLabelOfNextBlock.isPresent()) {
                if (falseLabel.equals(firstLabelOfNextBlock.get())) {
                    // the false label would fall through anyways
                    return List.of(
                        make.IRCJump(n.cond(), n.trueLabel())
                    );

                } else if (trueLabel.equals(firstLabelOfNextBlock.get())) {
                    // we swap false and true so we fall through to the true
                    IRExpr inverted = make.IRBinOp(
                        OpType.XOR,
                        make.IRConst(1),
                        n.cond()
                    );

                    return List.of(make.IRCJump(inverted, falseLabel));
                }
            }

            return List.of(
                    make.IRCJump(n.cond(), n.trueLabel()),
                    make.IRJump(make.IRName(n.falseLabel())));

        }

        @Override
        public List<IRStmt> visit(IRCompUnit n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRExp n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRFuncDecl n) {
            throw new UnsupportedOperationException(errorMsg);
        }

        @Override
        public List<IRStmt> visit(IRJump n) {
            List<String> labels = n.accept(LabelsInJumpStmtsVisitor.instance);
            if (labels.isEmpty()) {
                CLI.debugPrint("Maybe something is not right?");
                return List.of(n);
            }

            String label = labels.get(0);
            if (firstLabelOfNextBlock.isPresent()
                    && label.equals(firstLabelOfNextBlock.get())) {
                return List.of();
            } else {
                return List.of(n);
            }
        }

        @Override
        public List<IRStmt> visit(IRLabel n) {
            return List.of(n);
        }

        @Override
        public List<IRStmt> visit(IRMove n) {
            return List.of(n);
        }

        @Override
        public List<IRStmt> visit(IRReturn n) {
            return List.of(n);
        }

        @Override
        public List<IRStmt> visit(IRSeq n) {
            throw new UnsupportedOperationException(errorMsg);
        }

    }

}
