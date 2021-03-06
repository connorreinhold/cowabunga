package cyr7.integration;

public class TestStarTriangle extends TestProgram {
    @Override
    protected String filename() {
        return "startriangle";
    }

    @Override
    protected String expected() {
        return
            "\n" +
            "*\n" +
            "**\n" +
            "***\n" +
            "****\n" +
            "*****\n" +
            "******\n" +
            "*******\n" +
            "********\n" +
            "*********\n" +
            "***********\n" +
            "**********\n" +
            "*********\n" +
            "********\n" +
            "*******\n" +
            "******\n" +
            "*****\n" +
            "****\n" +
            "***\n" +
            "**\n" +
            "*\n";
    }
}
