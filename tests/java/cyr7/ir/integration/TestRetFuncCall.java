package cyr7.ir.integration;

public class TestRetFuncCall extends TestProgram {

    @Override
    protected String filename() {
        return "retFuncCall";
    }

    @Override
    protected String expected() {
        return "getPair(): 4\n";
    }

}
