package cyr7.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import cyr7.cli.OptConfig;
import cyr7.cli.Optimization;
import cyr7.integration.Run.RunConfiguration;
import cyr7.x86.ASMUtil.TilerConf;

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

    private final String linkerFilename = "/home/vagrant/runtime/linkxi.sh";

    private String getTestAssemblyFilename(TilerConf tilerConf) {
        return "tests/resources/integration/" + filename() + ".s" + "_" + tilerConf.name();
    }

    private String getTestAssemblyFilename() {
        return "tests/resources/integration/" + filename() + ".s";
    }

    private String getTestXiFilename() {
        return "tests/resources/integration/" + filename() + ".xi";
    }

    private String libpath() {
        return "tests/resources/integration/lib";
    }

    private String executeCommand(
        boolean throwOnError,
        Optional<File> stdin,
        String... command)
        throws InterruptedException, IOException {

        System.out.print(String.join(" ", command));
        ProcessBuilder process = new ProcessBuilder(command);
        process.directory(new File("."));
        var redirectedTarget = File.createTempFile("redirect", null);
        var redirectedError = File.createTempFile("redirectError", null);
        assert redirectedTarget.setExecutable(true);
        assert redirectedTarget.setReadable(true);
        assert redirectedTarget.setWritable(true);

        stdin.ifPresent(process::redirectInput);

        process.redirectOutput(redirectedTarget);
        process.redirectError(redirectedError);
        Process runner = process.start();
        runner.waitFor();
        InputStream resultStream = new BufferedInputStream(
                new FileInputStream(redirectedTarget));
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                resultStream));
        List<String> output = reader
            .lines().collect(Collectors.toList());

        resultStream = new BufferedInputStream(
            new FileInputStream(redirectedError));
        reader = new BufferedReader(new InputStreamReader(
            resultStream));
        output.addAll(
            reader
            .lines()
            .map(x -> "ERROR: " + x)
            .collect(Collectors.toList()));

        var builder = new StringBuilder(String.join("\n", output));
        if (this.newLineExists(redirectedTarget)) {
            builder.append('\n');
        }
        reader.close();
        resultStream.close();
        assert redirectedTarget.delete();

        if (throwOnError && runner.exitValue() != 0) {
            throw new RuntimeException(
                "\nUnable to execute command \""
                    + String.join(" ", command)
                    + "\": \n" + builder.toString());
        }

        return builder.toString();
    }

    private void runAssemblyTest(
        TilerConf tilerConf,
        OptConfig optConfig) throws Exception {

        File linkerFile = new File(linkerFilename);
        if (!linkerFile.exists()) {
            System.out.println("Cannot find linker file in ~/runtime");
            return;
        }
        if (!linkerFile.canExecute()) {
            System.out.println("Cannot execute the linker in ~/runtime");
            return;
        }

        List<String> arguments = new ArrayList<>(List.of(
            "./xic",
            "-libpath",
            this.libpath(),
            "-tiler",
            tilerConf.name()));
        arguments.addAll(optConfig.convertToCLI());
        arguments.add(this.getTestXiFilename());
        System.out.println(this.executeCommand(
            true,
            Optional.empty(),
            arguments.toArray(new String[0])));

        File tmpFile = File.createTempFile("temp_" + filename(), "");
        tmpFile.setExecutable(true);
        tmpFile.setReadable(true);
        tmpFile.setWritable(true);
        tmpFile.deleteOnExit();
        System.out.println(this.executeCommand(
            true,
            Optional.empty(),
            linkerFile.getAbsolutePath(),
            this.getTestAssemblyFilename(),
            "-o",
            tmpFile.getAbsolutePath()));

        Optional<File> stdinFile;
        RunConfiguration configuration = configuration();
            if (!configuration.stdin.isEmpty()) {
                File stdinFileReal = File.createTempFile("temp_stdin_" + filename(), "");
                stdinFileReal.setReadable(true);
                stdinFileReal.setWritable(true);
                tmpFile.deleteOnExit();

                stdinFile = Optional.of(stdinFileReal);

                FileWriter writer = new FileWriter(stdinFileReal);
            writer.write(configuration.stdin);
            writer.flush();
            writer.close();
        } else {
            stdinFile = Optional.empty();
        }

        String[] args = configuration.args;
        String[] command = new String[1 + args.length];
        command[0] = tmpFile.getAbsolutePath();
        System.arraycopy(args, 0, command, 1, args.length);

        String result = this.executeCommand(
            false,
            stdinFile,
            command);

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

    // Source:
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
