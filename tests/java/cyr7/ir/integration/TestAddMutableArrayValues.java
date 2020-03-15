package cyr7.ir.integration;

public class TestAddMutableArrayValues extends TestProgram {

    @Override
    protected String filename() {
        return "addMutableArrayValues";
    }

    @Override
    protected String expected() {
        return "146\n" + "246\n";
    }
}
