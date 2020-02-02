package cyr7.lexer;

import cyr7.exceptions.LexerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class LexerUtil {

	public static void lex(Reader reader, Writer writer) throws IOException {
		MyLexer lexer = new MyLexer(new BufferedReader(reader));
		MyLexer.Token token;

		try {
			while ((token = lexer.nextToken()) != null) {
				writer.append(fullDescription(token))
						.append(System.lineSeparator());
			}
		} catch (LexerException e) {
			writer.append(e.getMessage()).append(System.lineSeparator());
		}
		writer.flush();
	}

	static String fullDescription(MyLexer.Token token) {
		return (token.line + 1) + ":" + (token.column + 1) + " "
				+ typeAttributeDescription(token);
	}

	static String typeAttributeDescription(MyLexer.Token token) {
		switch (token.type) {
			case USE:
				return "use";
			case IF:
				return "if";
			case WHILE:
				return "while";
			case ELSE:
				return "else";
			case RETURN:
				return "return";
			case LENGTH:
				return "length";

			case TYPE_INT:
				return "int";
			case TYPE_BOOL:
				return "bool";

			case BOOL_LITERAL:
				return token.attribute.toString();
			case INT_LITERAL:
				return "integer " + token.attribute;
			case CHAR_LITERAL:
				return "character " + unescapeString((String) token.attribute);
			case STRING_LITERAL:
				return "string " + unescapeString((String) token.attribute);

			case ID:
				return "id " + token.attribute;

			case L_PAREN:
				return "(";
			case R_PAREN:
				return ")";
			case L_SQ_BRKT:
				return "[";
			case R_SQ_BRKT:
				return "]";
			case L_BRACE:
				return "{";
			case R_BRACE:
				return "}";
			case COLON:
				return ":";
			case SEMICOLON:
				return ";";
			case COMMA:
				return ",";
			case UNDERSCORE:
				return "_";

			case ASSIGN:
				return "=";

			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case MULT:
				return "*";
			case HIGH_MULT:
				return "*>>";
			case DIVIDE:
				return "/";
			case REMAINDER:
				return "%";

			case NEG_BOOL:
				return "!";

			case LT:
				return "<";
			case LTE:
				return "<=";
			case GT:
				return ">";
			case GTE:
				return ">=";

			case EQUALS:
				return "==";
			case NOT_EQUALS:
				return "!=";

			case LOGICAL_AND:
				return "&";
			case LOGICAL_OR:
				return "|";

			default:
				throw new RuntimeException(
						"Token " + token + " is missing a description.");
		}
	}

	public static String unescapeString(String s){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
			switch (s.charAt(i)){
				case '\n': sb.append("\\n"); break;
				case '\t': sb.append("\\t"); break;
				case '\r': sb.append("\\r"); break;
				case '\f': sb.append("\\f"); break;
				default: sb.append(s.charAt(i));
			}
		return sb.toString();
	}

	private LexerUtil() {
	}

}
