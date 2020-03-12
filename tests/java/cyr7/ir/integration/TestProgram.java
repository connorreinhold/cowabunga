package cyr7.ir.integration;

import cyr7.ir.IRUtil.Configuration;
import cyr7.ir.integration.Run.RunConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TestProgram {

    protected abstract String filename();

    protected abstract String expected();

    protected RunConfiguration configuration() {
        return new RunConfiguration();
    }

    @Test
    void testMir() throws Exception {
        String result = Run.mirRun(Run.getFile(filename()), configuration());
        assertEquals(expected(), result);
    }

    @Test
    void testLirNoOptimizations() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()), new Configuration(false, false), configuration());
        assertEquals(expected(), result);
    }

    @Test
    void testLirCfoldEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()), new Configuration(true, false), configuration());
        assertEquals(expected(), result);
    }

    @Test
    void testLirTraceEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()), new Configuration(false, true), configuration());
        assertEquals(expected(), result);
    }

    @Test
    void testLirAllEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()), new Configuration(true, true), configuration());
        assertEquals(expected(), result);
    }

}
