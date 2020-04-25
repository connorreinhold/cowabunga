package cyr7.integration;

public class TestLocalDecl13 extends TestProgram {
    @Override
    protected String filename() {
        return "localdecl13";
    }

    @Override
    protected String expected() {
        return "Index: 0\n" +
                "Index: 0\n" +
                "Index: 0\n" +
                "2\n" +
                "3\n" +
                "4\n" +
                "4\n" +
                "2\n" +
                "3\n";
    }
}
