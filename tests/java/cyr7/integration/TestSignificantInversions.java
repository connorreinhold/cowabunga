package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;
import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestSignificantInversions extends TestProgram {
    
    public static String[] parseSigInversionsArgs(String input) {
        return input.split("\n");
    }
    
    @Override
    protected String filename() {
        return "significantinversions";
    }

    @Override
    protected String expected() {
        return "18\n" + 
            "77\n" + 
            "2304\n";
    }
    
    @Override
    protected RunConfiguration configuration() {
        String testVals =
            "4 7 8 6 4 6 7 3 10 2\n" + 
            "3 8 11 20 4 7 1 7 13 17 12 9 8 10 3 11 3 4 8 16\n" + 
            "30 3 23 59 70 68 94 57 12 43 30 74 22 20 85 38 99 25 16 71 14 27 92 81 57 74 63 71 97 82 6 26 85 28 37 6"
            + " 47 30 14 58 25 96 83 46 15 68 35 65 44 51 88 9 77 79 89 85 4 52 55 100 33 61 77 69 40 13 27 87 95 40 "
            + "96 71 35 79 68 2 98 3 18 93 53 57 2 81 87 42 66 90 45 20 41 30 32 18 98 72 82 76 10 28\n";
        return new RunConfiguration()
            .args(parseSigInversionsArgs(testVals))
            .bigHeap(true);
    }
}
