package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

public class TestEcho extends TestProgram {
    @Override
    protected String filename() {
        return "echo";
    }

    @Override
    protected String expected() {
        return "Hello world\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().args(new long[][]{
            new long[]{ 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100 }
        });
    }
}
