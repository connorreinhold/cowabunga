package cyr7.ir.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.integration.Run.RunConfiguration;

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
        String result = Run.lirRun(Run.getFile(filename()), new LowerConfiguration(false, false), configuration());
        assertEquals(expected(), result);
    }

    @Test
    void testLirCfoldEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()), new LowerConfiguration(true, false), configuration());
        assertEquals(expected(), result);
    }

    @Test
    void testLirTraceEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()), new LowerConfiguration(false, true), configuration());
        assertEquals(expected(), result);
    }

    @Test
    void testLirAllEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()), new LowerConfiguration(true, true), configuration());
        assertEquals(expected(), result);
    }


    private final URL linkerFilename = Run.class.getClassLoader()
            .getResource("x86/runtime/linkxi.sh");

    private String getTestAssemblyFilename() {
        return "tests/resources/irgen/" + filename() + ".s";
    }

    private String getTestXiFilename() {
        return "tests/resources/irgen/" + filename() + ".xi";
    }

    private String libpath() {
        return "tests/resources/irgen/lib";
    }

    @Test
    void runAssemblyTest()
            throws IOException, URISyntaxException, InterruptedException {

        if (!System.getProperty("os.name").equals("Linux")) {
            System.out.println("The operating system is not Linux,\n"
                    + "so this test case will not be performed.\n"
                    + "To run this test, please use the CS 4120 VM.");
            return;
        }
        if (Objects.isNull(linkerFilename)) {
            System.out.println("Cannot find linker resource in resources/x86/");
            return;
        }

        File linkerFile = new File(linkerFilename.getPath());
        if (!linkerFile.exists()) {
            System.out.println("Cannot find linker file in resources/x86/");
            return;
        }
        if (!linkerFile.canExecute()) {
            System.out.println("Cannot execute the linker in resources/x86/");
            return;
        }

        ProcessBuilder compileFile = new ProcessBuilder("./xic", "-libpath",
                this.libpath(), this.getTestXiFilename());
        compileFile.directory(new File("."));
        Process compileProcess = compileFile.start();
        compileProcess.waitFor();

        File tmpFile = File.createTempFile("temp_" + filename(), "");
        tmpFile.setExecutable(true);
        tmpFile.setReadable(true);
        tmpFile.setWritable(true);
        tmpFile.deleteOnExit();

        ProcessBuilder pb = new ProcessBuilder(
                linkerFile.getAbsoluteFile().toString(),
                this.getTestAssemblyFilename(), "-o",
                tmpFile.getAbsoluteFile().toString());

        pb.directory(new File("."));
        Process p = pb.start();
        p.waitFor();

        long[][] longArgs = this.configuration().args;
        String[] args = new String[longArgs.length];

        for (int i = 0; i < longArgs.length; i++) {
            long[] longRep = longArgs[i];
            char[] charRep = new char[longRep.length];

            for (int j = 0; j < charRep.length; j++) {
                charRep[j] = (char) longRep[j];
            }
            args[i] = String.valueOf(charRep);
        }

        String[] command = new String[1 + args.length];
        command[0] = tmpFile.getAbsolutePath().toString();
        for (int i = 0; i < args.length; i++) {
            command[i + 1] = args[i];
        }

        System.out.println(Arrays.toString(command));
        ProcessBuilder loadASM = new ProcessBuilder(command);
        loadASM.directory(new File("."));
        Process runASM = loadASM.start();
        InputStream resultStream = new BufferedInputStream(
                runASM.getInputStream());

        runASM.waitFor();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(resultStream));

        String result = String.join("\n",
                reader.lines().collect(Collectors.toList()));
        assertEquals(expected(), result);

        reader.close();
        resultStream.close();
    }

}
