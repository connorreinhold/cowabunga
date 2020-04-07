package cyr7.ir.integration.galeshapley;

import java.util.Scanner;

import cyr7.ir.integration.Run.RunConfiguration;
import cyr7.ir.integration.TestProgram;

public class TestGaleShapley0 extends TestProgram {

    public static String[] parseGaleShapleyArgs(String input) {
        return input.split("\n");
    }


    @Override
    protected String filename() {
        return "galeshapley";
    }

    @Override
    protected String expected() {
        return "[3, 0, 2, 1]";
    }

    @Override
    protected RunConfiguration configuration() {
        String test0 =
            "1 3 0 2\n" +
            "2 1 3 0\n" +
            "2 3 0 1\n" +
            "2 0 1 3\n" +
            "1 3 2 0\n" +
            "0 3 1 2\n" +
            "0 2 3 1\n" +
            "3 0 2 1\n";
        return new RunConfiguration()
            .args(parseGaleShapleyArgs(test0))
            .bigHeap(false);
    }
}
