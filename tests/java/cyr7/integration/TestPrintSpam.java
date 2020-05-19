package cyr7.integration;

import org.junit.jupiter.api.Disabled;

public class TestPrintSpam extends TestProgram {

    @Override
    protected String filename() {
        return "printSpam";
    }

    @Override
    protected String expected() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1_000; i++) {
            sb.append("Hello World!").append("\n");
        }
        return sb.toString();
    }

    @Disabled
    @Override
    void testMir() throws Exception {
        super.testMir();
    }

    @Disabled
    @Override
    void testLirNoOptimizations() throws Exception {
        super.testLirNoOptimizations();
    }

    @Disabled
    @Override
    void testLirCfoldEnabled() throws Exception {
        super.testLirCfoldEnabled();
    }

    @Disabled
    @Override
    void testLirAllEnabled() throws Exception {
        super.testLirAllEnabled();
    }

    @Disabled
    @Override
    protected void testRegisterAllocator() {
    }

    @Disabled
    @Override
    protected void testRegisterAllocatorAllOptimizations() {
    }

    @Disabled
    @Override
    protected void testRegisterAllocatorConstantFolding() throws Exception {
        super.testRegisterAllocatorConstantFolding();
    }

    @Disabled
    @Override
    protected void testRegisterAllocatorCopyAndDCE() throws Exception {
        super.testRegisterAllocatorCopyAndDCE();
    }

    @Disabled
    @Override
    protected void testRegisterAllocatorLoopUnrolling() throws Exception {
        super.testRegisterAllocatorLoopUnrolling();
    }

}
