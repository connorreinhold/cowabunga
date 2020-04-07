package cyr7.ir.integration.galeshapley;

import cyr7.ir.integration.Run.RunConfiguration;
import cyr7.ir.integration.TestProgram;

import static cyr7.ir.integration.galeshapley.TestGaleShapley0.parseGaleShapleyArgs;

public class TestGaleShapley1 extends TestProgram {
    @Override
    protected String filename() {
        return "galeshapley";
    }

    @Override
    protected String expected() {
        return "[5, 3, 2, 1, 0, 4]";
    }

    @Override
    protected RunConfiguration configuration() {
        String test1 =
            "4 5 1 0 2 3\n" +
            "4 1 2 3 5 0\n" +
            "3 2 5 4 1 0\n" +
            "1 2 4 0 5 3\n" +
            "5 1 4 3 2 0\n" +
            "2 4 1 0 3 5\n" +
            "2 1 5 0 3 4\n" +
            "3 2 4 1 5 0\n" +
            "2 0 3 4 5 1\n" +
            "1 2 5 4 3 0\n" +
            "0 5 1 3 4 2\n" +
            "3 2 4 1 0 5\n";
        return new RunConfiguration()
            .args(parseGaleShapleyArgs(test1))
            .bigHeap(false);
    }
}
