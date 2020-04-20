package cyr7.integration;

public class TestLotsOfRecursion extends TestProgram{
    @Override
    protected String filename() {
        return "lotsofrecursion";
    }

    @Override
    protected String expected() {
        return "abcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcd";
    }

    @Override
    protected Run.RunConfiguration configuration() {
        return new Run.RunConfiguration()
                .bigHeap(true);
    }
}
