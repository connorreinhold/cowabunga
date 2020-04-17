package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;

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
        return new RunConfiguration().args("Hello world");
    }
}
