package cyr7.ir.integration;

public class TestFunctionTypes extends TestProgram {
    @Override
    protected String filename() {
        return "functionTypes";
    }

    @Override
    protected String expected() {
        return "Called Create Values\n" +
                "5\n" +
                "true\n" +
                "20\n" +
                "40\n" +
                "Called Create Arrays\n" +
                "Hello World\n" +
                "Compilers\n" +
                "5\n" +
                "true\n" +
                "true\n" +
                "true\n" +
                "false\n" +
                "false\n" +
                "Called make Bool Array Of Ten\n" +
                "Called Create Arrays\n" +
                "true\n" +
                "true\n" +
                "true\n" +
                "true\n" +
                "true\n" +
                "false\n" +
                "false\n" +
                "false\n" +
                "false\n" +
                "false\n" +
                "10\n" +
                "true\n" +
                "true\n" +
                "false\n" +
                "false\n" +
                "true\n";
    }
}