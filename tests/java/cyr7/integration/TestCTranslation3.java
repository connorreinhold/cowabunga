package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestCTranslation3 extends TestProgram {
    @Override
    protected String filename() {
        return "ctranslation3";
    }

    @Override
    protected String expected() {
        return "";
    }
}
