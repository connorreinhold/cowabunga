package cyr7.cli;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import cyr7.cli.CLI;

class CLITest {

    @Test
    void checksXiFile() {
	String[] correctFilenames = new String[] { "correct.xi",
		"/path/to/file.xi", "../../../../here.xi" };

	String[] incorrectFilenames = new String[] { "incorrect.xipp",
		"almost.c", "not_close.cpp", "wrong_lang.java",
		"just_a_raw_file", ".xi", "xi" };
	
	for (String correct : correctFilenames) {
	    assertTrue(CLI.isAXiFile(new File(correct)));
	}

	for (String wrong : incorrectFilenames) {
	    assertFalse(CLI.isAXiFile(new File(wrong)));
	}
    }

    @Test
    void catchesBadArguments() {
	String[] args = "-q".split(" ");
	try {
	    CLI.parseCommand(args);
	    fail("ParseException failed to be thrown");
	} catch (ParseException e) {
	}

	args = "-q -p --this_is_a_bad_option".split(" ");
	try {
	    CLI.parseCommand(args);
	    fail("ParseException failed to be thrown");
	} catch (ParseException e) {
	}

	args = ". -q -p --this_is_a_bad_option".split(" ");
	try {
	    CLI.parseCommand(args);
	    fail("ParseException failed to be thrown");
	} catch (ParseException e) {
	}

	args = "--help --this_is_a_bad_option -D . --lex a.xi".split(" ");
	try {
	    CLI.parseCommand(args);
	    fail("ParseException failed to be thrown");
	} catch (ParseException e) {
	}

    }

    @Test
    void noPathArgumentForDException() {
	String[] args = "-D".split(" ");
	try {
	    CLI.parseCommand(args);
	    fail("ParseException failed to be thrown");
	} catch (ParseException e) {
	}

	args = "-D -l".split(" ");
	try {
	    CLI.parseCommand(args);
	    fail("ParseException failed to be thrown");
	} catch (ParseException e) {
	}

	args = "-D -l -h -v".split(" ");
	try {
	    CLI.parseCommand(args);
	    fail("ParseException failed to be thrown");
	} catch (ParseException e) {
	}

	args = "--lex --help -v -D".split(" ");
	try {
	    CLI.parseCommand(args);
	    fail("ParseException failed to be thrown");
	} catch (ParseException e) {
	}
    }

    @Test
    void obtainsAllOptions() {
	String command = "--help --lex -D .";
	String[] args = command.split(" ");
	try {
	    CommandLine cmd = CLI.parseCommand(args);
	    assertTrue(cmd.hasOption("h"));
	    assertTrue(cmd.hasOption("l"));
	    assertTrue(cmd.hasOption("D"));
	    assertEquals(cmd.getOptionValue("D"), ".");
	} catch (ParseException e) {
	    fail("ParseException thrown: " + e.getMessage());
	}
    }

    @Test
    void getsCorrectPathForD() {
	final String command = "-D ";
	String[] paths = new String[] { ".", "..", "../..",
		"/base/path/to/some/loc", "~/Desktop/results" };
	for (String path : paths) {
	    String[] args = (command + path).split(" ");
	    try {
		CommandLine cmd = CLI.parseCommand(args);
		assertTrue(!cmd.hasOption("h"));
		assertTrue(!cmd.hasOption("l"));
		assertTrue(cmd.hasOption("D"));
		assertEquals(cmd.getOptionValue("D"), path);
	    } catch (ParseException e) {
		fail("ParseException thrown: " + e.getMessage());
	    }
	}
    }

    @Test
    void getsAllSourceFiles() {
	final String[] noFiles = "--lex".split(" ");
	final String[] oneFile = "--lex a.xi".split(" ");
	final String[] twoFiles = "--lex a.xi b.xi".split(" ");
	final String[] noOptions = "a.xi b.xi c.xi".split(" ");
	final String[] includesD = "-D . a.xi c.xi b.xi".split(" ");
	final String[] includesAllOptions = "-D . -v --lex --help a.xi b.xi c.xi"
		.split(" ");
	try {
	    CommandLine cmd = CLI.parseCommand(noFiles);
	    assertTrue(!cmd.hasOption("h"));
	    assertTrue(cmd.hasOption("l"));
	    assertTrue(!cmd.hasOption("D"));
	    assertEquals(cmd.getArgs().length, 0);

	    cmd = CLI.parseCommand(oneFile);
	    assertTrue(!cmd.hasOption("h"));
	    assertTrue(cmd.hasOption("l"));
	    assertTrue(!cmd.hasOption("D"));
	    assertEquals(cmd.getArgs().length, 1);
	    assertEquals(cmd.getArgs()[0], "a.xi");

	    cmd = CLI.parseCommand(twoFiles);
	    assertTrue(!cmd.hasOption("h"));
	    assertTrue(cmd.hasOption("l"));
	    assertTrue(!cmd.hasOption("D"));
	    assertEquals(cmd.getArgs().length, 2);
	    assertEquals(cmd.getArgs()[0], "a.xi");
	    assertEquals(cmd.getArgs()[1], "b.xi");

	    cmd = CLI.parseCommand(noOptions);
	    assertTrue(!cmd.hasOption("h"));
	    assertTrue(!cmd.hasOption("l"));
	    assertTrue(!cmd.hasOption("D"));
	    assertEquals(cmd.getArgs().length, 3);
	    assertEquals(cmd.getArgs()[0], "a.xi");
	    assertEquals(cmd.getArgs()[1], "b.xi");
	    assertEquals(cmd.getArgs()[2], "c.xi");

	    cmd = CLI.parseCommand(includesD);
	    assertTrue(!cmd.hasOption("h"));
	    assertTrue(!cmd.hasOption("l"));
	    assertTrue(cmd.hasOption("D"));
	    assertEquals(cmd.getArgs().length, 3);
	    assertEquals(cmd.getArgs()[0], "a.xi");
	    assertEquals(cmd.getArgs()[1], "c.xi");
	    assertEquals(cmd.getArgs()[2], "b.xi");

	    cmd = CLI.parseCommand(includesAllOptions);
	    assertTrue(cmd.hasOption("h"));
	    assertTrue(cmd.hasOption("l"));
	    assertTrue(cmd.hasOption("D"));
	    assertEquals(cmd.getOptionValue("D"), ".");
	    assertEquals(cmd.getArgs().length, 3);
	    assertEquals(cmd.getArgs()[0], "a.xi");
	    assertEquals(cmd.getArgs()[1], "b.xi");
	    assertEquals(cmd.getArgs()[2], "c.xi");
	} catch (ParseException e) {
	    fail("ParseException thrown: " + e.getMessage());
	}
    }

}
