package cyr7.ir.block;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cyr7.ir.lowering.JumpableIRVisitor;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRStmt;

public class BasicBlockGenerator {

    private final List<IRStmt> stmts;
    private final JumpableIRVisitor hasJumps;

    public BasicBlockGenerator(List<IRStmt> stmts) {
        this.stmts = stmts;
        this.hasJumps = new JumpableIRVisitor();
    }

    public Set<BasicBlock> getBlocks() {
        Set<BasicBlock> blocks = new HashSet<>();
        List<IRStmt> seq = new LinkedList<>();

        stmts.stream().forEach(s -> {
            if (s.accept(hasJumps)) {
                seq.add(s);
                blocks.add(new BasicBlock(seq));
                stmts.clear();
            } else if (s instanceof IRLabel) {
                blocks.add(new BasicBlock(seq));
                stmts.clear();
                stmts.add(s);
            } else {
                stmts.add(s);
            }
        });
        return blocks;
    }

}
