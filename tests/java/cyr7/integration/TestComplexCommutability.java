package cyr7.integration;

public class TestComplexCommutability extends TestProgram {

    @Override
    protected String filename() {
        return "complexCommutability";
    }

    @Override
    protected String expected() {
        return "550\n550\n";
    }

}
