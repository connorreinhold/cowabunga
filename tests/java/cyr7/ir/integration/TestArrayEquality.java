package cyr7.ir.integration;

public class TestArrayEquality extends TestProgram {

    @Override
    protected String filename() {
        return "arrayEquality";
    }

    @Override
    protected String expected() {
        return "false\n" + "true\n" + "true\n" + "false\n" + "false\n";
    }

}
