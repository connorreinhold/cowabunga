package cyr7.ir.integration.knapsack;

import cyr7.ir.integration.Run.RunConfiguration;
import cyr7.ir.integration.TestProgram;

public class TestKnapsack5 extends TestProgram {
    @Override
    protected String filename() {
        return "knapsack";
    }

    @Override
    protected String expected() {
        return "10\n" +
            "1\n" +
            "0\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().args(TestKnapsack0.parseArgs(
            "2 10\n" +
                "10 10\n" +
                "2 1\n"));
    }
}
