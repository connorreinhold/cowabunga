package cyr7.ir.integration;

public class TestOpenTheLock extends TestProgram {

    @Override
    protected String filename() {
        return "openthelock";
    }

    @Override
    protected String expected() {
        return
            "-1\n4\n3\n";
    }
}
