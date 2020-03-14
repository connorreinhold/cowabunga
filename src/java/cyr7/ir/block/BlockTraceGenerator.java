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

/**
 * Generates a list of traces from a list of basic blocks.
 */
final class BlockTraceGenerator {

    public static List<List<BasicBlock>> getTraces(List<BasicBlock> basicBlocks) {
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
            }

            if (!trace.isEmpty()) {
                traces.add(trace);
            }
        }

        assert markedBlocks.containsAll(basicBlocks);
        assert basicBlocks.containsAll(markedBlocks);

        return traces;
    }

}
