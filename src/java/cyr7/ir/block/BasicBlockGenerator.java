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

    public BasicBlockSet getBlocks() {
        BasicBlockSet blocks = new BasicBlockSet();
        List<IRStmt> seq = new LinkedList<>();

        stmts.stream().forEach(s -> {
            if (s.accept(hasJumps)) {
                seq.add(s);
                blocks.add(new BasicBlock(seq));
                seq.clear();
            } else if (s instanceof IRLabel) {
                if (!seq.isEmpty())
                    blocks.add(new BasicBlock(seq));
                seq.clear();
                seq.add(s);
            } else {
                seq.add(s);
            }
        });
        if (!seq.isEmpty()) {
            blocks.add(new BasicBlock(seq));
        }
        return blocks;
    }

}
