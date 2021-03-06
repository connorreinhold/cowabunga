package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;

public class TestMultiDimArrSpeed extends TestProgram {

    @Override
    protected String filename() {
        return "multiDimArrSpeed";
    }

    @Override
    protected String expected() {
        return "10\n"
             + "10\n"
             + "10\n"
             + "10\n"
             + "10\n"
             + "10\n"
             + "10\n"
             + "10\n"
             + "10\n"
             + "10\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true);
    }
}
