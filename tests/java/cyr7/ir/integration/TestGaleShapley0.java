package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

import java.util.Scanner;

public class TestGaleShapley0 extends TestProgram {

    public static long[][] parseGaleShapleyArgs(String input) {
        Scanner scanner = new Scanner(input);
        int n = scanner.nextInt();
        scanner.nextLine();
        long[][] args = new long[2 * n][n];
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < n; j++) {
                args[i][j] = scanner.nextInt();
            }
            scanner.nextLine();
        }
        return args;
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
        String test0 = "4\n" +
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
