package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

public class TestStairs extends TestProgram {

    @Override
    protected String filename() {
        return "climbingstairs";
    }

    @Override
    protected String expected() {
        return "1\n"
                + "8\n"
                + "13\n"
                + "0\n"
                + "89\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true);
    }

}
