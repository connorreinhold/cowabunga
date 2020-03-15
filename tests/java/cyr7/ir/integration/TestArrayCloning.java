package cyr7.ir.integration;

public class TestArrayCloning extends TestProgram {

    @Override
    protected String filename() {
        return "arrayCloning";
    }

    @Override
    protected String expected() {
        return "false\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n";
    }

}
