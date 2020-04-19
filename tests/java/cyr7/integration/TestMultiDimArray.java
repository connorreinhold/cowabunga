package cyr7.integration;

public class TestMultiDimArray extends TestProgram {

    @Override
    protected String filename() {
        return "multidimarray";
    }

    @Override
    protected String expected() {
        return
            "abcdeghijkmnopqstuvwyz{|}\n"
            + "[[3, 0, 2, 1][0, 2, 3, 1][0, 3, 1, 2][1, 3, 2, 0]]";
    }
}
