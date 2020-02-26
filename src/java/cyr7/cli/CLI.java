package cyr7.cli;

import cyr7.lexer.LexerUtil;
import cyr7.parser.ParserUtil;
import cyr7.typecheck.IxiFileOpener;
import cyr7.typecheck.TypeCheckUtil;
import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CLI {

    private static class FileOpener implements IxiFileOpener {

        @Override
        public Reader openIxiLibraryFile(String name) throws IOException {
            return getReader(name + ".ixi", true);
        }

    }

    final static private String usage = "xic [options] <source files>";
    final static private int consoleWidth = HelpFormatter.DEFAULT_WIDTH;
    final static private int leftPadding = HelpFormatter.DEFAULT_LEFT_PAD;
    final static private PrintWriter writer = new PrintWriter(System.out);
    final static private HelpFormatter helpFormatter = new HelpFormatter();
    final static private Options options = createOptions();
    final static private CommandLineParser parser = new DefaultParser();

    static boolean debugPrintingEnabled = false;
    static boolean wantsLexing = false;
    static boolean wantsParsing = false;
    static boolean wantsTypechecking = false;
    static File sourceRoot = new File(".");
    static File libRoot = new File(".");
    static File destinationRoot = new File(".");

    /**
     * Creates an {@code Options} instance of the CLI parser. <\br> In this
     * instance, an {@code Option} instance is created for at least each of the
     * following CLI options: {@literal --help}, {@literal --lex}, and
     * {@literal -D <path>}.
     */
    public static Options createOptions() {
        Options options = new Options();

        Option help = Option
                .builder("h")
                .longOpt("help")
                .desc("Print a synopsis of options")
                .hasArg(false)
                .argName(null)
                .numberOfArgs(0)
                .required(false)
                .build();

        Option lex = Option
                .builder("l")
                .longOpt("lex")
                .desc("Generate output from lexical analysis")
                .hasArg(false)
                .argName(null)
                .numberOfArgs(0)
                .required(false)
                .build();

        Option parse = Option
                .builder("p")
                .longOpt("parse")
                .desc("Generate output from syntactic analysis")
                .hasArg(false)
                .argName(null)
                .numberOfArgs(0)
                .required(false)
                .build();

        Option typecheck = Option
                .builder("t")
                .longOpt("typecheck")
                .desc("Generate output from semantic analysis.")
                .hasArg(false)
                .numberOfArgs(0)
                .required(false)
                .build();

        Option source = Option
                .builder("sourcepath")
                .desc("Specify where to find generated diagnostic files")
                .hasArg(true)
                .argName("path")
                .numberOfArgs(1)
                .required(false)
                .build();
        
        Option libpath = Option
                .builder("libpath")
                .desc("Specify where to find library interface files.")
                .hasArg(true)
                .argName("path")
                .numberOfArgs(1)
                .required(false)
                .build();

        Option destination = Option
                .builder("D")
                .longOpt(null)
                .desc("Specify where to place generated diagnostic files")
                .hasArg(true)
                .argName("path")
                .numberOfArgs(1)
                .required(false)
                .build();

        Option version = Option
                .builder("v")
                .longOpt("version")
                .desc("Version information")
                .hasArg(false)
                .argName(null)
                .numberOfArgs(0)
                .required(false)
                .build();

        Option debugPrinting = Option
                .builder("debug")
                .desc("Print debugging information")
                .hasArg(false)
                .argName(null)
                .numberOfArgs(0)
                .required(false)
                .build();

        return options.addOption(help)
                .addOption(lex)
                .addOption(parse)
                .addOption(typecheck)
                .addOption(source)
                .addOption(libpath)
                .addOption(destination)
                .addOption(version)
                .addOption(debugPrinting);
    }

    /**
     * Prints a synopsis of the options.
     */
    public static void printHelpMessage() {
        helpFormatter.printHelp(writer, consoleWidth, usage,
                "where possible options include:", options, 0, leftPadding,
                "\n");
        writer.flush();
    }

    /**
     * Prints the version of xic.
     */
    public static void printVersionMessage() {
        writer.append("xic 1.0")
                .append(System.lineSeparator());
        writer.flush();
    }

    /**
     * Parses the command given by {@code args} under the options setting
     * {@code options}.
     *
     * @param args The options and arguments in a command.
     * @return A CommandLine instance which represents the results of the parsed
     * {@code args}.
     * @throws ParseException Command contains invalid flags or incorrect number
     *                        of arguments.
     */
    public static CommandLine parseCommand(String[] args)
            throws ParseException {
        return parser.parse(options, args);
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
     * Returns the filename without its extension if an extension exists.
     *
     * @param path A path
     * @return The filename of {@code path} without the extension.
     */
    public static String getMainFilename(Path path) {
        String name = path.getFileName().toString();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;
    }

    /**
     * Return {@code true} if {@code file} is a {@literal .xi} file. Otherwise,
     * return {@code false}.
     *
     * @param file A filename
     */
    public static boolean isAXiFile(File file) {
        return getExtensionName(file).equals("xi");
    }

    /**
     * Returns the filename's extension name. If an extension does not exist,
     * then an empty string is returned.
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
                case "l":
                    wantsLexing = true;
                    break;
                case "p":
                    wantsParsing = true;
                    break;
                case "t":
                    wantsTypechecking = true;
                    break;
                case "D": {
                    String directory = cmd.getOptionValue("D");
                    destinationRoot = new File(directory);
                    break;
                }
                case "sourcepath": {
                    String directory = cmd.getOptionValue("sourcepath");
                    sourceRoot = new File(directory);
                    break;
                }
                case "libpath": {
                    String directory = cmd.getOptionValue("libpath");
                    libRoot = new File(directory);
                    break;
                }
                case "v":
                    printVersionMessage();
                    break;
                case "debug":
                    debugPrintingEnabled = true;
                    break;
                default:
                    writer.write("No case for given for option: " + opt);
                    writer.flush();
                    break;
            }
        });
        for (String filename : cmd.getArgs()) {
            boolean isIXI;
            if (filename.endsWith(".xi") || filename.endsWith(".ixi")) {
            	isIXI = filename.endsWith(".ixi");
            } else {
            	continue;
            }

            Reader input = null;
            Writer output = null;
            if (wantsLexing) {
                debugPrint("Lexing file: " + filename);
                try {
                    input = getReader(filename, isIXI);
                    output = getWriter(filename, "lexed");
                    LexerUtil.lex(input, output);
                } catch (Exception e) {
                    writer.write(e.getMessage());
                }
                closeIOStreams(input, output);
            }

            if (wantsParsing) {
                debugPrint("Parsing file: " + filename);
                try {
                    input = getReader(filename, isIXI);
                    output = getWriter(filename, "parsed");
                    ParserUtil.parse(input, output, isIXI);
                } catch (Exception e) {
                    writer.write(e.getMessage());
                }
                closeIOStreams(input, output);
            }
            
            if (wantsTypechecking) {
                debugPrint("Typechecking file: " + filename);
                try {
                    input = getReader(filename, isIXI);
                    output = getWriter(filename, "typed");
                    TypeCheckUtil.typeCheck(input, output, new FileOpener());
                } catch (Exception e) {
                    writer.write(e.getMessage());
                }
                closeIOStreams(input, output);
            }
        }
        writer.flush();
        writer.close();
    }

    private static Reader getReader(String filename, boolean isIXI) 
            throws IOException {
        Path sourcePath;
        if (isIXI) {
            sourcePath = Paths.get(libRoot.getAbsolutePath(), filename);
        } else {
            sourcePath = Paths.get(sourceRoot.getAbsolutePath(), filename);
        }
        debugPrint("Opening reader to: " + sourcePath);
        return new BufferedReader(new FileReader(sourcePath.toFile()));
    }

    private static Writer getWriter(String relativePath, String fileExtension) 
            throws IOException {
        Path destPath = Paths.get(destinationRoot.getAbsolutePath(), 
                                    relativePath).getParent();
        File dest = new File(destPath.toFile(), String.format("%s." + 
                                    fileExtension, 
                                    getMainFilename(Path.of(relativePath))));
        if (!dest.exists()) {
            // Create directories if they don't exist
            dest.getParentFile().mkdirs();
        }

        debugPrint("Opening writer to: " + dest);

        return new BufferedWriter(new FileWriter(dest));
    }

    
    private static void closeIOStreams(Reader input, Writer output) {
        if (input != null && output != null) {
            try {
                output.flush();
                output.close();
                input.close();
            } catch (IOException e) {
                writer.write("Unexpected error occurred when closing "
                        + "io stream.");
            }
        }
    }
    
    
    private static void debugPrint(Object v) {
        if (debugPrintingEnabled) {
            System.err.println("DEBUG: " + v);
        }
    }

}
