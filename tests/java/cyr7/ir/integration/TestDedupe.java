package cyr7.ir.integration;

public class TestDedupe extends TestProgram {
    @Override
    protected String filename() {
        return "arrayRemoveDup";
    }

    @Override
    protected String expected() {
        return "42\n" + "2\n" + "4\n" + "3\n" + "5\n" + "7\n";
    }

}
