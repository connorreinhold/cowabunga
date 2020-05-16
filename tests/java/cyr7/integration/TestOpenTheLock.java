package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;
import org.junit.jupiter.api.Disabled;

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

    @Disabled
    @Override
    protected void testRegisterAllocator() throws Exception {
    }

}
