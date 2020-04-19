package cyr7.integration;

public class TestMatrixMultiplication extends TestProgram {

    @Override
    protected String filename() {
        return "matrixmultiplication";
    }

    @Override
    protected String expected() {
        return
            "[[30, 36, 42][66, 81, 96]]\n"
            + "[[10, 10, 2, 18, 18, 4][32, 32, 16, 48, 48, 20]]\n"
            + "[[1, 2, 3, 4][5, 6, 7, 8][9, 10, 11, 12][13, 14, 15, 16]]";
    }
}
