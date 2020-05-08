package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestCTranslation2 extends TestProgram {
    @Override
    protected String filename() {
        return "ctranslation2";
    }

    @Override
    protected String expected() {
        return "";
    }
}
