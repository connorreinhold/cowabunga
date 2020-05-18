package cyr7.integration;

import cyr7.Bash;
import cyr7.cli.OptConfig;
import cyr7.cli.Optimization;
import cyr7.integration.Run.RunConfiguration;
import cyr7.ir.interpret.Configuration;
import cyr7.x86.ASMUtil.TilerConf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        String result = Run.lirRun(Run.getFile(filename()),
            OptConfig.none(),
            configuration());
        assertEquals(expected(), result);
    }

    @Test
    void testLirCfoldEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()),
            OptConfig.cfOnly(),
            configuration());
        assertEquals(expected(), result);
    }

    @Test
    void testLirAllEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()),
            OptConfig.allEnabled(),
            configuration());
        assertEquals(expected(), result);
    }

    private String getTestAssemblyFilename() {
        return "tests/resources/integration/" + filename() + ".s";
    }

    private String getTestXiFilename() {
        return "tests/resources/integration/" + filename() + ".xi";
    }


    private void runAssemblyTest(
        TilerConf tilerConf,
        OptConfig optConfig) throws Exception {

        Bash.compileToAssembly(getTestXiFilename(), tilerConf, optConfig);
        File exe = Bash.linkExecutable(getTestAssemblyFilename());
        RunConfiguration config = configuration();
        String result = Bash.executeFile(exe, filename(), config.stdin, config.args);

        System.out.println();
        System.out.println(expected());
        System.out.println("-------------------------");
        System.out.println(result);
        assertEquals(expected(), result);
    }

    @EnabledOnOs({OS.LINUX})
    @Test
    void testBasicTilerAssembly() throws Exception {
        runAssemblyTest(TilerConf.BASIC, OptConfig.none());
    }

    @EnabledOnOs({OS.LINUX})
    @Test
    void testComplexTilerAssembly() throws Exception {
        runAssemblyTest(TilerConf.COMPLEX, OptConfig.none());
    }

    @Timeout(value=60) // seconds
    @EnabledOnOs({OS.LINUX})
    @Test
    protected void testRegisterAllocator() throws Exception {
        runAssemblyTest(TilerConf.COMPLEX, OptConfig.of(Optimization.REG));
    }

    @Timeout(value=60) // seconds
    @EnabledOnOs({OS.LINUX})
    @Test
    protected void testRegisterAllocatorWithOptimizations() throws Exception {
        runAssemblyTest(TilerConf.COMPLEX, OptConfig.allEnabled());
    }

}
