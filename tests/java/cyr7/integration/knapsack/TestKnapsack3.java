package cyr7.integration.knapsack;

import cyr7.integration.Run.RunConfiguration;
import cyr7.integration.TestProgram;

public class TestKnapsack3 extends TestProgram {
    @Override
    protected String filename() {
        return "knapsack";
    }

    @Override
    protected String expected() {
        return "1348\n" +
            "0\n" +
            "0\n" +
            "1\n" +
            "0\n" +
            "0\n" +
            "0\n" +
            "1\n" +
            "0\n" +
            "1\n" +
            "0\n" +
            "1\n" +
            "1\n" +
            "0\n" +
            "0\n" +
            "1\n" +
            "0\n" +
            "1\n" +
            "0\n" +
            "0\n" +
            "0\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true).args(TestKnapsack0.parseArgs(
            "20 1450\n" +
                "688 672\n" +
                "1056 1160\n" +
                "86 84\n" +
                "1320 1450\n" +
                "396 435\n" +
                "860 840\n" +
                "132 145\n" +
                "1056 1160\n" +
                "86 84\n" +
                "1188 1305\n" +
                "344 336\n" +
                "264 290\n" +
                "792 870\n" +
                "1320 1450\n" +
                "172 168\n" +
                "516 504\n" +
                "264 290\n" +
                "792 870\n" +
                "924 1015\n" +
                "1320 1450\n"));
    }
}
