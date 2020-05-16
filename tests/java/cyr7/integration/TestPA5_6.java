package cyr7.integration;

public class TestPA5_6 extends TestProgram {

    @Override
    protected String filename() {
        return "group_of_anonymous08__trivialtest";
    }

    @Override
    protected String expected() {
        return "-1\n" +
                "-9223372036854775808\n";
    }
}
