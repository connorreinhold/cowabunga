package cyr7.ir.integration;

public class TestReturnInts extends TestProgram {

    @Override
    protected String filename() {
        return "returnInts";
    }

    @Override
    protected String expected() {
        return "0\n";
    }
}