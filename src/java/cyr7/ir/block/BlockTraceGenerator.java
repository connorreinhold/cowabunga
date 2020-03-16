package cyr7.ir.block;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
