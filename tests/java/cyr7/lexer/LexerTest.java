package cyr7.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;

class LexerTest {

    @Test
    void add() {
        Reader inputFile = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("add.xi"));

    }

}