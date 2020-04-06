package cyr7.ir.integration;

public class TestFibonacci extends TestProgram {
    @Override
    protected String filename() {
        return "fibonacci";
    }

    @Override
    protected String expected() {
        return "5";
    }
}
