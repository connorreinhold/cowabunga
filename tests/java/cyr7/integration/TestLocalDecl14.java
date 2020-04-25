package cyr7.integration;

public class TestLocalDecl14 extends TestProgram {
    @Override
    protected String filename() {
        return "localdecl14";
    }

    @Override
    protected String expected() {
        return "Index: 3\n" +
                "Index: 2\n" +
                "Index: 2\n" +
                "Index: 1\n" +
                "Index: 1\n" +
                "Index: 0\n" +
                "1\n" +
                "2\n" +
                "2\n" +
                "2\n" +
                "4\n" +
                "5\n" +
                "5";
    }
}
