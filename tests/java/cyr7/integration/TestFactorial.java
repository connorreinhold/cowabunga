package cyr7.integration;

public class TestFactorial extends TestProgram {
    @Override
    protected String filename() {
        return "factorial";
    }

    @Override
    protected String expected() {
        return "Factorial 10 is: 3628800";
    }
}
