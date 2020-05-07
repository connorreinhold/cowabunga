package cyr7.ir.ctranslation;

import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IdGenerator;

public final class LookaheadIdGenerator implements IdGenerator {

    private int labelCounter;
    private int tempCounter;

    public LookaheadIdGenerator() {
        this.labelCounter = 0;
        this.tempCounter = 0;
    }

    private static String label(int num) {
        return String.format("_l%d", num);
    }

    private static String temp(int num) {
        return String.format("_t%d", num);
    }

    @Override
    public String newLabel() {
        return label(labelCounter++);
    }

    @Override
    public String newTemp() {
        return temp(tempCounter++);
    }

    @Override
    public String newTemp(String description) {
        return String.format("_t_%s_%d", description, tempCounter++);
    }

    /**
     * Peek the next label with some offset.
     *
     * peekLabel(0) will return the next label. peekLabel(1) will return the
     * label after that, and so on.
     *
     * @param offset
     * @return
     */
    public String peekLabel(int offset) {
        return label(labelCounter + offset);
    }

    /**
     * Peek the next temp with some offset.
     *
     * peekTemp(0) will return the next temp. peekTemp(1) will return the temp
     * after that, and so on.
     *
     * @param offset
     * @return
     */
    public String peekTemp(int offset) {
        return temp(tempCounter + offset);
    }

    @Override
    public String retTemp(int num) {
        return new DefaultIdGenerator().retTemp(num);
    }

    @Override
    public String argTemp(int num) {
        return new DefaultIdGenerator().argTemp(num);
    }

}
