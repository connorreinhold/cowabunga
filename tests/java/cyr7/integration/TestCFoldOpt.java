package cyr7.integration;

public class TestCFoldOpt extends TestProgram {
    @Override
    protected String filename() {
        return "cfoldOpt";
    }

    @Override
    protected String expected() {
        return "-10240\n";
    }
}
