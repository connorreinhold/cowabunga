package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;
import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestRomanToInt extends TestProgram {

    @Override
    protected String filename() {
        return "romanToInt";
    }

    @Override
    protected String expected() {
        return "1994\n" + "4\n" + "5\n" + "3\n";
    }

    @Override
    protected RunConfiguration configuration() {
       return new RunConfiguration().args(
            "MCMXCIV",
            "IV",
            "V",
            "III"
        );
    }
}
