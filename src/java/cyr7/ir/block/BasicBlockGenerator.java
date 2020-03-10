package cyr7.ir.block;

import java.util.LinkedList;
import java.util.List;

import cyr7.ir.lowering.ContainsJumpsStmtVisitor;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRStmt;

final class BasicBlockGenerator {

    private BasicBlockGenerator() {}

    /**
     * A block is defined as: The last statement is a JUMP/CJUMP/RETURN.
     * OR
     * The first statement of the next block is a LABEL.
     *
     * @param stmts
     * @return
     */
    public static BasicBlockList getBlocks(List<IRStmt> stmts) {
        ContainsJumpsStmtVisitor hasJumps = new ContainsJumpsStmtVisitor();
        List<BasicBlock> blocks = new LinkedList<>();
        List<IRStmt> currentBlockStmts = new LinkedList<>();

        stmts.stream().forEach(s -> {
            if (s.accept(hasJumps)) {
                currentBlockStmts.add(s);
                blocks.add(new BasicBlock(currentBlockStmts));
                currentBlockStmts.clear();
            } else if (s instanceof IRLabel) {
                if (!currentBlockStmts.isEmpty())
                    blocks.add(new BasicBlock(currentBlockStmts));
                currentBlockStmts.clear();
                currentBlockStmts.add(s);
            } else {
                currentBlockStmts.add(s);
            }
        });
        if (!currentBlockStmts.isEmpty()) {
            blocks.add(new BasicBlock(currentBlockStmts));
        }
        return new BasicBlockList(blocks);
    }
}