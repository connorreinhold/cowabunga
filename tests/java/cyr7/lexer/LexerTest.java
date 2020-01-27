package cyr7.lexer;

import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void add() throws IOException {
        fail();

        MyLexer lexer = new MyLexer(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("add.xi")));
        Scanner actualFile = new Scanner(getClass().getClassLoader().getResourceAsStream("add.lexedsol"));

        MyLexer.Token token;
        String line;
        while ((token = lexer.nextToken()) != null && (line = actualFile.nextLine()) != null) {
            assertEquals(token.toString(), line);
        }
    }

}