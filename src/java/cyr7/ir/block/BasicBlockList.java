package cyr7.ir.block;

import cyr7.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

/**
 * Contains a collection of BasicBlocks, i.e. a list of statements that either
 * end with a JUMP/CJUMP/RETURN or whose IR-code would have been immediately
 * following by a LABEL.
 *
 * @author ayang
 *
 */
final class BasicBlockList {

    /**
     *
     */
    private static final long serialVersionUID = 6515358544269791637L;

    public final List<BasicBlock> basicBlocks;
    private final Queue<BasicBlock> unmarkedBlocks;
    private final Map<String, BasicBlock> labelToBlock;

    public BasicBlockList(List<BasicBlock> blocks) {
        this.basicBlocks = Util.immutableCopy(blocks);

        this.unmarkedBlocks = new LinkedList<>(this.basicBlocks);
        this.labelToBlock = new HashMap<>();
        this.basicBlocks.forEach(b ->
            b.first().ifPresent(l -> this.labelToBlock.put(l.name(), b))
        );
    }

    public void unmarkBlocks() {
        this.unmarkedBlocks.clear();
        this.unmarkedBlocks.addAll(this.basicBlocks);
    }

    /**
     * Returns the unmarked block that begins with the label {@code label}. If
     * no unmarked block starts with {@code label}, then {@code Empty} is
     * returned.
     *
     * <p>
     * Side Effect: If a block starting with label {@code label} is found, then
     * it is removed from the list of unmarked blocks. Additionally, the
     * succeeding block will be moved to lowest priority, which will affect when
     * the block will appear if an arbitrary block is desired.
     *
     * @param label
     * @return
     */
    public Optional<BasicBlock> getBlock(String label) {
        Iterator<BasicBlock> itr = this.unmarkedBlocks.iterator();

        while (itr.hasNext()) {
            BasicBlock b = itr.next();

            var maybeLabel = b.first();
            if (maybeLabel.isPresent()
                    && maybeLabel.get().name().equals(label)) {

                itr.remove();
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    public BasicBlock getAnUnmarkedBlock() {
        return this.unmarkedBlocks.remove();
    }

    public boolean hasUnmarkedBlock() {
        return !this.unmarkedBlocks.isEmpty();
    }

    @Override
    public String toString() {
        return basicBlocks.toString();
    }
}
