package cyr7.cli;

import cyr7.lexer.LexerUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import cyr7.lexer.MyLexer;

import java.io.*;
import java.util.Arrays;
import java.util.function.Consumer;

public class CLI {

    final static private String usage = "xic [options] <source files>";
    final static private int consoleWidth = HelpFormatter.DEFAULT_WIDTH;
    final static private int leftPadding = HelpFormatter.DEFAULT_LEFT_PAD;
    final static private PrintWriter writer = new PrintWriter(System.out);
    final static private HelpFormatter helpFormatter = new HelpFormatter();
    final static private Options options = createOptions();
    final static private CommandLineParser parser = new DefaultParser();

    static File pathDestination = new File(".");
    static boolean wantsLexing = false;

    /**
     * Creates an {@code Options} instance of the CLI parser. <\br> In this
     * instance, an {@code Option} instance is created for at least each of the
     * following CLI options: {@literal --help}, {@literal --lex}, and
     * {@literal -D <path>}.
     */
    public static Options createOptions() {
        Options options = new Options();

        Option help = Option.builder("h").longOpt("help").desc("Print a synopsis of options").hasArg(false)
                .argName(null).numberOfArgs(0).required(false).build();

        Option lex = Option.builder("l").longOpt("lex").desc("Generate output from lexical analysis").hasArg(false)
                .argName(null).numberOfArgs(0).required(false).build();

        Option destination = Option.builder("D").longOpt(null).desc("Specify where to place generated diagnostic files")
                .hasArg(true).argName("path").numberOfArgs(1).required(false).build();

        Option version = Option.builder("v").longOpt("version").desc("Version information").hasArg(false).argName(null)
                .numberOfArgs(0).required(false).build();

        return options.addOption(help).addOption(lex).addOption(destination).addOption(version);
    }

    /**
     * Prints a synopsis of the options.
     */
    public static void printHelpMessage() {
        helpFormatter.printHelp(writer, consoleWidth, usage, "where possible options include:", options, 0, leftPadding,
                "\n");
        writer.flush();
    }

    /**
     * Prints the version of xic.
     */
    public static void printVersionMessage() {
        writer.append("xic 1.0").append(System.lineSeparator());
        writer.flush();
    }

    /**
     * Parses the command given by {@code args} under the options setting
     * {@code options}.
     * 
     * @param args The options and arguments in a command.
     * @return A CommandLine instance which represents the results of the parsed
     *         {@code args}.
     * @throws ParseException Command contains invalid flags or incorrect number of
     *                        arguments.
     */
    public static CommandLine parseCommand(String[] args) throws ParseException {
        return parser.parse(options, args);
    }

    /**
     * Calls the xi lexer to lex the contents of {@code input} and writes the output
     * into {@code output}.
     */
    public static void useLexer(InputStream input, OutputStream output) {
        try {
            // TODO: Replace InputStream with Reader and OutputStream with writer to reduce conversion overhead.
            LexerUtil.lex(new InputStreamReader(input), new OutputStreamWriter(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the filename without its extension if an extension exists.
     *
     * @param file A filename
     * @return The filename of {@code file} without the extension.
     */
    public static String getMainFilename(File file) {
        String name = file.getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;
    }

    /**
     * Return {@code true} if {@code file} is a {@literal .xi} file.
     * Otherwise, return {@code false}.
     * @param file A filename
     */
    public static boolean isAXiFile(File file) {
        return getExtensionName(file).equals("xi");
    }

    /**
     * Returns the filename's extension name. If an extension does not exist, then
     * an empty string is returned.
     *
     * @param file A filename
     * @return The extension name of {@code file}.
     */
    public static String getExtensionName(File file) {
        String name = file.getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            return name.substring(pos + 1, name.length());
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        // If no arguments or options given, print help.
        if (args.length == 0) {
            printHelpMessage();
            writer.close();
            return;
        }

        CommandLine cmd;
        try {
            cmd = parseCommand(args);
        } catch (ParseException e) {
            writer.write(e.getMessage());
            writer.flush();
            writer.close();
            return;
        }

        // For each option given, perform task corresponding to option.
        cmd.iterator().forEachRemaining(t -> {
            String opt = t.getOpt();
            switch (opt) {
            case "h":
                printHelpMessage();
                break;
            case "l": {
                wantsLexing = true;
                break;
            }
            case "D":
                String directory = cmd.getOptionValue("D");
                pathDestination = new File(directory);
                break;
            case "v":
                printVersionMessage();
                break;
            default:
                writer.write("No case for given for option: " + opt);
                writer.flush();
                break;
            }
        });

        if (wantsLexing) {
            String[] sourceFiles = cmd.getArgs();
            for (String filename : sourceFiles) {
                File file = new File(filename);
                if (!isAXiFile(file)) {
                    continue;
                }

                BufferedInputStream inputStream;
                BufferedOutputStream outputStream;
                try {
                    inputStream = new BufferedInputStream(new FileInputStream(file));
                    File destination = new File(pathDestination.getAbsolutePath(), getMainFilename(file) + ".lexed");
                    if (!destination.exists()) {
                    	// Create directories if they don't exist
                    	destination.getParentFile().mkdirs();
                    }
                    outputStream = new BufferedOutputStream(new FileOutputStream(destination));
                    useLexer(inputStream, outputStream);
                } catch (FileNotFoundException e) {
                    writer.write(e.getMessage());
                }
            }
        }
        writer.flush();
        writer.close();
    }
}
