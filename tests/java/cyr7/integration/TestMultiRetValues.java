package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestMultiRetValues extends TestProgram {

    @Override
    protected String filename() {
        return "multiRetValues";
    }

    @Override
    protected String expected() {
        return "(3, 4)\n" +
            "(4, 5, true)\n" +
            "---\n" +
            "(4, )\n" +
            "(1, 2, )\n" +
            "(3, )\n" +
            "(4, )\n" +
            "(1, 2, )\n" +
            "(3, )\n" +
            "(4, )\n" +
            "(1, 2, 3, )\n" +
            "(3, 4, )\n" +
            "(1, 2, 4, )\n" +
            "---\n" +
            "(7, )\n" +
            "(1, 5, )\n" +
            "(6, )\n" +
            "(7, )\n" +
            "(1, 5, )\n" +
            "(6, )\n" +
            "(7, )\n" +
            "(1, 2, 3, )\n" +
            "(3, 4, )\n" +
            "(1, 2, 4, )\n" +
            "---\n" +
            "1\n" +
            "---\n" +
            "(0, 5, 1, )\n";
    }
}
