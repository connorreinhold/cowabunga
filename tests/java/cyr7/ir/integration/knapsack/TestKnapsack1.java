package cyr7.ir.integration.knapsack;

import cyr7.ir.integration.Run.RunConfiguration;
import cyr7.ir.integration.TestProgram;

public class TestKnapsack1 extends TestProgram {

    @Override
    protected String filename() {
        return "knapsack";
    }

    @Override
    protected String expected() {
        return "891\n" +
            "0\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().args(TestKnapsack0.parseArgs(
            "10 100\n" +
                "100 100\n" +
                "99 1\n" +
                "99 1\n" +
                "99 1\n" +
                "99 1\n" +
                "99 1\n" +
                "99 1\n" +
                "99 1\n" +
                "99 1\n" +
                "99 1\n"));
    }
}
