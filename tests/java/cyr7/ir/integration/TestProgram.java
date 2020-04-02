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
import java.util.List;
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

    private String executeCommand(
            String... command)
            throws InterruptedException, IOException {
        System.out.println(Arrays.toString(command));
        ProcessBuilder process = new ProcessBuilder(command);
        process.directory(new File("."));
        Process runner = process.start();
        InputStream resultStream = new BufferedInputStream(
                runner.getInputStream());

        runner.waitFor();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(resultStream));

        List<String> output = reader.lines().collect(Collectors.toList());
        System.out.println("From executeCommand: " + output);

        return String.join("\n", output);
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

        this.executeCommand("./xic", "-libpath", this.libpath(),
                this.getTestXiFilename());


        File tmpFile = File.createTempFile("temp_" + filename(), "");
        tmpFile.setExecutable(true);
        tmpFile.setReadable(true);
        tmpFile.setWritable(true);
        tmpFile.deleteOnExit();
        this.executeCommand(linkerFile.getAbsolutePath(),
                this.getTestAssemblyFilename(), "-o",
                tmpFile.getAbsolutePath());


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

        String result = this.executeCommand(command);

        System.out.println(expected());
        System.out.println("-------------------------");
        System.out.println(result);
        assertEquals(expected(), result);
    }

}
