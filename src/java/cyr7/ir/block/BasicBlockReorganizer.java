package cyr7.ir.block;

import java.util.LinkedList;
import java.util.List;

public class BasicBlockReorganizer {

    private final BasicBlockSet blocks;

    public BasicBlockReorganizer(BasicBlockSet blocks) {
        this.blocks = blocks;
    }

    public List<BasicBlock> organize() {
        List<BasicBlock> orderedBlocks = new LinkedList<>();
        blocks.unmarkBlocks();

    }

}
