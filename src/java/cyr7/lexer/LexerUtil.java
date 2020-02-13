package cyr7.lexer;

import cyr7.util.Util;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import cyr7.parser.sym;

public class LexerUtil {

	/**
	 * Read contents and write a line by line description of the lexed output.
	 *
	 * @param reader the contents to read
	 * @param writer the place to write
	 * @throws IOException if the reader throws an {@code IOException}
	 */
	public static void lex(Reader reader, Writer writer) throws IOException {
		MyLexer lexer = new MyLexer(new BufferedReader(reader));
		ComplexSymbol token;

		try {
			while ((token = lexer.next_token()).sym != sym.EOF) {
				writer.append(fullDescription(token))
						.append(System.lineSeparator());
			}
		} catch (Exception e) {
			writer.append(e.getMessage()).append(System.lineSeparator());
		}
		writer.flush();
	}

	/**
	 * A description of a token including line and column number in the format:
	 *
	 * {@code $line:$column $desc}
	 *
	 * @param token the symbol to describe
	 * @return a description of the token
	 */
	static String fullDescription(ComplexSymbol token) {
		int line = token.xleft.getLine();
		int column = token.xleft.getColumn();

		String location = "" + line + ":" + column + " ";

		return location + symbolDescription(token.sym, token.value);
	}

	/**
	 * A readable description for tokens based on their Symbol ID.
	 *
	 * @param symId the ID of the symbol
	 * @param value the value of a given symbol
	 * @return a string containing a description of the symbol
	 */
	public static String symbolDescription(int symId, Object value) {
		switch (symId) {
			case sym.IXI_FILE:
				return "Ixi File";
			case sym.XI_FILE:
				return "Xi File";
			case sym.USE:
				return "use";
			case sym.IF:
				return "if";
			case sym.WHILE:
				return "while";
			case sym.ELSE:
				return "else";
			case sym.RETURN:
				return "return";
			case sym.LENGTH:
				return "length";

			case sym.TYPE_INT:
				return "int";
			case sym.TYPE_BOOL:
				return "bool";

			case sym.BOOL_LITERAL:
				return value.toString();
			case sym.INT_LITERAL:
				return "integer " + value.toString();
			case sym.CHAR_LITERAL:
				return "character " + Util.unescapeString(value.toString());
			case sym.STRING_LITERAL:
				return "string " + Util.unescapeString(value.toString());

			case sym.ID:
				return "id " + value;

			case sym.L_PAREN:
				return "(";
			case sym.R_PAREN:
				return ")";
			case sym.L_SQ_BRKT:
				return "[";
			case sym.R_SQ_BRKT:
				return "]";
			case sym.L_BRACE:
				return "{";
			case sym.R_BRACE:
				return "}";
			case sym.COLON:
				return ":";
			case sym.SEMICOLON:
				return ";";
			case sym.COMMA:
				return ",";
			case sym.UNDERSCORE:
				return "_";

			case sym.ASSIGN:
				return "=";

			case sym.PLUS:
				return "+";
			case sym.MINUS:
				return "-";
			case sym.MULT:
				return "*";
			case sym.HIGH_MULT:
				return "*>>";
			case sym.DIVIDE:
				return "/";
			case sym.REMAINDER:
				return "%";

			case sym.NEG_BOOL:
				return "!";

			case sym.LT:
				return "<";
			case sym.LTE:
				return "<=";
			case sym.GT:
				return ">";
			case sym.GTE:
				return ">=";

			case sym.EQUALS:
				return "==";
			case sym.NOT_EQUALS:
				return "!=";

			case sym.LOGICAL_AND:
				return "&";
			case sym.LOGICAL_OR:
				return "|";

			case sym.EOF:
				return "EOF";

			case sym.error:
				throw new RuntimeException(
						"Lexer should not output the parser error symbol: " + value);

			default:
				throw new RuntimeException(
						"Token " + symId + " is missing a description.");
		}
	}

    private LexerUtil() {
    }

}
