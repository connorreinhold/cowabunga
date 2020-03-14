package cyr7.ir.integration;

public class TestArithmetic extends TestProgram {
    @Override
    protected String filename() {
        return "arithmetic";
    }

    @Override
    protected String expected() {
        return "16\n"
            + "500\n";
    }
}
