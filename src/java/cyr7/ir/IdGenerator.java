package cyr7.ir;

public final class IdGenerator {

    private int labelCounter;
    private int tempCounter;

    public IdGenerator() {
        this.labelCounter = 0;
        this.tempCounter = 0;
    }

    public String newLabel() {
        return String.format("_l%d", (labelCounter++));
    }

    public String newTemp() {
        return String.format("_t%d", (tempCounter++));
    }

}
