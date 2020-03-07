package cyr7.ir.block;

import java.util.LinkedList;
import java.util.List;

public class BlockTraceGenerator {

    private final BasicBlockSet blocks;

    public BlockTraceGenerator(BasicBlockSet blocks) {
        this.blocks = blocks;
    }

    public List<BasicBlock> organize() {
        List<BasicBlock> orderedBlocks = new LinkedList<>();
        blocks.unmarkBlocks();
        return orderedBlocks;
    }

}
