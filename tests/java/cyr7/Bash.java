package cyr7;

import cyr7.cli.OptConfig;
import cyr7.integration.Run.RunConfiguration;
import cyr7.x86.ASMUtil.TilerConf;

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

import static org.junit.jupiter.api.Assertions.fail;

public enum Bash {
    ;

    private static String libpath() {
        return "tests/resources/integration/lib";
    }

    public static void compileToAssembly(
        String filename,
        TilerConf tilerConf,
        OptConfig optConfig) throws IOException, InterruptedException {

        List<String> arguments = new ArrayList<>(List.of(
            "./xic",
            "-libpath",
            libpath(),
            "-tiler",
            tilerConf.name()));

        arguments.addAll(optConfig.convertToCLI());
        arguments.add(filename);
        String result = Bash.executeCommand(
            true,
            Optional.empty(),
            arguments.toArray(new String[0]));
        if (!result.isEmpty()) {
            System.err.println(result);
        }
    }

    public static File linkExecutable(String filename)
        throws IOException, InterruptedException {

        String linkerFilename = "/home/vagrant/runtime/linkxi.sh";
        File linkerFile = new File(linkerFilename);
        if (!linkerFile.exists()) {
            System.out.println("Cannot find linker file in ~/runtime");
            fail();
            return null;
        }
        if (!linkerFile.canExecute()) {
            System.out.println("Cannot execute the linker in ~/runtime");
            fail();
            return null;
        }

        File tmpFile = File.createTempFile("temp_" + filename, "");
        tmpFile.setExecutable(true);
        tmpFile.setReadable(true);
        tmpFile.setWritable(true);
        tmpFile.deleteOnExit();
        String result = Bash.executeCommand(
            true,
            Optional.empty(),
            linkerFile.getAbsolutePath(),
            filename,
            "-o",
            tmpFile.getAbsolutePath());
        if (!result.isEmpty()) {
            System.err.println(result);
        }

        return tmpFile;
    }

    public static String executeFile(
        File exe,
        String filename,
        String stdin,
        String[] args)
        throws IOException, InterruptedException {

        Optional<File> stdinFile;
        if (!stdin.isEmpty()) {
            File stdinFileReal = File.createTempFile("temp_stdin_" + filename, "");
            stdinFileReal.setReadable(true);
            stdinFileReal.setWritable(true);
            stdinFileReal.deleteOnExit();

            stdinFile = Optional.of(stdinFileReal);

            FileWriter writer = new FileWriter(stdinFileReal);
            writer.write(stdin);
            writer.flush();
            writer.close();
        } else {
            stdinFile = Optional.empty();
        }

        String[] command = new String[1 + args.length];
        command[0] = exe.getAbsolutePath();
        System.arraycopy(args, 0, command, 1, args.length);

        String result = Bash.executeCommand(
            false,
            stdinFile,
            command);
        if (!result.isEmpty()) {
            System.err.println(result);
        }

        return result;
    }

    public static String executeCommand(
        boolean throwOnError,
        Optional<File> stdin,
        String... command)
        throws InterruptedException, IOException {

//        System.out.println(String.join(" ", command));
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
        if (newLineExists(redirectedTarget)) {
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

    // Source:
    // https://stackoverflow.com/questions/28795440/check-if-a-new-line-exists-at-end-of-file
    private static boolean newLineExists(File file) throws IOException {
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
