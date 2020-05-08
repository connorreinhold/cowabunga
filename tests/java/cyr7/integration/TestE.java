package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestE extends TestProgram {

    @Override
    protected String filename() {
        return "e";
    }

    @Override
    protected String expected() {
        return "271828";
    }

}
