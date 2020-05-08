package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestComplexCommutability extends TestProgram {

    @Override
    protected String filename() {
        return "complexCommutability";
    }

    @Override
    protected String expected() {
        return "550\n550\n";
    }

}
