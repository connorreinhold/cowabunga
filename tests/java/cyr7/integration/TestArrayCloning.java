package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestArrayCloning extends TestProgram {

    @Override
    protected String filename() {
        return "arrayCloning";
    }

    @Override
    protected String expected() {
        return "false\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n"
                + "true\n";
    }

}
