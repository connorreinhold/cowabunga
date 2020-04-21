package cyr7.integration.brainf;

import cyr7.integration.Run.RunConfiguration;
import cyr7.integration.TestProgram;
import cyr7.ir.interpret.Configuration;

public class TestBrainfEcho extends TestProgram {

    @Override
    protected String filename() {
        return "brainf";
    }

    @Override
    protected String expected() {
        return "Hello, World!";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true).stdin("Hello, World!").args(",[.,]");
    }

}
