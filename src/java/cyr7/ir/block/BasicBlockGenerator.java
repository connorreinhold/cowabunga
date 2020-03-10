package cyr7.ir.block;

import java.util.LinkedList;
import java.util.List;

import cyr7.ir.lowering.ContainsJumpsStmtVisitor;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRStmt;

public final class BasicBlockGenerator {

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
        List<IRStmt> currentBlockStmt = new LinkedList<>();

        stmts.stream().forEach(s -> {
            if (s.accept(hasJumps)) {
                currentBlockStmt.add(s);
                blocks.add(new BasicBlock(currentBlockStmt));
                currentBlockStmt.clear();
            } else if (s instanceof IRLabel) {
                if (!currentBlockStmt.isEmpty())
                    blocks.add(new BasicBlock(currentBlockStmt));
                currentBlockStmt.clear();
                currentBlockStmt.add(s);
            } else {
                currentBlockStmt.add(s);
            }
        });
        if (!currentBlockStmt.isEmpty()) {
            blocks.add(new BasicBlock(currentBlockStmt));
        }
        return new BasicBlockList(blocks);
    }
}