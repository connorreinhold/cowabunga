package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestArrayAlloc extends TestProgram {

    @Override
    protected String filename() {
        return "arrayalloc";
    }

    @Override
    protected String expected() {
        return "";
    }
}
