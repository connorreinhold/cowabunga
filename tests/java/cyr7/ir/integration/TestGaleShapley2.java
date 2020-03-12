package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

import static cyr7.ir.integration.TestGaleShapley0.parseGaleShapleyArgs;

public class TestGaleShapley2 extends TestProgram {
    @Override
    protected String filename() {
        return "galeshapley";
    }

    @Override
    protected String expected() {
        return "[4, 1, 3, 6, 5, 9, 0, 8, 7, 2]";
    }

    @Override
    protected RunConfiguration configuration() {
        String test2 = "10\n" +
            "6 5 1 8 2 3 7 0 4 9\n" +
            "0 8 1 3 4 6 5 2 9 7\n" +
            "9 5 6 1 3 7 8 0 2 4\n" +
            "4 2 0 7 8 6 9 1 3 5\n" +
            "9 0 2 4 1 6 8 7 5 3\n" +
            "4 9 7 2 3 5 8 1 6 0\n" +
            "6 3 9 0 2 1 5 4 8 7\n" +
            "3 0 6 8 1 5 4 9 2 7\n" +
            "7 4 0 9 1 2 3 6 5 8\n" +
            "4 1 6 8 5 0 7 9 2 3\n" +
            "9 2 4 7 3 0 8 6 5 1\n" +
            "0 7 6 4 2 1 5 9 8 3\n" +
            "7 6 0 9 1 3 4 5 8 2\n" +
            "6 8 1 2 7 3 0 5 9 4\n" +
            "0 5 7 3 2 9 6 8 4 1\n" +
            "5 4 9 3 1 7 2 6 8 0\n" +
            "0 8 1 2 6 9 5 3 4 7\n" +
            "5 8 0 7 9 2 4 6 3 1\n" +
            "6 0 3 7 1 2 8 4 9 5\n" +
            "8 2 9 6 7 4 1 5 0 3\n";
        return new RunConfiguration()
            .args(parseGaleShapleyArgs(test2))
            .bigHeap(false);
    }
}
