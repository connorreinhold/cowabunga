package cyr7.integration;

public class TestArrAck08 extends TestProgram {
    @Override
    protected String filename() {
        return "arrack08";
    }

    @Override
    protected String expected() {
        return "Index: 4 2\n" +
                "Index: 4 2\n" +
                "8\n";
    }
}
