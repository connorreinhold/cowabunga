package cyr7.integration;

public class TestIntegralEstimation extends TestProgram {
    @Override
    protected String filename() {
        return "integralEstimation";
    }

    @Override
    protected String expected() {
        return "317\n" + 
                "160\n" + 
                "155\n" + 
                "140\n";
    }
}
