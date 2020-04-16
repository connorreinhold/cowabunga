package cyr7.ir;

import cyr7.ir.interpret.Configuration;

public final class DefaultIdGenerator implements IdGenerator {

    private int labelCounter;
    private int tempCounter;

    public DefaultIdGenerator() {
        this.labelCounter = 0;
        this.tempCounter = 0;
    }

    @Override
    public String newLabel() {
        return String.format("_l%d", (labelCounter++));
    }

    @Override
    public String newTemp() {
        System.out.println(tempCounter);
        new Exception().printStackTrace();
        return String.format("_t%d", (tempCounter++));
    }

    @Override
    public String retTemp(int number) {
        return Configuration.ABSTRACT_RET_PREFIX + number;
    }

    @Override
    public String argTemp(int number) {
        return Configuration.ABSTRACT_ARG_PREFIX + number;
    }

}
