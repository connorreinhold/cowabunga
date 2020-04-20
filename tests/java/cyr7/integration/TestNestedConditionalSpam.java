package cyr7.integration;

public class TestNestedConditionalSpam extends TestProgram {

    @Override
    protected String filename() {
        return "nestedConditionalSpam";
    }

    @Override
    protected String expected() {
        return "75\n204\n0\n1\n255\n";
    }

}
