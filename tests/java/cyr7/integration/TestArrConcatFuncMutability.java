package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestArrConcatFuncMutability extends TestProgram {

    @Override
    protected String filename() {
        return "arrConcatFuncMutability";
    }

    @Override
    protected String expected() {
        return "[2 3 ]";
    }
}
