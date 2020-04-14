package cyr7.ir.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cyr7.x86.ASMUtil.TilerConf;
import org.junit.jupiter.api.Test;

import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.integration.Run.RunConfiguration;

public abstract class TestProgram {

    protected abstract String filename();

    protected abstract String expected();

    protected RunConfiguration configuration() {
        return new RunConfiguration();
    }

    //@Test
    void testMir() throws Exception {
        String result = Run.mirRun(Run.getFile(filename()), configuration());
        assertEquals(expected(), result);
    }

    //@Test
    void testLirNoOptimizations() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()),
                new LowerConfiguration(false, false),
                configuration());
        assertEquals(expected(), result);
    }

    //@Test
    void testLirCfoldEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()),
                new LowerConfiguration(true, false),
                configuration());
        assertEquals(expected(), result);
    }

    //@Test
    void testLirTraceEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()),
                new LowerConfiguration(false, true),
                configuration());
        assertEquals(expected(), result);
    }

    //@Test
    void testLirAllEnabled() throws Exception {
        String result = Run.lirRun(Run.getFile(filename()),
                new LowerConfiguration(true, true),
                configuration());
        assertEquals(expected(), result);
    }

    private final String linkerFilename = "/home/vagrant/runtime/linkxi.sh";

    private String getTestAssemblyFilename() {
        return "tests/resources/irgen/" + filename() + ".s";
    }

    private String getTestXiFilename() {
        return "tests/resources/irgen/" + filename() + ".xi";
    }

    private String libpath() {
        return "tests/resources/irgen/lib";
    }

    private String executeCommand(boolean wantsResult, String... command)
            throws InterruptedException, IOException {
        System.out.println("Executing command: "+Arrays.toString(command));
        ProcessBuilder process = new ProcessBuilder(command);
        process.directory(new File("."));
        var redirectedTarget = File.createTempFile("redirect", null);
        redirectedTarget.setExecutable(true);
        redirectedTarget.setReadable(true);
        redirectedTarget.setWritable(true);

        process.redirectOutput(redirectedTarget);
        Process runner = process.start();
        runner.waitFor();
        if (wantsResult) {
            InputStream resultStream = new BufferedInputStream(
                    new FileInputStream(redirectedTarget));
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    resultStream));
            List<String> output = reader.lines()
                                        .collect(Collectors.toList());

            var builder = new StringBuilder(String.join("\n", output));
            if (this.newLineExists(redirectedTarget)) {
                builder.append('\n');
            }
            reader.close();
            resultStream.close();
            redirectedTarget.delete();
            return builder.toString();
        } else {
            redirectedTarget.delete();
            return "";
        }
    }

    private void runAssemblyTest(String tilerName) throws Exception {
        System.out.println("Seinding.");
        if (!System.getProperty("os.name")
            .equals("Linux")) {
            System.out.println("The operating system is not Linux,\n"
                + "so this test case will not be performed.\n"
                + "To run this test, please use the CS 4120 VM.");
            return;
        }
        if (Objects.isNull(linkerFilename)) {
            System.out.println("Cannot find linker resource in ~/runtime");
            return;
        }

        File linkerFile = new File(linkerFilename);
        if (!linkerFile.exists()) {
            System.out.println("Cannot find linker file in ~/runtime");
            return;
        }
        if (!linkerFile.canExecute()) {
            System.out.println("Cannot execute the linker in ~/runtime");
            return;
        }

        System.out.println(this.executeCommand(true,
            "./xic",
            "-libpath",
            this.libpath(),
            "-tiler",
            tilerName,
            this.getTestXiFilename()));

        File tmpFile = File.createTempFile("temp_" + filename(), "");
        tmpFile.setExecutable(true);
        tmpFile.setReadable(true);
        tmpFile.setWritable(true);
        tmpFile.deleteOnExit();
        System.out.println(this.executeCommand(true,
            linkerFile.getAbsolutePath(),
            this.getTestAssemblyFilename(),
            "-o",
            tmpFile.getAbsolutePath()));

        String[] args = this.configuration().args;
        String[] command = new String[1 + args.length];
        command[0] = tmpFile.getAbsolutePath();
        for (int i = 0; i < args.length; i++) {
            command[i + 1] = args[i];
        }

        String result = this.executeCommand(true, command);

        System.out.println(expected());
        System.out.println("-------------------------");
        System.out.println(result);
        assertEquals(expected(), result);
    }

    //@Test
    void runBasicTilerAssemblyTest() throws Exception {
        runAssemblyTest("basic");
    }

    @Test
    void runComplexTilerAssemblyTest() throws Exception {
        runAssemblyTest("complex");
    }

    // Code stolen from StackOverflow.
    // https://stackoverflow.com/questions/28795440/check-if-a-new-line-exists-at-end-of-file
    private boolean newLineExists(File file) throws IOException {
        RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
        long fileLength = fileHandler.length() - 1;
        if (fileLength < 0) {
            fileHandler.close();
            return false;
        }
        fileHandler.seek(fileLength);
        byte readByte = fileHandler.readByte();
        fileHandler.close();

        if (readByte == 0xA || readByte == 0xD) {
            return true;
        }
        return false;
    }

}
