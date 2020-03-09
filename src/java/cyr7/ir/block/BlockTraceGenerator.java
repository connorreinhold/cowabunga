package cyr7.ir.block;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

public class BlockTraceGenerator {
    private final IdGenerator generator;

    public BlockTraceGenerator(IdGenerator generator) {
        this.generator = generator;
    }

    /**
     * Given a set of basic blocks, return a set of traces.
     * A trace is a list of blocks.
     * @param blocks
     * @return
     */
    public TraceSet getTraces(BasicBlockList blocks) {
        TraceSet traces = new TraceSet();
        blocks.unmarkBlocks();

        while (blocks.hasUnmarkedBlock()) {
            LinkedList<BasicBlock> trace = new LinkedList<>();
            BasicBlock b = blocks.getAnUnmarkedBlock();
            System.out.println(b);
            trace.add(b);
            List<String> jumpLabels = b.getJumpLabels();
            while (!jumpLabels.isEmpty()) {
                String label = jumpLabels.get(0);
                var maybeNextBlock = blocks.getBlock(label);
                if (maybeNextBlock.isPresent()) {
                    b = maybeNextBlock.get();
                    jumpLabels = b.getJumpLabels();
                    trace.add(b);
                } else {
                    break;
                }
            }
            traces.add(new ArrayList<>(trace));
        }
        optimize(traces);
        return traces;
    }

    private void optimize(TraceSet traces) {
        traces.forEach(trace -> {
            for (int i = 0; i < trace.size(); i++) {
                BasicBlock b = trace.get(i);
                IRStmt last = b.last();
                if (i < trace.size() - 1) {
                    BasicBlock nextBlock = trace.get(i + 1);
                    List<IRStmt> replace = last
                            .accept(new FinalBlockStmtVisitor(nextBlock));
                    b.replaceLastStmt(replace);
                }
            }
        });
    }

    private class FinalBlockStmtVisitor implements MyIRVisitor<List<IRStmt>> {

        private final String errorMsg = "The accessed node is an expression.";
        private final Optional<String> nextLabel;

        public FinalBlockStmtVisitor(BasicBlock nextBlock) {
            if (nextBlock.first.isPresent()) {
                this.nextLabel = Optional.of(nextBlock.first.get().name());
            } else {
                this.nextLabel = Optional.empty();
            }
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
            if (nextLabel.isPresent()) {
                if (falseLabel.equals(nextLabel.get())) {
                    return List.of(n);
                } else if (trueLabel.equals(nextLabel.get())) {
                    IRExpr inverted = make.IRBinOp(OpType.XOR, make.IRConst(1),
                            n.cond());
                    return List
                            .of(make.IRCJump(inverted, falseLabel, trueLabel));
                }
            }
            var newFalseLabel = generator.newLabel();
            return List.of(make.IRCJump(n.cond(), n.trueLabel(), newFalseLabel),
                    make.IRLabel(newFalseLabel),
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
                return List.of(n);
            }

            String label = labels.get(0);
            if (nextLabel.isPresent() && label.equals(nextLabel.get())) {
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
