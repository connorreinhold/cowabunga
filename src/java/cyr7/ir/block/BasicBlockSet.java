package cyr7.ir.block;

import java.util.HashSet;

public class BasicBlockSet extends HashSet<BasicBlock> {

    /**
     *
     */
    private static final long serialVersionUID = 2517117879175802778L;

    public BasicBlockSet() {
        super();
    }

    public void unmarkBlocks() {
        this.forEach(b -> {
            b.unmark();
        });
    }

    @Override
    public boolean add(BasicBlock b) {
        boolean isNewElement = super.add(b);
        if (!isNewElement) {
            throw new UnsupportedOperationException();
        }
        return isNewElement;
    }


}
