package cyr7.integration;

import org.junit.jupiter.api.Disabled;

public class TestNestedConditionalSpam extends TestProgram {

    @Override
    protected String filename() {
        return "nestedConditionalSpam";
    }

    @Override
    protected String expected() {
        return "75\n204\n0\n1\n255\n";
    }

    @Disabled
    @Override
    protected void testRegisterAllocator() throws Exception {
    }

}
