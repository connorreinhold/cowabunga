package cyr7.parser.xi;

import static org.junit.jupiter.api.Assertions.*;

import cyr7.exceptions.ParserIntegerOverflowException;
import cyr7.parser.ParserFactory;
import org.junit.jupiter.api.Test;

import cyr7.exceptions.LexerIntegerOverflowException;
import cyr7.parser.XiParser;

class TestIntegerOverflow {

    XiParser parser;
    String program;

    @Test
    void test() throws Exception {

        // Normal statement
        program = "main() { a: int = 0 }";
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);

        // Normal statement
        program = "main() { a: int = 100 }";
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);

        // Exceedingly large number
        program = "main() { a: int = 99999999999999999999999999999 }";
        parser = ParserFactory.make(program, false);
        assertThrows(LexerIntegerOverflowException.class, parser::parse);

        // At 2^63 check
        program = "main() { a: int = 9223372036854775808 }";
        parser = ParserFactory.make(program, false);
        assertThrows(ParserIntegerOverflowException.class, parser::parse);

    }

    @Test
    void unaryOperation() throws Exception {
        // Min Int
        program = "main() { a: int = -9223372036854775808 }";
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);

        // Cancel min int
        program = "main() { a: int = --9223372036854775808 }";
        parser = ParserFactory.make(program, false);
        assertThrows(ParserIntegerOverflowException.class, parser::parse);

        // Double Cancel min int
        program = "main() { a: int = ---9223372036854775808 }";
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);

        program = "main() { a: int = -(9223372036854775808) }";
        parser = ParserFactory.make(program, false);
        assertThrows(ParserIntegerOverflowException.class, parser::parse);

    }

    @Test
    void binaryOperation() throws Exception {
        // Fails
        program = "main() { a: int = 0 - 9223372036854775808 }";
        parser = ParserFactory.make(program, false);
        assertThrows(ParserIntegerOverflowException.class, parser::parse);

        // Cancel min int
        program = "main() { a: int = 0 - -9223372036854775808 }";
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);

    }

}
