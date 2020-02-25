package cyr7.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {

    @Test
    void checksXiFile() {
        String[] correctFilenames = new String[]
            { "correct.xi", "/path/to/file.xi", "../../../../here.xi" };

        String[] incorrectFilenames = new String[]
            { "incorrect.xipp", "almost.c", "not_close.cpp", "wrong_lang.java",
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
        final String[] args = "-q".split(" ");
        assertThrows(ParseException.class, () -> CLI.parseCommand(args));

        final String[] args2 = "-q -p --this_is_a_bad_option".split(" ");
        assertThrows(ParseException.class, () -> CLI.parseCommand(args2));

        final String[] args3 = ". -q -p --this_is_a_bad_option".split(" ");
        assertThrows(ParseException.class, () -> CLI.parseCommand(args3));

        final String[] args4 = 
                "--help --this_is_a_bad_option -D . --lex a.xi".split(" ");
        assertThrows(ParseException.class, () -> CLI.parseCommand(args4));

    }

    @Test
    void noPathArgumentForDException() {
        final String[] args = "-D".split(" ");
        assertThrows(ParseException.class, () -> CLI.parseCommand(args));

        final String[] args2 = "-D -l".split(" ");
        assertThrows(ParseException.class, () -> CLI.parseCommand(args2));

        final String[] args3 = "-D -l -h -v".split(" ");
        assertThrows(ParseException.class, () -> CLI.parseCommand(args3));

        final String[] args4 = "--lex --help -v -D".split(" ");
        assertThrows(ParseException.class, () -> CLI.parseCommand(args4));

    }

    @Test
    void obtainsAllOptions() throws ParseException {
        String command = "--help --lex -D .";
        String[] args = command.split(" ");
        assertDoesNotThrow(() -> CLI.parseCommand(args));
        CommandLine cmd = CLI.parseCommand(args);
        assertTrue(cmd.hasOption("h"));
        assertTrue(cmd.hasOption("l"));
        assertTrue(cmd.hasOption("D"));
        assertEquals(cmd.getOptionValue("D"), ".");
    }

    @Test
    void getsCorrectPathForD() throws ParseException {
        final String command = "-D ";
        String[] paths = new String[]
            { ".", "..", "../..", "/base/path/to/some/loc",
                    "~/Desktop/results" };
        for (String path : paths) {
            String[] args = (command + path).split(" ");
            CommandLine cmd = CLI.parseCommand(args);
            assertDoesNotThrow(() -> CLI.parseCommand(args));
            assertTrue(!cmd.hasOption("h"));
            assertTrue(!cmd.hasOption("l"));
            assertTrue(cmd.hasOption("D"));
            assertEquals(cmd.getOptionValue("D"), path);
        }
    }

    @Test
    void getsAllSourceFiles() throws ParseException {
        final String[] noFiles = "--lex".split(" ");
        final String[] oneFile = "--lex a.xi".split(" ");
        final String[] twoFiles = "--lex a.xi b.xi".split(" ");
        final String[] noOptions = "a.xi b.xi c.xi".split(" ");
        final String[] includesD = "-D . a.xi c.xi b.xi".split(" ");
        final String[] includesAllOptions = 
                "-D . -v --lex --help a.xi b.xi c.xi".split(" ");

        assertDoesNotThrow(() -> CLI.parseCommand(noFiles));
        CommandLine cmd = CLI.parseCommand(noFiles);
        assertTrue(!cmd.hasOption("h"));
        assertTrue(cmd.hasOption("l"));
        assertTrue(!cmd.hasOption("D"));
        assertEquals(cmd.getArgs().length, 0);

        assertDoesNotThrow(() -> CLI.parseCommand(oneFile));
        cmd = CLI.parseCommand(oneFile);
        assertTrue(!cmd.hasOption("h"));
        assertTrue(cmd.hasOption("l"));
        assertTrue(!cmd.hasOption("D"));
        assertEquals(cmd.getArgs().length, 1);
        assertEquals(cmd.getArgs()[0], "a.xi");

        assertDoesNotThrow(() -> CLI.parseCommand(twoFiles));
        cmd = CLI.parseCommand(twoFiles);
        assertTrue(!cmd.hasOption("h"));
        assertTrue(cmd.hasOption("l"));
        assertTrue(!cmd.hasOption("D"));
        assertEquals(cmd.getArgs().length, 2);
        assertEquals(cmd.getArgs()[0], "a.xi");
        assertEquals(cmd.getArgs()[1], "b.xi");

        assertDoesNotThrow(() -> CLI.parseCommand(noOptions));
        cmd = CLI.parseCommand(noOptions);
        assertTrue(!cmd.hasOption("h"));
        assertTrue(!cmd.hasOption("l"));
        assertTrue(!cmd.hasOption("D"));
        assertEquals(cmd.getArgs().length, 3);
        assertEquals(cmd.getArgs()[0], "a.xi");
        assertEquals(cmd.getArgs()[1], "b.xi");
        assertEquals(cmd.getArgs()[2], "c.xi");

        assertDoesNotThrow(() -> CLI.parseCommand(includesD));
        cmd = CLI.parseCommand(includesD);
        assertTrue(!cmd.hasOption("h"));
        assertTrue(!cmd.hasOption("l"));
        assertTrue(cmd.hasOption("D"));
        assertEquals(cmd.getArgs().length, 3);
        assertEquals(cmd.getArgs()[0], "a.xi");
        assertEquals(cmd.getArgs()[1], "c.xi");
        assertEquals(cmd.getArgs()[2], "b.xi");

        assertDoesNotThrow(() -> CLI.parseCommand(includesAllOptions));
        cmd = CLI.parseCommand(includesAllOptions);
        assertTrue(cmd.hasOption("h"));
        assertTrue(cmd.hasOption("l"));
        assertTrue(cmd.hasOption("D"));
        assertEquals(cmd.getOptionValue("D"), ".");
        assertEquals(cmd.getArgs().length, 3);
        assertEquals(cmd.getArgs()[0], "a.xi");
        assertEquals(cmd.getArgs()[1], "b.xi");
        assertEquals(cmd.getArgs()[2], "c.xi");
    }

}
