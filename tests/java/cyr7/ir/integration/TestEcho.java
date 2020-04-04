package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

public class TestEcho extends TestProgram {
    @Override
    protected String filename() {
        return "echo";
    }

    @Override
    protected String expected() {
        return "asdlkfjasldfkjdbkljad\nHello world\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().args("Hello world");
    }
}
