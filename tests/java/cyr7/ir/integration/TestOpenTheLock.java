package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

public class TestOpenTheLock extends TestProgram {

    @Override
    protected String filename() {
        return "openthelock";
    }

    @Override
    protected String expected() {
        return
            "-1\n4\n3\n8\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true);
    }
}
