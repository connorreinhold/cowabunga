package cyr7.ir.integration.knapsack;

import cyr7.ir.integration.Run.RunConfiguration;
import cyr7.ir.integration.TestProgram;

public class TestKnapsack0 extends TestProgram {

    public static String[] parseArgs(String args) {
        return args.split("\n");
    }

    @Override
    protected String filename() {
        return "knapsack";
    }

    @Override
    protected String expected() {
        return "928\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "0\n" +
            "1\n" +
            "1\n" +
            "0\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "1\n" +
            "0\n" +
            "0\n" +
            "1\n" +
            "0\n" +
            "1\n" +
            "0\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().args(parseArgs(
            "20 988\n" +
                "44 44\n" +
                "88 88\n" +
                "55 55\n" +
                "11 11\n" +
                "540 590\n" +
                "77 77\n" +
                "54 59\n" +
                "324 354\n" +
                "162 177\n" +
                "54 59\n" +
                "66 66\n" +
                "44 44\n" +
                "33 33\n" +
                "108 118\n" +
                "432 472\n" +
                "540 590\n" +
                "66 66\n" +
                "162 177\n" +
                "66 66\n" +
                "270 295\n"));
    }
}
