package cyr7.integration;

public class TestEmpty extends TestProgram {
    @Override
    protected String filename() {
        return "empty";
    }

    @Override
    protected String expected() {
        return "";
    }
}
