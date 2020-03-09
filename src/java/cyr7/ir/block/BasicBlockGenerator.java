package cyr7.ir.block;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cyr7.ir.lowering.ContainsJumpsStmtVisitor;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRStmt;

public class BasicBlockGenerator {

    private final List<IRStmt> stmts;
    private final ContainsJumpsStmtVisitor hasJumps;

    public BasicBlockGenerator(List<IRStmt> stmts) {
        this.stmts = Collections.unmodifiableList(stmts);
        this.hasJumps = new ContainsJumpsStmtVisitor();
    }

    public BasicBlockList getBlocks() {
        List<BasicBlock> blocks = new LinkedList<>();
        List<IRStmt> block = new LinkedList<>();

        stmts.stream().forEach(s -> {
            if (s.accept(hasJumps)) {
                block.add(s);
                blocks.add(new BasicBlock(block));
                block.clear();
            } else if (s instanceof IRLabel) {
                if (!block.isEmpty())
                    blocks.add(new BasicBlock(block));
                block.clear();
                block.add(s);
            } else {
                block.add(s);
            }
        });
        if (!block.isEmpty()) {
            blocks.add(new BasicBlock(block));
        }
        return new BasicBlockList(blocks);
    }

}
