package cyr7.ir.integration;

public class TestArrConcatFuncMutability extends TestProgram {

    @Override
    protected String filename() {
        return "arrConcatFuncMutability";
    }

    @Override
    protected String expected() {
        return "[2 3 ]";
    }
}
