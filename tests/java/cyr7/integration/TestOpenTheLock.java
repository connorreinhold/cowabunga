package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;

public class TestOpenTheLock extends TestProgram {

    @Override
    protected String filename() {
        return "openthelock";
    }

    @Override
    protected String expected() {
        return
            "-1\n4\n3\n7\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true);
    }
}
