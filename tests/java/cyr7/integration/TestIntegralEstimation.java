package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
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
