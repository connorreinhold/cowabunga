package cyr7.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import cyr7.ir.IrUtil;
import cyr7.lexer.LexerUtil;
import cyr7.parser.ParserUtil;
import cyr7.typecheck.TypeCheckUtil;

public class CLI {

    final static private String usage = "xic [options] <source files>";
    final static private int consoleWidth = HelpFormatter.DEFAULT_WIDTH;
    final static private int leftPadding = HelpFormatter.DEFAULT_LEFT_PAD;
    final static private PrintWriter writer = new PrintWriter(System.out);
    final static private HelpFormatter helpFormatter = new HelpFormatter();
    final static private Options options = createOptions();
    final static private CommandLineParser parser = new DefaultParser();

    private static boolean debugPrintingEnabled = false;
    private static boolean optimizationsEnabled = true;

    private static boolean cFoldEnabled = true;
    private static boolean commutativeEnabled = true;
    
    private static boolean wantsLexing = false;
    private static boolean wantsParsing = false;
    private static boolean wantsTypechecking = false;
    private static boolean wantsIrGen = false;
    private static boolean wantsMirRun = false;
    private static boolean wantsIrRun = false;
    
    private static File sourceRoot = new File(".");
    private static File libRoot = new File(".");
    private static File destinationRoot = new File(".");

    /**
     * Creates an {@code Options} instance of the CLI parser. <\br> In this
     * instance, an {@code Option} instance is created for at least each of the
     * following CLI options: {@literal --help}, {@literal --lex}, and
     * {@literal -D <path>}.
     */
    private static Options createOptions() {
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
            .desc("Generate output from semantic analysis")
            .hasArg(false)
            .numberOfArgs(0)
            .required(false)
            .build();
        
        Option irGen = Option
            .builder("ign")
            .longOpt("irgen")
            .desc("Generate intermediate code")
            .hasArg(false)
            .numberOfArgs(0)
            .required(false)
            .build();
        
        // For internal testing
        Option irRun = Option
            .builder("irn")
            .longOpt("irrun")
            .desc("Generate and interpret intermediate code")
            .hasArg(false)
            .numberOfArgs(0)
            .required(false)
            .build();
        
        // For internal testing
        Option mirRun = Option
            .builder("mrn")
            .longOpt("mirrun")
            .desc("Generate and interpret middle-level intermediate code ")
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
        
        Option optimizations = Option
            .builder("O")
            .longOpt(null)
            .desc("Disable optimizations")
            .hasArg(false)
            .argName(null)
            .numberOfArgs(0)
            .required(false)
            .build();
        
        // For internal testing
        Option cFoldOpt = Option
            .builder("cfold")
            .longOpt(null)
            .desc("Enable constant folding optimizations")
            .hasArg(false)
            .argName(null)
            .numberOfArgs(0)
            .required(false)
            .build();
        
        // For internal testing
        Option commutativeOpt = Option
            .builder("commutative")
            .longOpt(null)
            .desc("Enable commutative optimizations")
            .hasArg(false)
            .argName(null)
            .numberOfArgs(0)
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
            .addOption(irGen)
            .addOption(irRun)
            .addOption(mirRun)
            .addOption(optimizations)
            .addOption(cFoldOpt)
            .addOption(commutativeOpt)
            .addOption(source)
            .addOption(libpath)
            .addOption(destination)
            .addOption(version)
            .addOption(debugPrinting);
    }

    /**
     * Prints a synopsis of the options.
     */
    private static void printHelpMessage() {
        helpFormatter.printHelp(writer, consoleWidth, usage,
            "where possible options include:", options, 0, leftPadding,
            "\n");
        writer.flush();
    }

    /**
     * Prints the version of xic.
     */
    private static void printVersionMessage() {
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
    static CommandLine parseCommand(String[] args)
        throws ParseException {
        return parser.parse(options, args);
    }


    /**
     * Returns the filename without its extension if an extension exists.
     *
     * @param path A path
     * @return The filename of {@code path} without the extension.
     */
    private static String getMainFilename(Path path) {
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
    static boolean isAXiFile(File file) {
        return getExtensionName(file).equals("xi");
    }

    /**
     * Returns the filename's extension name. If an extension does not exist,
     * then an empty string is returned.
     *
     * @param file A filename
     * @return The extension name of {@code file}.
     */
    private static String getExtensionName(File file) {
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
                case "irg":
                    wantsIrGen = true;
                    break;
                case "irn": 
                    wantsIrRun = true;
                    break;
                case "mrn":
                    wantsMirRun = true;
                    break;
                case "D": {
                    String directory = cmd.getOptionValue("D");
                    destinationRoot = new File(directory);
                    break;
                }
                case "O": {
                    optimizationsEnabled = false;
                    break;
                }
                case "cfold": {
                    cFoldEnabled = true;
                    break;
                }
                case "commutative": {
                    commutativeEnabled = true;
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
                    input = getReader(filename);
                    output = getWriter(filename, "lexed");
                    LexerUtil.lex(input, output, filename);
                } catch (Exception e) {
                    writer.write(e.getMessage());
                }
                closeIOStreams(input, output);
            }

            if (wantsParsing) {
                debugPrint("Parsing file: " + filename);
                try {
                    input = getReader(filename);
                    output = getWriter(filename, "parsed");
                    ParserUtil.parse(input, output, filename, isIXI);
                } catch (Exception e) {
                    writer.write(e.getMessage());
                }
                closeIOStreams(input, output);
            }

            if (wantsTypechecking) {
                debugPrint("Typechecking file: " + filename);
                try {
                    input = getReader(filename);
                    output = getWriter(filename, "typed");
                    TypeCheckUtil.typeCheck(input, output, filename, isIXI,
                        ixiFilename -> getLibraryReader(ixiFilename + ".ixi")
                    );
                } catch (Exception e) {
                    writer.write(e.getMessage());
                }
                closeIOStreams(input, output);
            }
            
            if (wantsIrGen) {
                debugPrint("Generate intermediate code for: " + filename);
                try {
                    input = getReader(filename);
                    output = getWriter(filename, "ir");
                    IrUtil.irGen(input, output, filename, isIXI);
                } catch (Exception e) {
                    writer.write(e.getMessage());
                }
                
            }
            
            if (wantsMirRun) {
                debugPrint("Generate and interpret middle-level intermediate code for: " + filename);
                try {
                    input = getReader(filename);
                    output = getWriter(filename, "mir_run");
                    IrUtil.mirRun(input, output, filename, isIXI);
                } catch (Exception e) {
                    writer.write(e.getMessage());
                }
            }
            
            if (wantsIrRun) {
                debugPrint("Generate and interpret intermediate code for: " + filename);
                try {
                    input = getReader(filename);
                    output = getWriter(filename, "ir_run");
                    IrUtil.irRun(input, output, filename, isIXI);
                } catch (Exception e) {
                    writer.write(e.getMessage());
                }
            }
            

        }
        writer.flush();
        writer.close();
    }

    private static Reader getReader(String filename) throws IOException {
        Path sourcePath = Paths.get(sourceRoot.getAbsolutePath(), filename);
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

    private static Reader getLibraryReader(String filename) throws IOException {
        Path sourcePath = Paths.get(libRoot.getAbsolutePath(), filename);
        debugPrint("Opening reader to: " + sourcePath);
        return new BufferedReader(new FileReader(sourcePath.toFile()));

    }

}
