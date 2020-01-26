package cyr7.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.util.function.Consumer;

public class CLI {

	final static String usage = "xic [options] <source files>";
	final static int consoleWidth = HelpFormatter.DEFAULT_WIDTH;
	final static int leftPadding = HelpFormatter.DEFAULT_LEFT_PAD;
	final static PrintWriter writer = new PrintWriter(System.out);
	final static HelpFormatter helpFormatter = new HelpFormatter();
	final static Options options = createOptions();
	final static CommandLineParser parser = new DefaultParser();

	public static Options createOptions() {
		Options options = new Options();        
        
        Option help =  Option.builder("h")
        					 .longOpt("help")
        					 .desc("Print a synopsis of options")
        					 .hasArg(false)
        					 .argName(null)
        					 .numberOfArgs(0)
        					 .required(false)
        					 .build();
        
        Option lex = Option.builder("l")
				   		   .longOpt("lex")
				   		   .desc("Generate output from lexical analysis")
				   		   .hasArg(false)
				   		   .argName(null)
				   		   .numberOfArgs(0)
				   		   .required(false)
				   		   .build();
        
        Option destination = Option.builder("D")
        						   .longOpt(null)
        						   .desc("Specify where to place generated diagnostic files")
        						   .hasArg(true)
        						   .argName("path")
        						   .numberOfArgs(1)
        						   .required(false)
        						   .build();
        
        Option version = Option.builder("v")
        					   .longOpt("version")
        					   .desc("Version information")
        					   .hasArg(false)
        					   .argName(null)
        					   .numberOfArgs(0)
        					   .required(false)
        					   .build();
        
        return options.addOption(help).addOption(lex).addOption(destination).addOption(version);
	}
	
	public static void printHelpMessage() {
		helpFormatter.printHelp(writer, consoleWidth, usage, "where possible options include:", options, 0, leftPadding, "\n");
		writer.flush();		
	}
	
	
	public static void printVersionMessage() {
		writer.println("xic 1.0");
		writer.flush();		
	}
	
	
	
    public static void main(String[] args) {        
     	
    	if (args.length == 0) {
    		printHelpMessage();
    		return;
    	}
    	
    	CommandLine cmd;
        try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {		
			System.out.println(e.getMessage());
			return;
		}
       
        cmd.iterator().forEachRemaining(new Consumer<Option>(){
			@Override
			public void accept(Option t) {
				String opt = t.getOpt();
				switch (opt) {
				case "h":
					break;
				case "l":
					break;
				case "D":
					break;
				case "v":
					break;
				default:
					System.out.println("No case for given for option: " + opt);
					break;
				}
			}
        });
        
    }
}







